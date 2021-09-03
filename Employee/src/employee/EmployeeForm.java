package employee;

import javax.swing.*;
import java.awt.*;

public class EmployeeForm extends Frame {

    Label labelConnect = new Label("Настройка подключения к серверу:");
    Label labelIpPort = new Label("IP/PORT:");
    Label labelWhoIsUse = new Label("Пользователь приложения:");
    Label labelId = new Label("ID:");
    Label labelWho = new Label("ФИО:");
    Label labelIdText = new Label();
    Label labelWhoText = new Label();
    Label labelChoiceAction = new Label("Выберите действие:");

    Button buttonConnect = new Button("Подключиться");
    Button buttonDisconnect = new Button("Отключиться");
    Button buttonExit = new Button("Выход");
    Button buttonKnowResult = new Button("Получить результат");

    Choice choiceAction = new Choice();

    TextField textFieldIp = new TextField();
    TextField textFieldPort = new TextField();
    TextArea textAreaLogs = new TextArea();


    public EmployeeForm () {

        super("Приложение");

        this.setSize(795, 602);
        this.setLayout(null);


        labelConnect.setBounds(10,32,205,20);
        add(labelConnect);
        labelIpPort.setBounds(10,54,52,20);
        add(labelIpPort);
        textFieldIp.setBounds(65,54,100,20);
        textFieldIp.setText("127.0.0.1");
        add(textFieldIp);
        textFieldPort.setBounds(165,54,72,20);
        textFieldPort.setText("8080");
        add(textFieldPort);
        buttonConnect.setBounds(10,78,90,20);
        add(buttonConnect);
        buttonDisconnect.setBounds(102,78,82,20);
        add(buttonDisconnect);
        buttonExit.setBounds(186,78,50,20);
        add(buttonExit);

        labelWhoIsUse.setBounds(10,100,255,20);
        add(labelWhoIsUse);
        labelId.setBounds(10,122,20,20);
        add(labelId);
        labelIdText.setBounds(32,122,30,20);
        labelIdText.setBackground(Color.GRAY);
        add(labelIdText);
        labelWho.setBounds(64,122,35,20);
        add(labelWho);
        labelWhoText.setBounds(101,122,150,20);
        labelWhoText.setBackground(Color.GRAY);
        add(labelWhoText);
        labelChoiceAction.setBounds(10,144,150,20);
        add(labelChoiceAction);

        choiceAction.setBounds(10,166,162,20);
        choiceAction.add("Номинальная зарплата");
        choiceAction.add("Фактическая зарплата");
        add(choiceAction);

        buttonKnowResult.setBounds(10,191,162,20);
        add(buttonKnowResult);
        textAreaLogs.setBounds(10,215,775,376);
        add(textAreaLogs);
        this.setVisible(true);
    }

    public void EmployeeConnected () {

        textFieldIp.setEnabled(false);
        textFieldPort.setEnabled(false);
        buttonConnect.setEnabled(false);
        buttonDisconnect.setEnabled(true);
        buttonExit.setEnabled(false);
        choiceAction.setEnabled(true);
        buttonKnowResult.setEnabled(true);
        labelIdText.setBackground(Color.WHITE);
        labelWhoText.setBackground(Color.WHITE);
    }

    public void EmployeeDisconnected () {

        textFieldIp.setEnabled(true);
        textFieldPort.setEnabled(true);
        buttonConnect.setEnabled(true);
        buttonDisconnect.setEnabled(false);
        buttonExit.setEnabled(true);
        choiceAction.setEnabled(false);
        buttonKnowResult.setEnabled(false);
        labelIdText.setBackground(Color.GRAY);
        labelWhoText.setBackground(Color.GRAY);
        labelIdText.setText("");
        labelWhoText.setText("");

    }

    public Button getButtonConnect() {
        return buttonConnect;
    }

    public Button getButtonDisconnect() {
        return buttonDisconnect;
    }

    public Button getButtonExit() {
        return buttonExit;
    }

    public Button getButtonKnowResult() {
        return buttonKnowResult;
    }

    public Choice getChoiceAction() {
        return choiceAction;
    }

    public TextField getTextFieldIp() {
        return textFieldIp;
    }

    public TextField getTextFieldPort() {
        return textFieldPort;
    }

    public TextArea getTextAreaLogs() {
        return textAreaLogs;
    }

    public Label getLabelIdText() {
        return labelIdText;
    }

    public Label getLabelWhoText() {
        return labelWhoText;
    }

}
