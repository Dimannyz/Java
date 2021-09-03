package secretary;

import controllers.GeneralController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class SecretaryFormListener implements ActionListener, ItemListener {

    public static SecretaryForm sf = null;

    public SecretaryFormListener(SecretaryForm sf)
    {

        this.sf = sf;

        sf.secretaryDisconnected();

        sf.getButtonExit().addActionListener(this);
        sf.getButtonConnect().addActionListener(this);
        sf.getButtonDisconnect().addActionListener(this);
        sf.getChoiceAction().addItemListener(this);
        sf.getChoiceDateSchedule().addItemListener(this);
        sf.getChoiceFilterDay().addItemListener(this);
        sf.getButtonShowSchedule().addActionListener(this);
        sf.getButtonShowAttendance().addActionListener(this);
        sf.getButtonReason().addActionListener(this);
        sf.getButtonGiveSchedule().addActionListener(this);
        sf.getButtonSetDay().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sf.getButtonExit()) {

            GeneralController.secretaryExit();

        }

        if (e.getSource() == sf.getButtonConnect())
        {

            if (sf.getTextFieldPort().getText().isEmpty() || sf.getTextFieldIp().getText().isEmpty()) {

                sf.getTextAreaLogs().append("Отсутствуют данные в поле IP/PORT" + "\n");
                throw new IllegalArgumentException("Отсутствуют данные в поле IP/PORT");

            }

            String portStr = sf.getTextFieldPort().getText();

            if (!(portStr.matches("^\\d+$"))) {

                sf.getTextAreaLogs().append("Число из поля PORT не соответствует формату: Num" + "\n");
                throw new IllegalArgumentException("Число из поля PORT не соответствует формату: Num");

            }

            String ipStr = sf.getTextFieldIp().getText();

            if (!(ipStr.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$"))) {

                sf.getTextAreaLogs().append("Число из поля IP не соответствует формату: Num.Num.Num.Num" + "\n");
                throw new IllegalArgumentException("Число из поля IP не соответствует формату: Num.Num.Num.Num");

            }

            String[] numbers = ipStr.split("\\.");

            for (String number : numbers) {

                if ( ((Integer.parseInt(number)) < 0) || ((Integer.parseInt(number)) > 255) ) {

                    System.out.println(number);

                    sf.getTextAreaLogs().append("Число из поля IP не соответствует диапазону: 0 < Num < 255" + "\n");
                    throw new IllegalArgumentException("Число из поля IP не соответствует диапазону: 0 < Num < 255");

                }

            }

            try {

                int portInt = Integer.parseInt(portStr);

                if (((portInt < 0) || (portInt > 65535))) {

                    sf.getTextAreaLogs().append("Число из поля PORT не соответствует диапазону: 0 < Num < 65535" + "\n");
                    throw new IllegalArgumentException("Число из поля PORT не соответствует диапазону: 0 < Num < 65535");

                }

                GeneralController.secretaryConnect(portInt);

                sf.secretaryConnected();

            }
            catch (IOException ex) {

                sf.getTextAreaLogs().append("Исключение IOException" + "\n");
                throw new IllegalArgumentException("Исключение IOException");

            }

        }

        if (e.getSource() == sf.getButtonDisconnect())
        {

            try {

                GeneralController.secretaryDisconnect();

            }
            catch (IOException ioException) { ioException.printStackTrace(); }

            sf.secretaryDisconnected();

        }

        if (e.getSource() == sf.getButtonShowSchedule())
        {

            String dateStr = sf.getChoiceDateSchedule().getSelectedItem();

            String monthStr = sf.getTextFieldMonth().getText();

            GeneralController.secretaryShowSchedule(dateStr,monthStr);

        }

        if (e.getSource() == sf.getButtonShowAttendance())
        {

            String idWorkerStr = sf.getTextFieldIdEmployeeF().getText();

            String filterStr = sf.getChoiceFilterDay().getSelectedItem();
            String filterDayStr = sf.getTextFieldFilterDay().getText();

            String sorterStr = sf.getChoiceSorter().getSelectedItem();

            GeneralController.secretaryShowAttendance(idWorkerStr,filterStr,filterDayStr,sorterStr);

        }

        if (e.getSource() == sf.getButtonReason())
        {

            String idRecordStr = sf.getTextFieldIdRecord().getText();

            String reasonStr = sf.getTextFieldReason().getText();

            GeneralController.secretaryDetailReason(sf.getTextFieldIdEmployeeF().getText(),idRecordStr,reasonStr);

        }

        if (e.getSource() == sf.getButtonGiveSchedule())
        {

            String idWorkerStr = sf.getTextFieldIdEmployeeS().getText();

            GeneralController.secretaryGiveSchedule(idWorkerStr);

        }

        if (e.getSource() == sf.getButtonSetDay())
        {

            String idWorkerStr = sf.getTextFieldIdEmployeeS().getText();

            String dayStr = sf.getChoiceDay().getSelectedItem();

            GeneralController.secretarySpecify(idWorkerStr,dayStr);

        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if ((e.getSource() == sf.choiceAction) && (sf.choiceAction.getSelectedItem().equals("")))
        {

            sf.getChoiceDateSchedule().setVisible(false);
            sf.getLabelMonth().setVisible(false);
            sf.getTextFieldMonth().setVisible(false);
            sf.getButtonShowSchedule().setVisible(false);

            sf.getLabelIdEmployeeF().setVisible(false);
            sf.getTextFieldIdEmployeeF().setVisible(false);
            sf.getButtonShowAttendance().setVisible(false);
            sf.getLabelFilter().setVisible(false);
            sf.getChoiceFilterDay().setVisible(false);
            sf.getTextFieldFilterDay().setVisible(false);
            sf.getLabelSorter().setVisible(false);
            sf.getChoiceSorter().setVisible(false);
            sf.getLabelIdRecord().setVisible(false);
            sf.getTextFieldIdRecord().setVisible(false);
            sf.getLabelReason().setVisible(false);
            sf.getTextFieldReason().setVisible(false);
            sf.getButtonReason().setVisible(false);

            sf.getLabelIdEmployeeS().setVisible(false);
            sf.getTextFieldIdEmployeeS().setVisible(false);
            sf.getButtonGiveSchedule().setVisible(false);
            sf.getLabelDay().setVisible(false);
            sf.getChoiceDay().setVisible(false);
            sf.getButtonSetDay().setVisible(false);

        }

        if ((e.getSource() == sf.choiceAction) && (sf.choiceAction.getSelectedItem().equals("Просмотреть штатное расписание сотрудников")))
        {

            sf.getLabelIdEmployeeF().setVisible(false);
            sf.getTextFieldIdEmployeeF().setVisible(false);
            sf.getButtonShowAttendance().setVisible(false);
            sf.getLabelFilter().setVisible(false);
            sf.getChoiceFilterDay().setVisible(false);
            sf.getTextFieldFilterDay().setVisible(false);
            sf.getLabelSorter().setVisible(false);
            sf.getChoiceSorter().setVisible(false);
            sf.getLabelIdRecord().setVisible(false);
            sf.getTextFieldIdRecord().setVisible(false);
            sf.getLabelReason().setVisible(false);
            sf.getTextFieldReason().setVisible(false);
            sf.getButtonReason().setVisible(false);

            sf.getLabelIdEmployeeS().setVisible(false);
            sf.getTextFieldIdEmployeeS().setVisible(false);
            sf.getButtonGiveSchedule().setVisible(false);
            sf.getLabelDay().setVisible(false);
            sf.getChoiceDay().setVisible(false);
            sf.getButtonSetDay().setVisible(false);

            sf.getChoiceDateSchedule().setVisible(true);
            sf.getLabelMonth().setVisible(true);
            sf.getTextFieldMonth().setVisible(true);
            sf.getButtonShowSchedule().setVisible(true);

        }

        if ((sf.choiceAction.getSelectedItem().equals("Просмотреть штатное расписание сотрудников")) && (sf.choiceDateSchedule.getSelectedItem().equals("За произвольный месяц")))
        {

            sf.getLabelMonth().setEnabled(true);
            sf.getTextFieldMonth().setEnabled(true);

        }
        else
        {

            sf.getLabelMonth().setEnabled(false);
            sf.getTextFieldMonth().setEnabled(false);

        }

        if ((e.getSource() == sf.choiceAction) && (sf.choiceAction.getSelectedItem().equals("Детализировать причину отсутствия сотрудника")))
        {

            sf.getChoiceDateSchedule().setVisible(false);
            sf.getLabelMonth().setVisible(false);
            sf.getTextFieldMonth().setVisible(false);
            sf.getButtonShowSchedule().setVisible(false);

            sf.getLabelIdEmployeeS().setVisible(false);
            sf.getTextFieldIdEmployeeS().setVisible(false);
            sf.getButtonGiveSchedule().setVisible(false);
            sf.getLabelDay().setVisible(false);
            sf.getChoiceDay().setVisible(false);
            sf.getButtonSetDay().setVisible(false);

            sf.getLabelIdEmployeeF().setVisible(true);
            sf.getTextFieldIdEmployeeF().setVisible(true);
            sf.getButtonShowAttendance().setVisible(true);
            sf.getLabelFilter().setVisible(true);
            sf.getChoiceFilterDay().setVisible(true);
            sf.getTextFieldFilterDay().setVisible(true);
            sf.getLabelSorter().setVisible(true);
            sf.getChoiceSorter().setVisible(true);
            sf.getLabelIdRecord().setVisible(true);
            sf.getTextFieldIdRecord().setVisible(true);
            sf.getLabelReason().setVisible(true);
            sf.getTextFieldReason().setVisible(true);
            sf.getButtonReason().setVisible(true);

        }

        if ((sf.choiceAction.getSelectedItem().equals("Детализировать причину отсутствия сотрудника")) && (sf.choiceFilterDay.getSelectedItem().equals("по выбранному дню")))
        {

            sf.getTextFieldFilterDay().setEnabled(true);

        }
        else
        {

            sf.getTextFieldFilterDay().setEnabled(false);

        }

        if ((e.getSource() == sf.choiceAction) && (sf.choiceAction.getSelectedItem().equals("Уточнить штатное расписание сотрудника")))
        {

            sf.getChoiceDateSchedule().setVisible(false);
            sf.getLabelMonth().setVisible(false);
            sf.getTextFieldMonth().setVisible(false);
            sf.getButtonShowSchedule().setVisible(false);

            sf.getLabelIdEmployeeF().setVisible(false);
            sf.getTextFieldIdEmployeeF().setVisible(false);
            sf.getButtonShowAttendance().setVisible(false);
            sf.getLabelFilter().setVisible(false);
            sf.getChoiceFilterDay().setVisible(false);
            sf.getTextFieldFilterDay().setVisible(false);
            sf.getLabelSorter().setVisible(false);
            sf.getChoiceSorter().setVisible(false);
            sf.getLabelIdRecord().setVisible(false);
            sf.getTextFieldIdRecord().setVisible(false);
            sf.getLabelReason().setVisible(false);
            sf.getTextFieldReason().setVisible(false);
            sf.getButtonReason().setVisible(false);

            sf.getLabelIdEmployeeS().setVisible(true);
            sf.getTextFieldIdEmployeeS().setVisible(true);
            sf.getButtonGiveSchedule().setVisible(true);
            sf.getLabelDay().setVisible(true);
            sf.getChoiceDay().setVisible(true);
            sf.getButtonSetDay().setVisible(true);

        }

    }

}