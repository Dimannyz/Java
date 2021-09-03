package starter;

import UI.ServerForm;
import com.opencsv.exceptions.CsvValidationException;
import controllers.GeneralController;
import net.Server;
import scheme.*;
import scheme.Record;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.Timer;

public class Main {

    public static void main(String[] args) throws ParseException, UnknownHostException {
        /*try {
            Schedule january=new Schedule(1);
            try {
                january.getDate("01-01-2021").setStatus(2);
            }catch (IllegalArgumentException e){e.printStackTrace();}
            january.getInfo();
            Schedule february=new Schedule(2);
            february.getInfo();

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        /*ArrayList<Schedule> schedules=Schedule.defaultSchedule();
        for (Schedule schedule: schedules)
        {
            schedule.getInfo();
        }*/

       /* Boss.setBoss("Баранов Баран Баранович");
        Secretary.setSecretary("Михайлов Иван Баранович");
        Employee ivan=new Employee("Иванов Иван Иванович",20000);
        GeneralController.getEmployees().add(ivan);
        //Save/load allEmployees тест
        Employee misha=new Employee("Михайлов Михаил Михайлович",15000);
        Record record=new Record(0,true);
        Record record1=new Record(1,true);
        ivan.addRecord(record);
        misha.addRecord(record1);

        GeneralController.getEmployees().add(misha);
        Server.createTasks(ivan);
        Server.createTasks(misha);

        for (Employee employee: GeneralController.getEmployees())
        {
            System.out.println(employee.getInfo());
            for (ClientTask clientTask:employee.getTasksTime())
            {
                clientTask.cancel();
            }*/
        //}
        try {
            CSVStorage.loadHeads();
            CSVStorage.loadAllEmployees();
        }catch (IOException | CsvValidationException io){io.printStackTrace();}
            ServerForm sf=new ServerForm();
        //TimerTasks save/load тест
        /*
        try {
            Thread.sleep(300);
            for (Date date:ivan.getTasksTime())
            {
                System.out.println(ServerTask.getDateFormat().format(date));
            }
            System.out.println("\n\n");
            CSVStorage.saveTasksTime(ivan);
            CSVStorage.loadTasksTime(ivan);
            for (Date date:ivan.getTasksTime())
            {
                System.out.println(ServerTask.getDateFormat().format(date));
            }
        }catch (InterruptedException ie){ie.printStackTrace();}*/



        //Salary save/load тест
        /*for (Double salary:ivan.getRealSalary())
        {
            System.out.println(salary);
        }
        System.out.println("\n\n");
        CSVStorage.saveSalaries(ivan);
        CSVStorage.loadSalaries(ivan);
        for (Double salary:ivan.getRealSalary())
        {
            System.out.println(salary);
        }*/


        //Record save/load тест
        /*CSVStorage.loadRecords(ivan);
        Record record1=new Record(0,true);
        Record record2=new Record(0,false,"Прогулял сволочь.");
        ivan.addRecord(record1);
        ivan.addRecord(record2);
        //System.out.println(ivan.printRecords());
        CSVStorage.saveRecords(ivan);
        System.out.println(ivan.printRecords());*/

        //Schedule save/load тест
        /*for (Schedule schedule:ivan.getSchedules())
        {
            System.out.println(schedule.getInfo()+"\n");
        }
        System.out.println("\n\n\n");
        CSVStorage.saveSchedule(misha);
        CSVStorage.loadSchedule(misha);
        for (Schedule schedule:ivan.getSchedules())
        {
            System.out.println(schedule.getInfo()+"\n");
        }*/

        //Тест таймеров
        /*String yearMonthDay= LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
        try {
            Date base=dateFormat.parse(yearMonthDay+" 10:15:00");
            for (int i=0;i<6;i++)
            {
                //50 минут в миллисекундах
                int offset= (int) (Math.random() * 3000 * 1000);
                Date taskTime=new Date(base.getTime()+offset);
                System.out.println(dateFormat.format(taskTime));
                base.setTime(base.getTime()+4800000);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

    }
}
