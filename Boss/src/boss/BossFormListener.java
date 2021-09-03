package boss;

import controllers.GeneralController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class BossFormListener implements ActionListener, ItemListener {

    public static BossForm bf = null;

    public BossFormListener(BossForm bf)
    {

        BossFormListener.bf = bf;

        bf.getButtonExit().addActionListener(this);
        bf.getButtonConnect().addActionListener(this);
        bf.getButtonDisconnect().addActionListener(this);
        bf.getChoiceAction().addItemListener(this);
        bf.getButtonShow().addActionListener(this);
        bf.getButtonSetSalary().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bf.getButtonExit()) {

            GeneralController.bossExit();

        }

        if (e.getSource() == bf.getButtonConnect())
        {

            if (bf.getTextFieldPort().getText().isEmpty() || bf.getTextFieldIp().getText().isEmpty()) {

                bf.getTextAreaLogs().append("Отсутствуют данные в поле IP/PORT" + "\n");
                throw new IllegalArgumentException("Отсутствуют данные в поле IP/PORT");

            }

            String portStr = bf.getTextFieldPort().getText();

            if (!(portStr.matches("^\\d+$"))) {

                bf.getTextAreaLogs().append("Число из поля PORT не соответствует формату: Num" + "\n");
                throw new IllegalArgumentException("Число из поля PORT не соответствует формату: Num");

            }

            String ipStr = bf.getTextFieldIp().getText();

            if (!(ipStr.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$"))) {

                bf.getTextAreaLogs().append("Число из поля IP не соответствует формату: Num.Num.Num.Num" + "\n");
                throw new IllegalArgumentException("Число из поля IP не соответствует формату: Num.Num.Num.Num");

            }

            String[] numbers = ipStr.split("\\.");

            for (String number : numbers) {

                if ( ((Integer.parseInt(number)) < 0) || ((Integer.parseInt(number)) > 255) ) {

                    bf.getTextAreaLogs().append("Число из поля IP не соответствует диапазону: 0 < Num < 255" + "\n");
                    throw new IllegalArgumentException("Число из поля IP не соответствует диапазону: 0 < Num < 255");

                }

            }

            /*
            String idStr = bf.getTextFieldEmployeeID().getText();

            if (!(idStr.matches("^\\d+$"))) {

                bf.getTextAreaLogs().append("Число из поля Месяц не соответствует формату: Num" + "\n");
                throw new IllegalArgumentException("Число из поля Месяц не соответствует формату: Num");

            }
            */

            try {

                int portInt = Integer.parseInt(portStr);

                if (((portInt < 0) || (portInt > 65535))) {

                    bf.getTextAreaLogs().append("Число из поля PORT не соответствует диапазону: 0 < Num < 65535" + "\n");
                    throw new IllegalArgumentException("Число из поля PORT не соответствует диапазону: 0 < Num < 65535");

                }

                GeneralController.bossConnect(portInt);



            }
            catch (IOException ex) {

                bf.getTextAreaLogs().append("Исключение IOException" + "\n");
                throw new IllegalArgumentException("Исключение IOException");

            }

        }

        if (e.getSource() == bf.getButtonDisconnect())
        {

            try {

                GeneralController.bossDisconnect();

            }
            catch (IOException ioException) { ioException.printStackTrace(); }

        }
        if ((e.getSource()==bf.getButtonShow())&&(bf.getChoiceAction().getSelectedItem().equals("Просмотреть сотрудников")))
        {
            String filter=bf.getChoiceFilter().getSelectedItem();
            String sort=bf.getChoiceSorter().getSelectedItem();
            GeneralController.viewWorkers(filter,sort);
        }
        if ((e.getSource()==bf.getButtonShow())&&(bf.getChoiceAction().getSelectedItem().equals("Скорректировать зарплату")))
        {
            String strId=bf.getTextFieldEmployeeID().getText();
            GeneralController.getWorkerInfo(strId);
        }
        if (e.getSource()==bf.getButtonSetSalary())
        {
            String strId=bf.getTextFieldEmployeeID().getText();
            String strSalary=bf.getTextFieldFactSalary().getText();
            GeneralController.updateSalary(strSalary,strId);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if((e.getSource() == bf.choiceAction) && (bf.choiceAction.getSelectedItem().equals("Просмотреть сотрудников"))) {

            bf.labelEmployeeID.setVisible(false);
            bf.textFieldEmployeeID.setVisible(false);
            bf.labelFactSalary.setVisible(false);
            bf.textFieldFactSalary.setVisible(false);
            bf.buttonSetSalary.setVisible(false);

            bf.labelFilter.setVisible(true);
            bf.labelSorter.setVisible(true);
            bf.choiceFilter.setVisible(true);
            bf.choiceSorter.setVisible(true);

        }

        if((e.getSource() == bf.choiceAction) && (bf.choiceAction.getSelectedItem().equals("Скорректировать зарплату"))) {

            bf.labelFilter.setVisible(false);
            bf.labelSorter.setVisible(false);
            bf.choiceFilter.setVisible(false);
            bf.choiceSorter.setVisible(false);

            bf.labelEmployeeID.setVisible(true);
            bf.textFieldEmployeeID.setVisible(true);
            bf.labelFactSalary.setVisible(true);
            bf.textFieldFactSalary.setVisible(true);
            bf.buttonSetSalary.setVisible(true);

        }

    }
}