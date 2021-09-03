package controllers;

import controllers.GeneralController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetController extends Thread{

    boolean isConnect = false;
    int port;
    Socket s = null;
    private static ObjectInputStream objTranIn = null;
    private static ObjectOutputStream objTranOut = null;

    public NetController(int port) throws IOException {

        this.port = port;

    }

    public void run() {

        try {
            s = new Socket (GeneralController.getEf().getTextFieldIp().getText(),port);
            objTranOut = new ObjectOutputStream(s.getOutputStream());
            objTranIn = new ObjectInputStream(s.getInputStream());
            objTranOut.writeObject("Работник");
            Thread.sleep(500);
            GeneralController.getEf().getTextAreaLogs().append("Клиент подключился к серверу" + "\n");
            System.out.println("Клиент подключился к серверу");
            isConnect = true;
            GeneralController.getEf().EmployeeConnected();
            GeneralController.getEf().getLabelIdText().setText(String.valueOf(objTranIn.readInt()));
            GeneralController.getEf().getLabelWhoText().setText((String) objTranIn.readObject());
            while (true)
            {
                Object object = objTranIn.readObject();
                if (object instanceof String)
                {
                    if (object.equals("Пройдено"))
                    {
                        GeneralController.getCf().getCheckLabelResult().setVisible(true);
                        GeneralController.setCheckResult("Проверка успешно пройдена. Можете закрыть окно.");
                        GeneralController.getCf().endCheck();
                    }
                    if (object.equals("Не пройдено"))
                    {
                        //Integer attemps=(Integer) objTranIn.readObject();
                        GeneralController.getCf().getCheckLabelResult().setVisible(true);
                        //if (attemps>0) GeneralController.setCheckResult("Проверка не пройдена. Введите число еще раз. Осталось "+attemps+" попыток.");
                        //else
                            {
                                GeneralController.setCheckResult("Проверка провалена. Можете закрыть окно.");
                                GeneralController.getCf().endCheck();
                            }
                    }
                    if (object.equals("Проверка"))
                    {
                        System.out.println("Началась проверка.");
                        Integer number=(Integer) objTranIn.readObject();
                        GeneralController.checkIn(number);
                    }
                    if (((String)object).equals("Отключение"))
                    {
                        GeneralController.getEf().getTextAreaLogs().setText("Сервер отключился" + "\n");
                        GeneralController.getEf().EmployeeDisconnected();
                        s.close();
                        return;
                    }
                    else GeneralController.getEf().getTextAreaLogs().append((String) object);
                }

            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            if (isConnect == false) {

                GeneralController.getEf().getTextAreaLogs().setText("Сервер отключен или недоступен" + "\n");
                System.out.println("Клиент отключился от сервера");

            }
            else {e.printStackTrace();}
        }

    }

    public void disconnect() throws IOException {

        objTranOut.writeObject("Отключить");
        isConnect = false;
        GeneralController.getEf().EmployeeDisconnected();
        s.close();

    }
    public void getNominalSalary() throws IOException {
        objTranOut.writeObject("НоминЗП");
    }
    public void getRealSalary() throws IOException
    {
        objTranOut.writeObject("РеалЗП");
    }
    public static void checkOut(Integer answer) throws IOException {
        objTranOut.writeObject("Проверка");
        objTranOut.writeObject(answer);
    }
}
