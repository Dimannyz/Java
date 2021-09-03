package UI;

import controllers.GeneralController;

import java.awt.*;

public class ServerForm extends Frame
{
    protected Label labelPort=null;
    protected TextField textFieldPort=null;
    protected Button buttonStart=null;
    protected Button buttonStop=null;
    protected Button buttonExit=null;
    protected TextArea textAreaLogs =null;
    protected ServerFormListener sfl=null;
    public ServerForm()
    {
        labelPort=new Label();
        textFieldPort=new TextField();
        buttonStart=new Button();
        buttonStop=new Button();
        buttonExit=new Button();
        textAreaLogs =new TextArea();
        labelPort.setText("Порт");
        buttonStart.setLabel("Старт");
        buttonStop.setLabel("Стоп");
        buttonExit.setLabel("Выход");
        serverStopped();
        labelPort.setBounds(20,30,50,25);
        textFieldPort.setBounds(75,30,100,25);
        buttonStart.setBounds(20,65,80,25);
        buttonStop.setBounds(110,65,80,25);
        buttonExit.setBounds(200,65,80,25);
        textAreaLogs.setBounds(20,100,250,180);
        setLayout(null);
        add(labelPort);
        add(textFieldPort);
        add(buttonStart);
        add(buttonStop);
        add(buttonExit);
        add(textAreaLogs);
        sfl=new ServerFormListener(this);
        GeneralController.setSf(this);
        GeneralController.setSfl(sfl);
        setSize(300,300);
        setVisible(true);
    }
    public void serverStarted()
    {
        buttonStop.setEnabled(true);
        textFieldPort.setEnabled(false);
        buttonStart.setEnabled(false);
        buttonExit.setEnabled(false);
    }
    public void serverStopped()
    {
        buttonStop.setEnabled(false);
        textFieldPort.setEnabled(true);
        buttonStart.setEnabled(true);
        buttonExit.setEnabled(true);
    }

    public TextField getTextFieldPort()
    {
        return textFieldPort;
    }

    public Button getButtonStart()
    {
        return buttonStart;
    }

    public Button getButtonStop()
    {
        return buttonStop;
    }

    public Button getButtonExit()
    {
        return buttonExit;
    }

    public TextArea getTextAreaLogs() { return textAreaLogs; }
}
