package controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetController extends Thread {

    boolean isConnect = false;
    int port;
    Socket s = null;
    ObjectInputStream objTranIn = null;
    ObjectOutputStream objTranOut = null;

    public NetController(int port) throws IOException {

        this.port = port;

    }

    public void run() {

        try {

            s = new Socket ("localHost",port);
            objTranOut = new ObjectOutputStream(s.getOutputStream());
            objTranIn = new ObjectInputStream(s.getInputStream());
            objTranOut.writeObject("Секретарь");
            GeneralController.sf.getTextAreaLogs().append("Клиент подключился к серверу" + "\n");
            System.out.println("Клиент подключился к серверу");
            isConnect = true;
            GeneralController.sf.secretaryConnected();
            GeneralController.sf.getLabelIdText().setText(String.valueOf(objTranIn.readInt()));
            GeneralController.sf.getLabelWhoText().setText((String) objTranIn.readObject());
            while (true)
            {
                String object = (String) objTranIn.readObject();
                switch (object)
                {
                    case ("Отключение"):
                    {
                        {
                            GeneralController.sf.getTextAreaLogs().append("Сервер отключился" + "\n");
                            GeneralController.sf.secretaryDisconnected();
                            s.close();
                            return;
                        }
                    }
                    default:GeneralController.sf.getTextAreaLogs().append(object);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            if (isConnect == false) {

                GeneralController.sf.getTextAreaLogs().append("Сервер отключен или недоступен" + "\n");
                System.out.println("Клиент отключился от сервера"); }

            else {e.printStackTrace();}
        }
    }
    public void disconnect() throws IOException {

        objTranOut.writeObject("Отключить");
        GeneralController.sf.secretaryDisconnected();
        isConnect = false;
        s.close();

    }
    public void showScheduleMonthOrYear(String dateStr,Integer monthInt) throws IOException {
        objTranOut.writeObject("РасписМесяц");
        objTranOut.writeObject(dateStr);
        if (dateStr.equals("За произвольный месяц")) objTranOut.writeObject(monthInt);
        GeneralController.sf.getTextAreaLogs().setText("Запрос на просмотр расписания отправлен.\n");
    }
    public void showAttendance (Integer idWorkerInt,String filterStr,String sorterStr,Integer filterDayStr) throws IOException {
        objTranOut.writeObject("Записи");
        objTranOut.writeObject(idWorkerInt);
        objTranOut.writeObject(filterStr);
        objTranOut.writeObject(sorterStr);
        if (filterDayStr>0) objTranOut.writeObject(filterDayStr);
        GeneralController.sf.getTextAreaLogs().setText("Запрос на просмотр записей отправлен.\n");
    }

    public void detailReason (Integer id,Integer idRecordInt, String reasonStr) throws IOException {
        objTranOut.writeObject("Причина");
        objTranOut.writeObject(id);
        objTranOut.writeObject(idRecordInt);
        objTranOut.writeObject(reasonStr);
        GeneralController.sf.getTextAreaLogs().setText("Запрос на ввод причины отправлен.\n");
    }

    public void giveSchedule (Integer idWorkerInt) throws IOException {
        objTranOut.writeObject("Расписание");
        objTranOut.writeObject(idWorkerInt);
        GeneralController.sf.getTextAreaLogs().setText("Запрос на просмотр расписания отправлен.\n");
    }

    public void specify (Integer idWorkerInt, Integer dayInt) throws IOException {
        objTranOut.writeObject("Отпуск");
        objTranOut.writeObject(idWorkerInt);
        objTranOut.writeObject(dayInt);
        GeneralController.sf.getTextAreaLogs().setText("Запрос на изменение дня отправлен.\n");
    }

}
