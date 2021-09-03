package scheme;

import controllers.GeneralController;

import java.util.Date;
import java.util.TimerTask;

public class ClientTask extends TimerTask
{
    Employee employee=null;
    Date data=null;
    boolean isExecuted=false;
    public ClientTask(Employee employee, Date data)
    {
        super();
        this.data=data;
        this.employee=employee;
    }
    public ClientTask(Employee employee, Date data,Boolean isExecuted)
    {
        this(employee,data);
        this.isExecuted=isExecuted;
    }
    @Override
    public void run()
    {
        if (employee.isOnline())
        {
            try {
                Thread.sleep(500);
                GeneralController.clientCheck(employee);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            else
                {
                Record record=new Record(employee.getId(),false,"Компьютер работника не был подключен к серверу.");
                employee.addRecord(record);
        }
            isExecuted=true;
    }

    public Date getData() {
        return data;
    }

    public boolean isExecuted() {
        return isExecuted;
    }

    public String getInfo()
    {
        return GeneralController.getDateFormat().format(data)+CSVStorage.getSeparator()+isExecuted;
    }

}
