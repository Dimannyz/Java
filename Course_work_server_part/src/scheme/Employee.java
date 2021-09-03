package scheme;

import controllers.GeneralController;

import java.time.LocalDate;
import java.util.ArrayList;


public class Employee extends Person
{
    private double nominalSalary;
    private boolean isOnline;
    private LocalDate lastDateLogin=null;
    private ArrayList<Double> realSalary=new ArrayList<>(12);
    private ArrayList<Schedule> schedules=new ArrayList<>();
    private ArrayList<Record> records=new ArrayList<>();
    private ArrayList<ClientTask> tasksTime=new ArrayList<>(6);

    public Employee(String name,double nominalSalary)
    {
        super((GeneralController.getEmployees().size()+2),name);
        this.nominalSalary=nominalSalary;
        for (int i=0; i<12;i++)
            realSalary.add(i,nominalSalary);
        lastDateLogin=LocalDate.now();
        isOnline=false;
        this.schedules=Schedule.defaultSchedule();
    }
    //Для чтения из файла
    public Employee(int id,String name,double nominalSalary,boolean isOnline, LocalDate lastDateLogin)
    {
        super(id,name);
        this.nominalSalary=nominalSalary;
        this.isOnline=isOnline;
        this.lastDateLogin=lastDateLogin;
    }
    public void addRecord(Record record)
    {
        record.setId(records.size());
        if (!(record.isPassedTest())) getSchedules().get(LocalDate.now().getMonthValue() - 1).setFines(getSchedules().get(LocalDate.now().getMonthValue() - 1).getFines()+1);
        records.add(record);
    }
    public String printRecords()
    {
        StringBuilder out = new StringBuilder();
        for (Record record:records)
        {
            out.append(record.getInfo()).append('\n');
        }
        return out.toString();
    }
    @Override
    public String getInfo() {
        return super.getInfo()+CSVStorage.getSeparator()+ nominalSalary +CSVStorage.getSeparator()+ isOnline+
                CSVStorage.getSeparator()+lastDateLogin;
    }

    public double getNominalSalary() {
        return nominalSalary;
    }

    public ArrayList<Double> getRealSalary() {
        return realSalary;
    }

    public LocalDate getLastDateLogin() {
        return lastDateLogin;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public ArrayList<Record> getRecords() { return records; }

    public ArrayList<ClientTask> getTasksTime() { return tasksTime; }

    public ArrayList<Schedule> getSchedules() { return schedules; }

    public void setNominalSalary(double nominalSalary) {
        this.nominalSalary = nominalSalary;
    }

    public void setLastDateLogin(LocalDate lastDateLogin) {
        this.lastDateLogin = lastDateLogin;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }


}
