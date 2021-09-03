package checkForm;

import controllers.GeneralController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckFormListener implements ActionListener
{
    public static CheckForm cf=null;
    public CheckFormListener(CheckForm cf)
    {
        CheckFormListener.cf =cf;
        cf.getCheckButton().addActionListener(this);
        cf.getCheckButtonExit().addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==cf.getCheckButton())
        {
            GeneralController.checkOut(cf.getTextCheckOutput().getText());
        }
        if (e.getSource()==cf.getCheckButtonExit())
        {
            GeneralController.setCf(null);
            cf.dispose();
        }

    }
}
