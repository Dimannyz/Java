package secretary;

import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SecretaryForm extends Frame {

    Label labelConnect = new Label("Настройка подключения к серверу:");
    Label labelIpPort = new Label("IP/PORT:");
    Label labelWhoIsUse = new Label("Пользователь приложения:");
    Label labelId = new Label("ID:");
    Label labelIdText = new Label();
    Label labelWho = new Label("ФИО:");
    Label labelWhoText = new Label();
    Label labelChoiceAction = new Label("Выберите действие:");
    Label labelMonth = new Label("Месяц:");
    Label labelIdEmployeeF = new Label("ID сотрудника:");
    Label labelFilter = new Label("Отфильтровать:");
    Label labelSorter = new Label("Отсортировать:");
    Label labelIdRecord = new Label("ID записи:");
    Label labelReason = new Label("Причина:");
    Label labelIdEmployeeS = new Label("ID сотрудника:");
    Label labelDay = new Label("День:");

    Button buttonConnect = new Button("Подключиться");
    Button buttonDisconnect = new Button("Отключиться");
    Button buttonExit = new Button("Выход");

    Button buttonShowSchedule = new Button("Просмотреть расписание");
    Button buttonShowAttendance = new Button("Получить записи проверок на РМ");
    Button buttonReason = new Button("Детализировать");
    Button buttonGiveSchedule = new Button("Получить расписание на текущий месяц");
    Button buttonSetDay = new Button("Сделать выходным для сотрудника в этом месяце");

    TextField textFieldIp = new TextField();
    TextField textFieldPort = new TextField();
    TextArea textAreaLogs = new TextArea();

    TextField textFieldMonth = new TextField();
    TextField textFieldIdEmployeeF = new TextField();
    TextField textFieldFilterDay = new TextField();
    TextField textFieldIdRecord = new TextField();
    TextField textFieldReason = new TextField();
    TextField textFieldIdEmployeeS = new TextField();

    Choice choiceAction = new Choice();
    Choice choiceDateSchedule = new Choice();
    Choice choiceFilterDay = new Choice();
    Choice choiceSorter = new Choice();
    Choice choiceDay = new Choice();

    public SecretaryForm() {

        super("Приложение");

        this.setSize(795, 602);
        this.setLayout(null);
        this.setVisible(true);

        choiceDateSchedule.setVisible(false);
        labelMonth.setVisible(false);
        textFieldMonth.setVisible(false);
        buttonShowSchedule.setVisible(false);

        labelIdEmployeeF.setVisible(false);
        textFieldIdEmployeeF.setVisible(false);
        buttonShowAttendance.setVisible(false);
        labelFilter.setVisible(false);
        choiceFilterDay.setVisible(false);
        textFieldFilterDay.setVisible(false);
        labelSorter.setVisible(false);
        choiceSorter.setVisible(false);
        labelIdRecord.setVisible(false);
        textFieldIdRecord.setVisible(false);
        labelReason.setVisible(false);
        textFieldReason.setVisible(false);
        buttonReason.setVisible(false);

        labelIdEmployeeS.setVisible(false);
        textFieldIdEmployeeS.setVisible(false);
        buttonGiveSchedule.setVisible(false);
        labelDay.setVisible(false);
        choiceDay.setVisible(false);
        buttonSetDay.setVisible(false);

        choiceAction.setEnabled(false);

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
        choiceAction.setBounds(10,164,305,20);
        choiceAction.add("");
        choiceAction.add("Просмотреть штатное расписание сотрудников");
        choiceAction.add("Детализировать причину отсутствия сотрудника");
        choiceAction.add("Уточнить штатное расписание сотрудника");
        add(choiceAction);

        choiceDateSchedule.setBounds(10,190,170,20);
        choiceDateSchedule.add("За текущий год");
        choiceDateSchedule.add("За текущий месяц");
        choiceDateSchedule.add("За произвольный месяц");
        add(choiceDateSchedule);
        labelMonth.setBounds(183,191,40,20);
        add(labelMonth);
        textFieldMonth.setBounds(227,191,40,22);
        add(textFieldMonth);
        buttonShowSchedule.setBounds(10,215,170,22);
        add(buttonShowSchedule);

        labelIdEmployeeF.setBounds(10,190,85,20);
        add(labelIdEmployeeF);
        textFieldIdEmployeeF.setBounds(97,190,25,20);
        add(textFieldIdEmployeeF);
        buttonShowAttendance.setBounds(124,189,200,21);
        add(buttonShowAttendance);
        labelFilter.setBounds(10,215,95,20);
        add(labelFilter);
        choiceFilterDay.add("без фильтрации");
        choiceFilterDay.add("по выбранному дню");
        choiceFilterDay.setBounds(107,214,140,20);
        add(choiceFilterDay);
        textFieldFilterDay.setBounds(249,214,25,23);
        add(textFieldFilterDay);
        labelSorter.setBounds(276,215,90,20);
        add(labelSorter);
        choiceSorter.add("Сначала новые");
        choiceSorter.add("Сначала старые");
        choiceSorter.setBounds(370,214,120,20);
        add(choiceSorter);
        labelIdRecord.setBounds(10,239,60,20);
        add(labelIdRecord);
        textFieldIdRecord.setBounds(72,240,30,20);
        add(textFieldIdRecord);
        labelReason.setBounds(104,239,55,20);
        add(labelReason);
        textFieldReason.setBounds(161,240,50,20);
        add(textFieldReason);
        buttonReason.setBounds(213,239,110,21);
        add(buttonReason);

        labelIdEmployeeS.setBounds(10,190,85,20);
        add(labelIdEmployeeS);
        textFieldIdEmployeeS.setBounds(97,190,25,20);
        add(textFieldIdEmployeeS);
        buttonGiveSchedule.setBounds(124,189,260,21);
        add(buttonGiveSchedule);
        labelDay.setBounds(10,213,35,20);
        add(labelDay);
        choiceDay.setBounds(47,213,40,20);
        add(choiceDay);
        fillChoiceDay(choiceDay);
        buttonSetDay.setBounds(89,212,295,23);
        add(buttonSetDay);

        textAreaLogs.setBounds(10,264,775,331);
        add(textAreaLogs);
        secretaryDisconnected();
    }

    public void secretaryConnected() {

        labelIdText.setBackground(Color.WHITE);
        labelWhoText.setBackground(Color.WHITE);
        textFieldIp.setEnabled(false);
        textFieldPort.setEnabled(false);
        buttonConnect.setEnabled(false);
        buttonDisconnect.setEnabled(true);
        buttonExit.setEnabled(false);

        choiceAction.setEnabled(true);

    }

    public void secretaryDisconnected() {

        labelIdText.setBackground(Color.GRAY);
        labelWhoText.setBackground(Color.GRAY);
        labelIdText.setText("");
        labelWhoText.setText("");
        textFieldIp.setEnabled(true);
        textFieldPort.setEnabled(true);
        buttonConnect.setEnabled(true);
        buttonDisconnect.setEnabled(false);
        buttonExit.setEnabled(true);

        choiceDateSchedule.setVisible(false);
        labelMonth.setVisible(false);
        textFieldMonth.setVisible(false);
        buttonShowSchedule.setVisible(false);

        labelIdEmployeeF.setVisible(false);
        textFieldIdEmployeeF.setVisible(false);
        buttonShowAttendance.setVisible(false);
        labelFilter.setVisible(false);
        choiceFilterDay.setVisible(false);
        textFieldFilterDay.setVisible(false);
        labelSorter.setVisible(false);
        choiceSorter.setVisible(false);
        labelIdRecord.setVisible(false);
        textFieldIdRecord.setVisible(false);
        labelReason.setVisible(false);
        textFieldReason.setVisible(false);
        buttonReason.setVisible(false);

        labelIdEmployeeS.setVisible(false);
        textFieldIdEmployeeS.setVisible(false);
        buttonGiveSchedule.setVisible(false);
        labelDay.setVisible(false);
        choiceDay.setVisible(false);
        buttonSetDay.setVisible(false);

        choiceAction.select("");

        choiceAction.setEnabled(false);

    }
    public void fillChoiceDay(Choice choiceDay)
    {
        Calendar dateBegin = GregorianCalendar.getInstance();
        dateBegin.set(2021, LocalDate.now().getMonthValue()-1, 1);
        Calendar dateEnd = GregorianCalendar.getInstance();
        dateEnd.set(2021,LocalDate.now().getMonthValue(),1);
        while (dateBegin.before(dateEnd)) {
            choiceDay.add(String.valueOf(dateBegin.get(Calendar.DAY_OF_MONTH)));
            dateBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
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

    public Label getLabelIdText() {
        return labelIdText;
    }

    public Label getLabelWhoText() {
        return labelWhoText;
    }

    public TextArea getTextAreaLogs() {
        return textAreaLogs;
    }

    public Label getLabelConnect() {
        return labelConnect;
    }

    public Label getLabelIpPort() {
        return labelIpPort;
    }

    public Label getLabelWhoIsUse() {
        return labelWhoIsUse;
    }

    public Label getLabelId() {
        return labelId;
    }

    public Label getLabelWho() {
        return labelWho;
    }

    public Label getLabelChoiceAction() {
        return labelChoiceAction;
    }

    public Label getLabelMonth() {
        return labelMonth;
    }

    public Button getButtonShowSchedule() {
        return buttonShowSchedule;
    }

    public TextField getTextFieldMonth() {
        return textFieldMonth;
    }

    public Choice getChoiceAction() {
        return choiceAction;
    }

    public Choice getChoiceDateSchedule() {
        return choiceDateSchedule;
    }

    public Label getLabelIdEmployeeF() {
        return labelIdEmployeeF;
    }

    public Label getLabelFilter() {
        return labelFilter;
    }

    public Label getLabelSorter() {
        return labelSorter;
    }

    public Label getLabelIdRecord() {
        return labelIdRecord;
    }

    public Label getLabelReason() {
        return labelReason;
    }

    public Button getButtonShowAttendance() {
        return buttonShowAttendance;
    }

    public Button getButtonReason() {
        return buttonReason;
    }

    public TextField getTextFieldIdEmployeeF() {
        return textFieldIdEmployeeF;
    }

    public TextField getTextFieldFilterDay() {
        return textFieldFilterDay;
    }

    public TextField getTextFieldIdRecord() {
        return textFieldIdRecord;
    }

    public TextField getTextFieldReason() {
        return textFieldReason;
    }

    public Choice getChoiceFilterDay() {
        return choiceFilterDay;
    }

    public Choice getChoiceSorter() {
        return choiceSorter;
    }

    public Label getLabelIdEmployeeS() {
        return labelIdEmployeeS;
    }

    public Label getLabelDay() {
        return labelDay;
    }

    public Button getButtonGiveSchedule() {
        return buttonGiveSchedule;
    }

    public Button getButtonSetDay() {
        return buttonSetDay;
    }

    public TextField getTextFieldIdEmployeeS() {
        return textFieldIdEmployeeS;
    }

    public Choice getChoiceDay() {
        return choiceDay;
    }

}