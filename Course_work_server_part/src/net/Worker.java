package net;

import controllers.GeneralController;
import scheme.ClientTask;
import scheme.Employee;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Timer;


public class Worker implements Runnable
{
    Socket s=null;
    private InputStream in=null;
    private ObjectOutputStream oos=null;
    private ObjectInputStream ois=null;
    private Employee employee=null;

    public Worker(Socket s, InputStream in, ObjectOutputStream oos, ObjectInputStream ois, Employee employee)
    {
        this.s=s;
        this.in=in;
        this.oos=oos;
        this.ois=ois;
        this.employee=employee;
    }
    @Override
    public void run()
    {
        try{
            if (employee.getLastDateLogin().equals (LocalDate.now()))
            {
                employee.setLastDateLogin(LocalDate.now());
                Server.createTasks(employee);
            }
            for (ClientTask clientTask : employee.getTasksTime()) {
               // if (!(clientTask.isExecuted()))
                {
                    Timer task = new Timer();
                    task.schedule(clientTask, clientTask.getData());
                    System.out.println(clientTask.getData());
                }
            }
            oos.writeInt(employee.getId());
            oos.writeObject(employee.getName());
            while (true)
            {
                if (s.isClosed()) break;
                if (in.available()>0) {
                    GeneralController.processWorker(ois,oos,employee);
                }
            }

        }catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    public Socket getS() {
        return s;
    }

    public ObjectOutputStream getOos() { return oos; }

    public Employee getEmployee() {
        return employee;
    }

}
