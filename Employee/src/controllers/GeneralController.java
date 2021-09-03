package controllers;

import checkForm.CheckForm;
import employee.EmployeeForm;
import employee.EmployeeFormListener;

import javax.swing.*;
import java.io.IOException;

public class GeneralController {

    static NetController netController;
    private static EmployeeForm ef = EmployeeFormListener.ef;
    private static CheckForm cf=null;

    public static boolean employeeConnect(int port) throws IOException {

        netController = new NetController(port);
        netController.start();

        return true;

    }

    public static boolean employeeDisconnect () throws IOException {

        netController.disconnect();

        return true;

    }

    public static boolean employeeExit () {

        ef.dispose();
        System.exit(0);

        return true;

    }
    public static void getNominalSalary()
    {
        try {
            netController.getNominalSalary();
        }catch (IOException io){io.printStackTrace();}
    }
    public static void getRealSalary()
    {
        try {
            netController.getRealSalary();
        }catch (IOException io){io.printStackTrace();}
    }
    public static void checkIn(Integer number)
    {
        CheckForm cf=new CheckForm(ef,"Проверка присутствия", false);
        cf.getTextLabelInput().setText(String.valueOf(number));
    }
    public static void checkOut(String strAnswer)
    {
        try {
            Integer answer=Integer.parseInt(strAnswer);
            NetController.checkOut(answer);
        }catch (IOException io){io.printStackTrace();}
        catch (NumberFormatException notANumber)
        {
            cf.getCheckLabelResult().setText("Введите число");
            cf.getCheckLabelResult().setVisible(true);
        }
    }

    public static void setCheckResult(String result)
    {
        cf.getCheckLabelResult().setText(result);
    }
    public static EmployeeForm getEf() { return ef; }

    public static CheckForm getCf() { return cf; }

    public static void setCf(CheckForm cf) { GeneralController.cf = cf; }
}