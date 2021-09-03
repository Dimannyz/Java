package scheme;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import controllers.GeneralController;
import net.Server;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CSVStorage
{
    private static final char separator=';';
    private static final String rec=" записи.csv";
    private static final String sched= " расписание.csv";
    private static final String sal= " зарплаты.csv";
    private static final String tasks= " задачи.csv";
    private static final String empl= "Работники.csv";
    private static final String ips="IP.csv";
    private static final String heads="Босс и секретарь.csv";

    public static void saveRecords(Employee employee)
    {
        try {
            String FILE_NAME = String.valueOf(employee.getId()) + ' ' + employee.getName() + rec;
            CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME,  Charset.forName("cp1251")),separator,'"', '"', "\n");
            for (Record record: employee.getRecords())
            {
                    String[] line = record.getInfo().split(String.valueOf(separator));
                    writer.writeNext(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadRecords(Employee employee)
    {
        try{
            employee.getRecords().clear();
            String FILE_NAME=String.valueOf(employee.getId()) + ' ' + employee.getName() +rec;
            CSVParser csvParser=new CSVParserBuilder().withSeparator(separator).build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(FILE_NAME, Charset.forName("cp1251"))).withCSVParser(csvParser).build();
            String[] line;
            while ((line=reader.readNext())!=null)
            {
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd MM yyyy kk:mm:ss");
                Calendar calendar=GregorianCalendar.getInstance();
                calendar.setTime(dateFormat.parse(line[2]));
                Record record=new Record(Integer.parseInt(line[0]),Integer.parseInt(line[1]),(GregorianCalendar) calendar,Boolean.parseBoolean(line[3]),line[4]);
                employee.addRecord(record);
            }
            reader.close();
        }catch (IOException | CsvValidationException | ParseException io){io.printStackTrace();}
    }
    public static void saveSchedule(Employee employee) {
        try {
            String FILE_NAME = String.valueOf(employee.getId()) + ' ' + employee.getName() + sched;
            CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME, Charset.forName("cp1251")),separator,'"', '"', "\n");
            for (Schedule schedule : employee.getSchedules())
            {
                String[] line = schedule.getInfo().split(String.valueOf(separator));
                writer.writeNext(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadSchedule(Employee employee)
    {
        try{
            employee.getSchedules().clear();
            String FILE_NAME=String.valueOf(employee.getId()) + ' ' + employee.getName() +sched;
            CSVParser csvParser=new CSVParserBuilder().withSeparator(separator).build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(FILE_NAME, Charset.forName("cp1251"))).withCSVParser(csvParser).build();
            String[] line;
            int month=1;
            while ((line=reader.readNext())!=null)
            {
                Schedule schedule=null;
                schedule=new Schedule();
                Calendar dateBegin = GregorianCalendar.getInstance();
                dateBegin.set(Schedule.getYEAR(), month - 1, 1);
                Calendar dateEnd = GregorianCalendar.getInstance();
                if (dateBegin.get(Calendar.MONTH)==Calendar.JANUARY) dateEnd.set(Schedule.getYEAR(),month-1,31);
                else dateEnd.set(Schedule.getYEAR(), month, 1);
                schedule.setFines(Integer.parseInt(line[0]));
                int offset=1;
                while (offset<line.length)
                {
                    LocalDate date = LocalDate.parse(line[offset]);
                    DateInfo dateInfo = new DateInfo(date, Integer.parseInt(line[offset+1]), Boolean.parseBoolean(line[offset+2]));
                    schedule.getDates().add(dateInfo);
                    offset+=3;
                    dateBegin.add(Calendar.DAY_OF_MONTH, 1);
                }
                month++;
                employee.getSchedules().add(schedule);
            }
            reader.close();
        }catch (IOException | CsvValidationException io){io.printStackTrace();}

    }

    public static void saveSalaries(Employee employee)
    {
        try {
            String FILE_NAME = String.valueOf(employee.getId()) + ' ' + employee.getName() + sal;
            CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME, Charset.forName("cp1251")),separator,'"', '"', "\n");
            StringBuilder salaries=new StringBuilder();
            for (Double salary : employee.getRealSalary())
            {
                salaries.append(salary);
                salaries.append(separator);
            }
            String[] line = salaries.toString().split(String.valueOf(separator));
            writer.writeNext(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadSalaries(Employee employee)
    {
        try {
            employee.getRealSalary().clear();
            String FILE_NAME=String.valueOf(employee.getId()) + ' ' + employee.getName() +sal;
            CSVParser csvParser=new CSVParserBuilder().withSeparator(separator).build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(FILE_NAME, Charset.forName("cp1251"))).withCSVParser(csvParser).build();
            String[] line;
            while ((line=reader.readNext())!=null)
            {
                Double salary=null;
                for (int i=0;i<12;i++)
                {
                    salary = Double.parseDouble(line[i]);
                    employee.getRealSalary().add(salary);
                }
            }
            reader.close();
        }catch (IOException | CsvValidationException io){io.printStackTrace();}
    }
    public static void saveTasksTime(Employee employee)
    {
        try {
            String FILE_NAME = String.valueOf(employee.getId()) + ' ' + employee.getName() + tasks;
            CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME, Charset.forName("cp1251")),separator,'"', '"', "\n");
            StringBuilder tasksTime=new StringBuilder();
            for (ClientTask task : employee.getTasksTime())
            {
                tasksTime.append(task.getInfo());
                tasksTime.append(separator);
            }
            String[] line = tasksTime.toString().split(String.valueOf(separator));
            writer.writeNext(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadTasksTime(Employee employee)
    {
        try {
            employee.getTasksTime().clear();
            String FILE_NAME=String.valueOf(employee.getId()) + ' ' + employee.getName() +tasks;
            CSVParser csvParser=new CSVParserBuilder().withSeparator(separator).build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(FILE_NAME, Charset.forName("cp1251"))).withCSVParser(csvParser).build();
            String[] line;
            while ((line=reader.readNext())!=null)
            {
                Date taskTime=null;
                int offset=0;
                while (offset<line.length)
                {
                    taskTime = GeneralController.getDateFormat().parse(line[offset]);
                    boolean isExecuted=Boolean.parseBoolean(line[offset+1]);
                    ClientTask clientTask=new ClientTask(employee,taskTime,isExecuted);
                    offset+=2;
                    employee.getTasksTime().add(clientTask);
                }
            }
            reader.close();
        }catch (IOException | CsvValidationException | ParseException io){io.printStackTrace();}
    }



    public static void saveAllEmployees() throws IOException
    {
        CSVWriter writer = new CSVWriter(new FileWriter(empl,Charset.forName("cp1251")),separator,'"', '"', "\n");
        for (Employee employee: GeneralController.getEmployees())
        {
            String[] line=employee.getInfo().split(String.valueOf(separator));
            writer.writeNext(line);
            saveRecords(employee);
            saveSchedule(employee);
            saveSalaries(employee);
            saveTasksTime(employee);
        }
        writer.close();
    }
    public static void loadAllEmployees() throws IOException, CsvValidationException
    {
        GeneralController.getEmployees().clear();
        CSVParser csvParser=new CSVParserBuilder().withSeparator(separator).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(empl, Charset.forName("cp1251"))).withCSVParser(csvParser).build();
        String[] line;
        while ((line=reader.readNext())!=null)
        {
            Employee employee=new Employee(Integer.parseInt(line[0]),line[1],Double.parseDouble(line[2]),
                    Boolean.parseBoolean(line[3]),LocalDate.parse(line[4]));
            GeneralController.getEmployees().add(employee);
            loadRecords(employee);
            loadSchedule(employee);
            loadSalaries(employee);
            loadTasksTime(employee);
        }
        reader.close();
    }
    public static void loadIPs() throws IOException, CsvValidationException
    {
        Server.getIp().clear();
        CSVParser csvParser=new CSVParserBuilder().withSeparator(separator).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(ips, Charset.forName("cp1251"))).withCSVParser(csvParser).build();
        String[] line;
        while ((line=reader.readNext())!=null)
        {
            Server.getIp().put(Integer.parseInt(line[0]),InetAddress.getByName(line[1]));
        }
        reader.close();
    }
    public static void saveHeads() throws IOException
    {
        CSVWriter writer = new CSVWriter(new FileWriter(heads,Charset.forName("cp1251")),separator,'"', '"', "\n");
        String[] line=Boss.getBoss().getName().split(String.valueOf(separator));
        writer.writeNext(line);
        line=Secretary.getSecretary().getName().split(String.valueOf(separator));
        writer.writeNext(line);
        writer.close();
    }
    public static void loadHeads() throws IOException, CsvValidationException {
        CSVParser csvParser=new CSVParserBuilder().withSeparator(separator).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(heads, Charset.forName("cp1251"))).withCSVParser(csvParser).build();
        String[] line;
        line=reader.readNext();
        Boss.setBoss(line[0]);
        line=reader.readNext();
        Secretary.setSecretary(line[0]);
        reader.close();
    }
    public static char getSeparator() { return separator; }
}
