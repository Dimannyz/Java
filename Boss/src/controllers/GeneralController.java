package controllers;

import boss.BossForm;
import boss.BossFormListener;

import java.io.IOException;

public class GeneralController {

    static NetController netController;
    static BossForm bf = BossFormListener.bf;

    public static boolean bossConnect(int port) throws IOException {

        netController = new NetController(port);
        netController.start();

        return true;

    }

    public static boolean bossDisconnect() throws IOException {

        netController.disconnect();

        return true;

    }

    public static boolean bossExit() {

        bf.dispose();
        System.exit(0);

        return true;

    }
    public static void viewWorkers(String filter,String sorter)
    {
        try {
            netController.viewWorkers(filter, sorter);
        }catch (IOException io){io.printStackTrace();}
    }
    public static void getWorkerInfo(String srtId)
    {
        try {
            Integer id=Integer.parseInt(srtId);
            netController.getWorkerInfo(id);
        }catch (NumberFormatException notANumber)
        {
            bf.getTextAreaLogs().setText("Введите число");
            notANumber.printStackTrace();
        }
        catch (IOException io){io.printStackTrace();}
    }
    public static void updateSalary(String strSalary,String strId)
    {
        try {
            Integer id=Integer.parseInt(strId);
            Double salary=Double.parseDouble(strSalary);
            netController.updateSalary(salary,id);
        }catch (NumberFormatException notANumber)
        {
            bf.getTextAreaLogs().setText("Введите число");
            notANumber.printStackTrace();
        }
        catch (IOException io){io.printStackTrace();}
    }
}