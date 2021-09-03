package controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetController extends Thread{

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

            s = new Socket (GeneralController.bf.getTextFieldIp().getText(),port);
            objTranOut = new ObjectOutputStream(s.getOutputStream());
            objTranIn = new ObjectInputStream(s.getInputStream());
            objTranOut.writeObject("Босс");
            GeneralController.bf.getTextAreaLogs().append("Клиент подключился к серверу" + "\n");
            System.out.println("Клиент подключился к серверу");

            isConnect = true;
            GeneralController.bf.BossConnected();
            GeneralController.bf.getLabelIdText().setText(String.valueOf(objTranIn.readInt()));
            GeneralController.bf.getLabelWhoText().setText((String) objTranIn.readObject());
            while (true)
            {
                String object = (String) objTranIn.readObject();
                switch (object)
                {
                    case ("Отключение"):
                    {
                        GeneralController.bf.getTextAreaLogs().append("Сервер отключился" + "\n");
                        GeneralController.bf.BossDisconnected();
                        s.close();
                        return;
                    }
                    case ("Не найдено"):{
                        GeneralController.bf.getTextAreaLogs().append("Работника с таким id не найдено.");
                        break;
                    }

                    case ("Найдено"):
                    {
                        GeneralController.bf.salaryMode();
                        break;
                    }
                    default:GeneralController.bf.getTextAreaLogs().append(object);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (isConnect == false) {

                GeneralController.bf.getTextAreaLogs().append("Сервер отключен или недоступен" + "\n");
                System.out.println("Клиент отключился от сервера");

            }
            else {e.printStackTrace();}
        }

    }

    public void disconnect() throws IOException {

        objTranOut.writeObject("Отключить");
        GeneralController.bf.BossDisconnected();
        isConnect = false;
        s.close();

    }
    public void viewWorkers(String filter,String sort) throws IOException {
        objTranOut.writeObject("Просмотр");
        objTranOut.writeObject(filter);
        objTranOut.writeObject(sort);
        GeneralController.bf.getTextAreaLogs().setText("Запрос на просмотр работников отправлен.\n");
    }
    public void getWorkerInfo(Integer id) throws IOException
    {
        objTranOut.writeObject("Информация");
        objTranOut.writeObject(id);
        GeneralController.bf.getTextAreaLogs().setText("Запрос на получение информации о рабочем отправлен.\n");
    }
    public void updateSalary(Double salary,Integer id) throws IOException
    {
        objTranOut.writeObject("ИзменитьЗП");
        objTranOut.writeObject(id);
        objTranOut.writeObject(salary);
        GeneralController.bf.informationMode();
        GeneralController.bf.getTextAreaLogs().setText("Запрос на изменение зарплаты отправлен.");
    }

}