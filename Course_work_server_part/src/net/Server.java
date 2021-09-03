package net;

import com.opencsv.exceptions.CsvValidationException;
import controllers.GeneralController;
import scheme.*;
import scheme.Record;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

public class Server extends Thread
{
    static protected ArrayList<Worker> threads = new ArrayList<>();
    static protected HashMap<Integer,InetAddress> ip= new HashMap<>();
    static protected HashMap<Employee,Integer> check=new HashMap<>();
    static protected BossThread bossThread=null;
    static protected SecretaryThread secrThread=null;
    //static protected
    //static protected HashMap<Employee,Integer> activeTasks=new HashMap<>();
    protected int port;
    ServerSocket ss = null;
    private boolean isOnline;

    public Server(int port) throws IOException {
        this.port = port;
        ss = new ServerSocket(port);
        GeneralController.addLogs("Сервер запущен и готов начать работу с клиентами.\n");
    }

    public void run() {
        serverStart();
    }

    public void serverStart() {
        try {
            CSVStorage.loadIPs();
            for (Integer inte:ip.keySet())
            {
                System.out.println(ip.get(inte));
            }
            GeneralController.addLogs("Сервер ожидает подключения клиентов.\n");
            isOnline=true;
            while (isOnline) {
                Socket s = ss.accept();
                InputStream is=s.getInputStream();
                ObjectInputStream ois=new ObjectInputStream(is);
                OutputStream os = s.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                String userType=(String) ois.readObject();
                switch (userType)
                {
                    case ("Работник"):
                    {
                        Worker mt = new Worker(s,is,oos,ois,GeneralController.getEmployees().get(ipID(s.getInetAddress())));
                        Thread t = new Thread(mt);
                        threads.add(mt);
                        mt.getEmployee().setOnline(true);
                        GeneralController.addLogs("Рабочий подключился к серверу.\n");
                        t.start();
                        GeneralController.addLogs("Обработка запроса рабочего " + s.getInetAddress() + " направлена в отдельный поток.\n");
                    }
                    break;
                    case ("Босс"):
                    {
                        bossThread=new BossThread(s,is,oos,ois);
                        Thread t = new Thread(bossThread);
                        GeneralController.addLogs("Босс подключился к серверу.\n");
                        t.start();
                        GeneralController.addLogs("Обработка запроса босса " + s.getInetAddress() + " направлена в отдельный поток.\n");
                    }
                    break;
                    case ("Секретарь"):
                    {
                        secrThread=new SecretaryThread(s,is,oos,ois);
                        Thread t = new Thread(secrThread);
                        GeneralController.addLogs("Секретарь подключился к серверу.\n");
                        t.start();
                        GeneralController.addLogs("Обработка запроса секретаря " + s.getInetAddress() + " направлена в отдельный поток.\n");
                    }
                    break;
                }

            }
        } catch (IOException | CsvValidationException | ClassNotFoundException io) {
            io.printStackTrace();
        }
    }

    public void serverStop() throws IOException {
        for (Worker th : threads) {
            if (!th.getS().isClosed())
            {
                String exit = "Отключение";
                th.getOos().writeObject(exit);
                th.getEmployee().setOnline(false);
                th.getS().close();
            }
        }
        if ((bossThread!=null)&&(!bossThread.getS().isClosed())) {
            bossThread.getOos().writeObject("Отключение");
            bossThread.getS().close();
        }
        if ((secrThread!=null)&&(!secrThread.getS().isClosed())) {
            secrThread.getOos().writeObject("Отключение");
            secrThread.getS().close();
        }
        ss.close();
        GeneralController.addLogs("Сервер отключен.\n");
        isOnline=false;
    }

    public static void createTasks(Employee employee)
    {
        employee.getTasksTime().clear();
        String yearMonthDay= LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        try
        {
            Date base= GeneralController.getDateFormat().parse(yearMonthDay+" 10:15:00");
            for (int i=0;i<1;i++)
            {
                //Рандомное число миллисекунд из 50 минутного интервала
                int offset= (int) (Math.random() * 3000 * 1000);
                Date taskTime=new Date(base.getTime()+offset);
                ClientTask clientTask=new ClientTask(employee,taskTime);
                employee.getTasksTime().add(clientTask);
                //Добавляем 80 минут для как минимум 30 минутной задержки.
                base.setTime(base.getTime()+4800000);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    //Обработка рабочего
    public static void processWorker(ObjectInputStream ois, ObjectOutputStream oos, Employee employee) throws IOException, ClassNotFoundException {
        String action=(String) ois.readObject();
        switch (action)
        {
            case ("Отключить"):
                {
                for (Worker th : threads)
                {
                    if (th.getOos().equals(oos))
                    {
                        th.getS().close();
                        th.getEmployee().setOnline(false);
                        threads.remove(th);
                        GeneralController.addLogs("Клиент " + th.getS().getInetAddress() + " отключился.\n");
                        break;
                    }
                }
                }
                break;
            case ("НоминЗП"):
            {
                oos.writeObject(GeneralController.getNominalSalary(employee));
            }
            break;
            case ("РеалЗП"):
            {
                oos.writeObject(GeneralController.getRealSalary(employee));
            }
            break;
            case ("Проверка"):
            {
                Integer check=(Integer) ois.readObject();
               // Integer attemps=(Integer) ois.readObject();
                if ((Server.check.containsKey(employee) &&(Server.check.get(employee).equals(check))))
                {
                    oos.writeObject("Пройдено");
                    System.out.println("Проверка пройдена");
                    Server.check.remove(employee,check);
                    employee.addRecord(new Record(employee.getId(),true));
                   // Server.activeTasks.remove(employee,attemps);
                }
                else {
                    oos.writeObject("Не пройдено");
                    System.out.println("Проверка не пройдена");
                    Server.check.remove(employee, check);
                    employee.addRecord(new Record(employee.getId(), false));
                }
            }
        }
    }
    public static void clientCheck(Employee employee) throws IOException {
        System.out.println("Началась проверка ");
        for (Worker worker :threads)
        {
            if (worker.getEmployee().equals(employee))
            {
                Integer rand=(int) (Math.random()*100);
                worker.getOos().writeObject("Проверка");
                worker.getOos().writeObject(rand);
                //client.getOos().writeObject(GeneralController.getAmountOfAttemps());
                check.put(employee,rand);
                //activeTasks.put(employee,GeneralController.getAmountOfAttemps());
                System.out.println("Началась проверка "+employee.getName());
            }
        }
    }
    //Обработка босса
    public static void processHead(ObjectInputStream ois, ObjectOutputStream oos, Boss boss) throws IOException, ClassNotFoundException
    {
        String action=(String) ois.readObject();
        switch (action) {
            case ("Отключить"): {
                bossThread.getS().close();
                GeneralController.addLogs("Босс " + bossThread.getS().getInetAddress() + " отключился.\n");
                bossThread=null;
            }
            break;
            case ("Просмотр"):
            {
                ArrayList<Employee> buffer = new ArrayList<>(GeneralController.getEmployees());
                String filter=(String) ois.readObject();
                String sort=(String) ois.readObject();
                Comparator<Employee> comparator=new Comparator<Employee>() {
                    @Override
                    public int compare(Employee o1, Employee o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                };
                if (sort.equals("от А до Я")) buffer.sort(comparator);
                else buffer.sort(Collections.reverseOrder(comparator));
                System.out.println("Просмотр сотрудников");
                StringBuilder out=new StringBuilder();
                for (Employee employee:buffer)
                {
                    boolean condition=false;
                    switch (filter)
                    {
                        case ("Все"): condition=true;
                        break;
                        case ("В сети"): if (employee.isOnline()) condition=true;
                        break;
                        case ("Не в сети"): if (!(employee.isOnline())) condition=true;
                        break;
                    }
                    int month=LocalDate.now().get(MONTH_OF_YEAR)-1;
                    System.out.println(employee.getSchedules().get(month).getDate(LocalDate.now().format(Schedule.getDateFormat())).getDate());
                    if ((employee.getSchedules().get(month).getDate(LocalDate.now().format(Schedule.getDateFormat())).getStatus()==0)&&(condition))
                    {
                        out.append("id ").append(employee.getId()).append(" ").append(employee.getName()).append(" статус ");
                        if (employee.isOnline()) out.append("в сети\n");
                        else out.append("не в сети\n");
                    }
                }
                if (out.length()==0) out.append("По заданным параметрам фильтрации и сортировки не найдено пользователей.\n");
                oos.writeObject(out.toString());
                System.out.println(out.toString());
            }
            break;
            case ("Информация"):
            {
                Integer id=(Integer) ois.readObject();
                StringBuilder out=new StringBuilder();
                boolean match=false;
                int fines=0;
                for (Employee employee:GeneralController.getEmployees())
                {
                    if (employee.getId()==id)
                    {
                        match=true;
                        out.append("Номинальная зарплата:");
                        out.append(employee.getNominalSalary());
                        out.append("\n");
                        int m=LocalDate.now().getMonthValue()-1;
                        out.append(GeneralController.printMonth(m)).append("\n");
                        Schedule month=employee.getSchedules().get(m);
                        out.append(GeneralController.printEmployeeSchedule(month));
                        fines=month.getFines();
                    }
                }
                if (!(match)) oos.writeObject("Не найдено");
                else
                    {
                        oos.writeObject("Найдено");
                        out.append("\n");
                        out.append("Число штрафов: ").append(fines);
                        oos.writeObject(out.toString());
                    }
            }
            break;
            case ("ИзменитьЗП"):
            {
                Integer id=(Integer) ois.readObject();
                Double salary=(Double) ois.readObject();
                for (Employee employee:GeneralController.getEmployees())
                {
                    if (employee.getId()==id) {
                        employee.getRealSalary().set((LocalDate.now().getMonthValue() - 1), salary);
                        break;
                    }
                }
                oos.writeObject("Зарплата успешно изменена.");
            }
            }
    }
    public static void processSecr(ObjectInputStream ois,ObjectOutputStream oos,Secretary secretary) throws IOException, ClassNotFoundException, ParseException {
        String action=(String) ois.readObject();
        switch (action) {
            case ("Отключить"): {
                secrThread.getS().close();
                GeneralController.addLogs("Секретарь " + secrThread.getS().getInetAddress() + " отключился.\n");
                secrThread = null;
            }
            break;
            case ("РасписМесяц"): {
                String type = (String) ois.readObject();
                StringBuilder out = new StringBuilder();
                switch (type) {
                    case ("За текущий год"):
                    {
                        int month = 0;
                        for (Schedule schedule : Schedule.defaultSchedule()) {
                            out.append(GeneralController.printMonth(month)).append("\n");
                            out.append(GeneralController.printDefaultSchedule(schedule));
                            out.append("\n");
                            month++;
                    }
                        break;
                    }
                    case ("За текущий месяц"):
                    {
                        Schedule month = new Schedule(LocalDate.now().getMonthValue() - 1);
                        out.append(GeneralController.printMonth(LocalDate.now().getMonthValue() - 1)).append("\n");
                        out.append(GeneralController.printDefaultSchedule(month));
                        out.append("\n");
                    }
                    break;
                    case ("За произвольный месяц"):
                        Integer month=(Integer) ois.readObject();
                        System.out.println(month);
                        Schedule schedule = new Schedule(month);
                        out.append(GeneralController.printMonth(month - 1)).append("\n");
                        out.append(GeneralController.printDefaultSchedule(schedule));
                        out.append("\n");
            }
                    oos.writeObject(out.toString());
                }
                break;
            case ("Записи"):
            {
                Integer id=(Integer) ois.readObject();
                String filter=(String) ois.readObject();
                String sorter=(String) ois.readObject();
                Employee employee=null;
                for (Employee e:GeneralController.getEmployees())
                {
                    if (e.getId()==id) employee=e;
                }
                ArrayList<Record> buffer=new ArrayList<>();
                if (filter.equals("без фильтрации"))
                {
                    if (employee==null)
                    {
                        oos.writeObject("Не найдено рабочего с таким id.");
                        break;
                    }
                    for (Record record:employee.getRecords())
                    {
                        if (record.getDateTime().get(Calendar.MONTH)==(LocalDate.now().getMonthValue()-1)) buffer.add(record);
                    }
                }
                else
                    {
                        Integer day=(Integer) ois.readObject();
                        if (employee==null)
                        {
                            oos.writeObject("Не найдено рабочего с таким id.");
                            break;
                        }
                        for (Record record:employee.getRecords())
                        {
                            if ((record.getDateTime().get(Calendar.MONTH)==(LocalDate.now().getMonthValue()-1))&&(record.getDateTime().get(Calendar.DAY_OF_MONTH)==day))
                                buffer.add(record);
                        }
                    }
                Comparator<Record> comparator=new Comparator<Record>() {
                    @Override
                    public int compare(Record o1, Record o2) {
                        return o1.getDateTime().compareTo(o2.getDateTime());
                    }
                };
                if (sorter.equals("Сначала новые")) buffer.sort(Collections.reverseOrder(comparator));
                else buffer.sort(comparator);
                oos.writeObject(GeneralController.printRecords(buffer));
            }
            break;
            case ("Причина"):
            {
                Integer id=(Integer) ois.readObject();
                Integer recordId=(Integer)  ois.readObject();
                String reason=(String) ois.readObject();
                Employee employee=null;
                for (Employee e:GeneralController.getEmployees())
                {
                    if (e.getId()==id) employee=e;
                }
                Record record=null;
                for (Record rec:employee.getRecords())
                {
                    if (rec.getId()==recordId) record=rec;
                }
                if (record==null)
                {
                    oos.writeObject("Запись с данным id не найдена.");
                    break;
                }
                record.setNote(reason);
                oos.writeObject("Причина успешно изменена.");
            }
            break;
            case ("Расписание"):
            {
                Integer id =(Integer) ois.readObject();
                Employee employee=null;
                for (Employee e:GeneralController.getEmployees())
                {
                    if (e.getId()==id) employee=e;
                }
                if (employee==null)
                {
                    oos.writeObject("Не найдено рабочего с данным id.");
                    break;
                }
                StringBuilder out=new StringBuilder();
                int m=LocalDate.now().getMonthValue()-1;
                out.append(GeneralController.printMonth(m)).append("\n");
                Schedule month=employee.getSchedules().get(m);
                out.append(GeneralController.printEmployeeSchedule(month));
                oos.writeObject(out.toString());
            }
            break;
            case ("Отпуск"):
            {
                Integer id =(Integer) ois.readObject();
                Integer day =(Integer) ois.readObject();
                Employee employee=null;
                for (Employee e:GeneralController.getEmployees())
                {
                    if (e.getId()==id) employee=e;
                }
                if (employee==null)
                {
                    oos.writeObject("Не найдено рабочего с данным id.");
                    break;
                }
                employee.getSchedules().get(LocalDate.now().getMonthValue()-1).getDates().get(day-1).setStatus(2);
                oos.writeObject("День успешно изменен на выходной.");
            }
        }
    }
    public static int ipID(InetAddress address)
    {
        for (int id: ip.keySet())
        {
            if (ip.get(id).equals(address)) return id;
        }
        return -1;
    }
    public static HashMap<Integer,InetAddress> getIp() { return ip; }

}
