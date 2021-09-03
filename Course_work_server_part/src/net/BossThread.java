package net;

import controllers.GeneralController;
import scheme.Boss;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BossThread implements Runnable
{
    private Socket s=null;
    private InputStream in=null;
    private ObjectOutputStream oos=null;
    private ObjectInputStream ois=null;
    private Boss boss=null;
    public BossThread(Socket s, InputStream in, ObjectOutputStream oos, ObjectInputStream ois)
    {
        this.s=s;
        this.in=in;
        this.oos=oos;
        this.ois=ois;
        boss=Boss.getBoss();
    }

    @Override
    public void run() {
        try {
            oos.writeInt(boss.getId());
            System.out.println(boss.getId());
            oos.writeObject(boss.getName());
            System.out.println(boss.getName());
            while (true)
            {
                if (s.isClosed()) break;
                if (in.available()>0) {
                    GeneralController.processHead(ois,oos,boss);
                }
            }
        }catch (IOException io){io.printStackTrace();}

    }

    public Socket getS() { return s; }

    public ObjectOutputStream getOos() { return oos; }
}
