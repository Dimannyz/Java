package net;

import controllers.GeneralController;
import scheme.Boss;
import scheme.Secretary;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SecretaryThread implements Runnable
{
    private Socket s=null;
    private InputStream in=null;
    private ObjectOutputStream oos=null;
    private ObjectInputStream ois=null;
    private Secretary secretary=null;
    public SecretaryThread(Socket s, InputStream in, ObjectOutputStream oos, ObjectInputStream ois)
    {
        this.s=s;
        this.in=in;
        this.oos=oos;
        this.ois=ois;
        secretary=Secretary.getSecretary();
    }

    @Override
    public void run() {
        try {
            oos.writeInt(secretary.getId());
            System.out.println(secretary.getId());
            oos.writeObject(secretary.getName());
            System.out.println(secretary.getName());
            while (true)
            {
                if (s.isClosed()) break;
                if (in.available()>0) {
                    GeneralController.processSecr(ois,oos,secretary);
                }
            }
        }catch (IOException io){io.printStackTrace();}

    }

    public Socket getS() { return s; }

    public ObjectOutputStream getOos() { return oos; }
}
