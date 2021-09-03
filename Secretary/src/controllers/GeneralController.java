package controllers;

import secretary.SecretaryForm;
import secretary.SecretaryFormListener;

import java.io.IOException;

public class GeneralController {

    static NetController netController;
    static SecretaryForm sf = SecretaryFormListener.sf;

    public static boolean secretaryConnect(int port) throws IOException {

        netController = new NetController(port);
        netController.start();

        return true;

    }

    public static boolean secretaryDisconnect() throws IOException {

        netController.disconnect();

        return true;

    }

    public static boolean secretaryShowSchedule(String dateStr, String monthStr) {

            try {
                if (dateStr.equals("За произвольный месяц"))
                {
                    Integer month = Integer.parseInt(monthStr);
                    if  ((month < 1) || (month > 12))
                        throw new IllegalArgumentException();
                    netController.showScheduleMonthOrYear(dateStr,month);
                }
                else netController.showScheduleMonthOrYear(dateStr,0);
            } catch (IllegalArgumentException wrongNumber)
            {
                GeneralController.sf.getTextAreaLogs().setText("Введите число от 1 до 12");
                wrongNumber.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return true;

    }


    public static boolean secretaryShowAttendance (String idWorkerStr, String filterStr, String filterDayStr, String sorterStr) {

        Integer idWorkerInt = Integer.parseInt(idWorkerStr);

        try {
            if (filterStr == "без фильтрации") {
                netController.showAttendance(idWorkerInt,filterStr, sorterStr,0);
                return true;
            }
            if (filterStr == "по выбранному дню") {
                Integer filterDayInt = Integer.parseInt(filterDayStr);
                netController.showAttendance(idWorkerInt, filterStr, sorterStr,filterDayInt);
                return true;
            }
        }catch (IOException io){io.printStackTrace();}


        System.out.println("Что-то пошло нет так c фильтрацией");
        return false;

    }

    public  static boolean secretaryDetailReason(String idStr, String idRecordStr, String reasonStr) {

        try {
            Integer id=Integer.parseInt(idStr);
            Integer idRecordInt = Integer.parseInt(idRecordStr);
            netController.detailReason(id,idRecordInt, reasonStr);
        } catch (NumberFormatException notANumber)
        {
            sf.getTextAreaLogs().setText("Введите число.");
        }
        catch (IOException io){io.printStackTrace();}


        return true;

    }

    public static boolean secretaryGiveSchedule(String idWorkerStr) {


        try {
            Integer idWorkerInt = Integer.parseInt(idWorkerStr);
            netController.giveSchedule(idWorkerInt);
        } catch (NumberFormatException notANumber)
        {
            sf.getTextAreaLogs().setText("Введите число.");
        }
        catch (IOException io){io.printStackTrace();}

        return true;

    }

    public static boolean secretarySpecify(String idWorkerStr, String dayStr) {
    try {
        Integer idWorkerInt = Integer.parseInt(idWorkerStr);
        Integer dayInt = Integer.parseInt(dayStr);
        netController.specify(idWorkerInt,dayInt);
    }catch (IOException io){io.printStackTrace();}
        return true;

    }

    public static boolean secretaryExit() {

        sf.dispose();
        System.exit(0);

        return true;

    }

}