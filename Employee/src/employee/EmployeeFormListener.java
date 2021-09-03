package employee;

import controllers.GeneralController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class EmployeeFormListener implements ActionListener {

    public static EmployeeForm ef = null;

    public EmployeeFormListener (EmployeeForm ef)
    {

        this.ef = ef;

        ef.EmployeeDisconnected();

        ef.getButtonExit().addActionListener(this);
        ef.getButtonConnect().addActionListener(this);
        ef.getButtonDisconnect().addActionListener(this);
        ef.getButtonKnowResult().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ef.getButtonExit()) {

            GeneralController.employeeExit();

        }

        if (e.getSource() == ef.getButtonConnect())
        {

            if (ef.getTextFieldPort().getText().isEmpty() || ef.getTextFieldIp().getText().isEmpty()) {

                ef.getTextAreaLogs().append("Отсутствуют данные в поле IP/PORT" + "\n");
                throw new IllegalArgumentException("Отсутствуют данные в поле IP/PORT");

            }

            String portStr = ef.getTextFieldPort().getText();

            if (!(portStr.matches("^\\d+$"))) {

                ef.getTextAreaLogs().append("Введенное в поле PORT не соответствует формату: Num" + "\n");
                throw new IllegalArgumentException("Введенное в поле PORT не соответствует формату: Num");

            }

            String ipStr = ef.getTextFieldIp().getText();

            if (!(ipStr.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$"))) {

                ef.getTextAreaLogs().append("Число из поля IP не соответствует формату: Num.Num.Num.Num" + "\n");
                throw new IllegalArgumentException("Число из поля IP не соответствует формату: Num.Num.Num.Num");

            }

            String[] numbers = ipStr.split("\\.");

            for (String number : numbers) {

                if ( ((Integer.parseInt(number)) < 0) || ((Integer.parseInt(number)) > 255) ) {

                    System.out.println(number);

                    ef.getTextAreaLogs().append("Число из поля IP не соответствует диапазону: 0 < Num < 255" + "\n");
                    throw new IllegalArgumentException("Число из поля IP не соответствует диапазону: 0 < Num < 255");

                }

            }

            try {

                int portInt = Integer.parseInt(portStr);

                if (((portInt < 0) || (portInt > 65535))) {

                    ef.getTextAreaLogs().append("Число из поля PORT не соответствует диапазону: 0 < Num < 65535" + "\n");
                    throw new IllegalArgumentException("Число из поля PORT не соответствует диапазону: 0 < Num < 65535");

                }

                GeneralController.employeeConnect(portInt);

            }
            catch (IOException ex) {

                ef.getTextAreaLogs().append("Исключение IOException" + "\n");
                throw new IllegalArgumentException("Исключение IOException");

            }

        }

        if (e.getSource() == ef.getButtonDisconnect())
        {

            try {

                GeneralController.employeeDisconnect();

            }
            catch (IOException ioException) { ioException.printStackTrace(); }

            ef.EmployeeDisconnected();

        }
        if ((e.getSource()==ef.getButtonKnowResult()) && (ef.choiceAction.getSelectedItem().equals("Номинальная зарплата")))
        {
            GeneralController.getNominalSalary();
            ef.getTextAreaLogs().setText("Запрос на получение номинальной зарплаты отправлен.\n");
        }
        if ((e.getSource()==ef.getButtonKnowResult()) && (ef.choiceAction.getSelectedItem().equals("Фактическая зарплата")))
        {
            GeneralController.getRealSalary();
            ef.getTextAreaLogs().setText("Запрос на получение реальной зарплаты отправлен.\n");
        }
    }

}
