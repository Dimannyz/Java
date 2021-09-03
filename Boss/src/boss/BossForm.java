package boss;

import controllers.GeneralController;

import java.awt.*;

public class BossForm extends Frame {

    Label labelConnect = new Label("Настройка подключения к серверу:");
    Label labelIpPort = new Label("IP/PORT:");
    Label labelWhoIsUse = new Label("Пользователь приложения:");
    Label labelId = new Label("ID:");
    Label labelIdText = new Label();
    Label labelWho = new Label("ФИО:");
    Label labelWhoText = new Label();
    Label labelChoiceAction = new Label("Выберите действие:");
    Label labelFilter = new Label("Фильтрация:");
    Label labelSorter = new Label("Сортировка:");
    Label labelEmployeeID = new Label("ID сотрудника:");
    Label labelFactSalary = new Label("Зарплата:");

    Button buttonConnect = new Button("Подключиться");
    Button buttonDisconnect = new Button("Отключиться");
    Button buttonExit = new Button("Выход");
    Button buttonShow = new Button("Показать информацию");
    Button buttonSetSalary = new Button("Ввод");

    TextField textFieldIp = new TextField();
    TextField textFieldPort = new TextField();
    TextField textFieldEmployeeID = new TextField();
    TextField textFieldFactSalary = new TextField();
    TextArea textAreaLogs = new TextArea();

    Choice choiceAction = new Choice();
    Choice choiceFilter = new Choice();
    Choice choiceSorter = new Choice();

    public BossForm() {

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

        add(labelIdText);
        labelWho.setBounds(64,122,35,20);
        add(labelWho);
        labelWhoText.setBounds(101,122,200,20);

        add(labelWhoText);
        labelChoiceAction.setBounds(10,144,150,20);
        add(labelChoiceAction);
        choiceAction.add("Просмотреть сотрудников");
        choiceAction.add("Скорректировать зарплату");
        choiceAction.setBounds(10,164,180,20);
        add(choiceAction);
        labelFilter.setBounds(10,188,75,20);
        add(labelFilter);
        choiceFilter.add("Все");
        choiceFilter.add("В сети");
        choiceFilter.add("Не в сети");
        choiceSorter.add("от А до Я");
        choiceSorter.add("от Я до А");
        choiceFilter.setBounds(87,188,80,20);
        add(choiceFilter);
        labelSorter.setBounds(10,212,75,20);
        add(labelSorter);
        choiceSorter.setBounds(87,212,80,20);
        add(choiceSorter);
        labelEmployeeID.setBounds(10,188,85,20);
        add(labelEmployeeID);
        textFieldEmployeeID.setBounds(97,188,30,20);
        add(textFieldEmployeeID);
        labelFactSalary.setBounds(10,211,60,20);
        add(labelFactSalary);
        textFieldFactSalary.setBounds(72,212,50,20);
        add(textFieldFactSalary);
        buttonSetSalary.setBounds(124,211,65,20);
        add(buttonSetSalary);
        buttonShow.setBounds(10,236,179,20);
        add(buttonShow);
        textAreaLogs.setBounds(10,260,775,331);
        add(textAreaLogs);

        labelEmployeeID.setVisible(false);
        textFieldEmployeeID.setVisible(false);
        labelFactSalary.setVisible(false);
        textFieldFactSalary.setVisible(false);
        buttonSetSalary.setVisible(false);
        BossDisconnected();
        this.setVisible(true);
    }

    public void BossConnected() {

        labelIdText.setBackground(Color.WHITE);
        labelWhoText.setBackground(Color.WHITE);
        textFieldIp.setEnabled(false);
        textFieldPort.setEnabled(false);
        buttonConnect.setEnabled(false);
        buttonDisconnect.setEnabled(true);
        buttonExit.setEnabled(false);

        choiceAction.setEnabled(true);

        labelEmployeeID.setEnabled(true);
        textFieldEmployeeID.setEnabled(true);
        labelFactSalary.setEnabled(true);
        textFieldFactSalary.setEnabled(false);
        buttonSetSalary.setEnabled(false);

        labelFilter.setEnabled(true);
        labelSorter.setEnabled(true);
        choiceFilter.setEnabled(true);
        choiceSorter.setEnabled(true);

        buttonShow.setEnabled(true);

    }

    public void BossDisconnected() {

        labelIdText.setBackground(Color.GRAY);
        labelWhoText.setBackground(Color.GRAY);
        labelIdText.setText("");
        labelWhoText.setText("");
        textFieldIp.setEnabled(true);
        textFieldPort.setEnabled(true);
        buttonConnect.setEnabled(true);
        buttonDisconnect.setEnabled(false);
        buttonExit.setEnabled(true);

        choiceAction.setEnabled(false);

        labelEmployeeID.setEnabled(false);
        textFieldEmployeeID.setEnabled(false);
        labelFactSalary.setEnabled(false);
        textFieldFactSalary.setEnabled(false);
        buttonSetSalary.setEnabled(false);

        labelFilter.setEnabled(false);
        labelSorter.setEnabled(false);
        choiceFilter.setEnabled(false);
        choiceSorter.setEnabled(false);

        buttonShow.setEnabled(false);

    }
    public void salaryMode()
    {
        getButtonSetSalary().setEnabled(true);
        getTextFieldEmployeeID().setEnabled(false);
        getButtonShow().setEnabled(false);
        getTextFieldFactSalary().setEnabled(true);
    }
    public void informationMode()
    {
        getButtonSetSalary().setEnabled(false);
        getTextFieldEmployeeID().setEnabled(true);
        getButtonShow().setEnabled(true);
        getTextFieldFactSalary().setEnabled(false);
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

    public TextField getTextFieldIp() {
        return textFieldIp;
    }

    public TextField getTextFieldPort() {
        return textFieldPort;
    }

    public TextArea getTextAreaLogs() {
        return textAreaLogs;
    }

    public Button getButtonShow() {
        return buttonShow;
    }

    public Button getButtonSetSalary() {
        return buttonSetSalary;
    }

    public TextField getTextFieldEmployeeID() {
        return textFieldEmployeeID;
    }

    public TextField getTextFieldFactSalary() {
        return textFieldFactSalary;
    }

    public Choice getChoiceAction() {
        return choiceAction;
    }

    public Choice getChoiceFilter() {
        return choiceFilter;
    }

    public Choice getChoiceSorter() {
        return choiceSorter;
    }

    public Label getLabelIdText() {
        return labelIdText;
    }

    public Label getLabelWhoText() {
        return labelWhoText;
    }
}