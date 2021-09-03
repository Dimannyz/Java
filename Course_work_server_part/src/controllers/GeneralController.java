package controllers;

import UI.ServerForm;
import UI.ServerFormListener;
import net.Server;
import scheme.*;
import scheme.Record;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class GeneralController
{
    private static ServerForm sf;
    private static ServerFormListener sfl;
    private static Server server;
    private static ArrayList<Employee> employees=new ArrayList<>();
    private static DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
    //private static final Integer amountOfAttemps=3;

    public static boolean startServer(int port)
    {
        try {
            Server server=new Server(port);
            GeneralController.setServer(server);
            server.start();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        sf.serverStarted();
        return true;
    }
    public static boolean stopServer()
    {
        try{
            server.serverStop();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        sf.serverStopped();
        return true;
    }
    //Обработка клиента
    public static void processWorker(ObjectInputStream ois, ObjectOutputStream oos, Employee employee)
    {
        try {
            Server.processWorker(ois, oos,employee);
        }catch (IOException| ClassNotFoundException io){io.printStackTrace();}
    }

    public static String getNominalSalary(Employee employee)
    {
        return String.valueOf(employee.getNominalSalary());
    }
    public static String getRealSalary(Employee employee)
    {
        StringBuilder out=new StringBuilder();
        int month=0;
        for (Double number:employee.getRealSalary())
        {
            out.append(printMonth(month)).append(":");
            out.append(number);
            if (number==employee.getNominalSalary()) out.append(" (указана номинальная зарплата, возможны изменения)");
            out.append("\n");
            month++;
        }
        return out.toString();
    }
    public static void clientCheck(Employee employee)
    {
        try {
            Server.clientCheck(employee);
        }catch (IOException io){io.printStackTrace();}
    }
    //Обработка босса
    public static void processHead(ObjectInputStream ois, ObjectOutputStream oos, Boss boss)
    {
        try {
            Server.processHead(ois, oos,boss);
        }catch (IOException| ClassNotFoundException io){io.printStackTrace();}
    }
    //Обработка секретаря
    public static void processSecr(ObjectInputStream ois, ObjectOutputStream oos, Secretary secretary)
    {
        try {
            Server.processSecr(ois,oos,secretary);
        }catch (IOException| ClassNotFoundException| ParseException io){io.printStackTrace();}
    }
    public static String printMonth(int month)
    {
        switch (month)
        {
            case (0):return "Январь";
            case (1):return "Февраль";
            case (2):return "Март";
            case (3):return "Апрель";
            case (4):return "Май";
            case (5):return "Июнь";
            case (6):return "Июль";
            case (7):return "Август";
            case (8):return "Сентябрь";
            case (9):return "Октябрь";
            case (10):return "Ноябрь";
            case (11):return "Декабрь";
            default:return null;
        }
    }
    public static String printEmployeeSchedule(Schedule schedule)
    {
        StringBuilder out=new StringBuilder();
        int datesInRow=0;
        for (DateInfo date:schedule.getDates())
        {
            DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("d");
            out.append(date.getDate().format(dateTimeFormatter));
            employeeDates(date,out);
            datesInRow++;
            if (datesInRow==7)
            {
                out.append("\n");
                datesInRow=0;
            }
        }
        return out.toString();
    }
    public static String printDefaultSchedule(Schedule schedule) {
        StringBuilder out = new StringBuilder();
        int datesInRow = 0;
        for (DateInfo date : schedule.getDates()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d");
            out.append(date.getDate().format(dateTimeFormatter));
            defaultDates(date, out);
            datesInRow++;
            if (datesInRow == 7) {
                out.append("\n");
                datesInRow = 0;
            }
        }
        return out.toString();
    }
    public static void employeeDates(DateInfo date,StringBuilder out)
    {
        switch (date.getStatus())
        {
            case (0): {
                if (date.isAttended()) out.append("(Р) ");
                else out.append("(П) ");
            }
            break;
            case (1):out.append("(В) ");
                break;
            case (2):out.append("(O) ");
                break;
        }
    }
    public static void defaultDates(DateInfo date,StringBuilder out)
    {
        switch (date.getStatus())
        {
            case (0): out.append("(Р) ");
            break;
            case (1):out.append("(В) ");
            break;
        }
    }
    public static String printRecords(ArrayList<Record> buffer)
    {
        StringBuilder out=new StringBuilder();
        for (Record record:buffer)
        {
            String data=String.valueOf(record.getDateTime().get(Calendar.DAY_OF_MONTH))+' '+
                    (record.getDateTime().get(Calendar.MONTH)+1)+' '+
                    record.getDateTime().get(Calendar.YEAR)+' '+record.getDateTime().get(Calendar.HOUR_OF_DAY)
                    +':'+record.getDateTime().get(Calendar.MINUTE)+':'+record.getDateTime().get(Calendar.SECOND);
            out.append("id ").append(record.getId()).append(" время проверки ").append(data).append(" тест");
            if (record.isPassedTest()) out.append(" пройден");
            else out.append(" не пройден");
            out.append("\n");
        }
        return out.toString();
    }
    public static void addLogs(String add)
    {
        GeneralController.getSf().getTextAreaLogs().setText
                (GeneralController.getSf().getTextAreaLogs().getText() + add);
    }

    public static ServerForm getSf() {
        return sf;
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static void setSf(ServerForm sf) {
        GeneralController.sf = sf;
    }

    public static void setSfl(ServerFormListener sfl) {
        GeneralController.sfl = sfl;
    }

    public static void setServer(Server server) {
        GeneralController.server = server;
    }

    public static DateFormat getDateFormat() { return dateFormat; }

    //public static Integer getAmountOfAttemps() { return amountOfAttemps; }
}
