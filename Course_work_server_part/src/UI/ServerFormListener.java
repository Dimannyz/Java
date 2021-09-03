package UI;

import controllers.GeneralController;
import exceptions.IllegalPortArgumentException;
import exceptions.NullPortException;
import scheme.CSVStorage;
import scheme.ClientTask;
import scheme.Employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerFormListener implements ActionListener
{
    protected ServerForm sf=null;
    public ServerFormListener (ServerForm sf)
    {
        this.sf=sf;
        sf.getButtonStart().addActionListener(this);
        sf.getButtonStop().addActionListener(this);
        sf.getButtonExit().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) throws NullPortException,NumberFormatException,IllegalPortArgumentException
    {
        if (e.getSource()==sf.getButtonExit()) {
            try {
                CSVStorage.saveAllEmployees();
                CSVStorage.saveHeads();
                for (Employee employee: GeneralController.getEmployees())
                {
                    for (ClientTask clientTask: employee.getTasksTime())
                    {
                        clientTask.cancel();
                    }
                }
                System.exit(0);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            sf.dispose();
        }
        if (e.getSource()==sf.getButtonStop())
        {
            GeneralController.stopServer();
        }
        if (e.getSource()==sf.getButtonStart())
        {
            String portStr=sf.getTextFieldPort().getText();
            if (portStr==null)
            {
                sf.getTextAreaLogs().setText(sf.getTextAreaLogs().getText()+"Неверный фомат порта. Введите целое число от 0 до 65535."+'\n');
                throw new NullPortException();
            }
            try {
                int portInt = Integer.parseInt(portStr);
                if ((portInt<0) || (portInt>65535)) throw new IllegalPortArgumentException();
                GeneralController.startServer(portInt);
            }catch (NumberFormatException|IllegalPortArgumentException nfe)
            {
                nfe.printStackTrace();
                sf.getTextAreaLogs().setText(sf.getTextAreaLogs().getText()+"Неверный фомат порта. Введите целое число от 0 до 65535."+'\n');
            }
        }
    }
}