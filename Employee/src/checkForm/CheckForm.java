package checkForm;

import controllers.GeneralController;

import javax.swing.*;
import java.awt.*;

public class CheckForm extends JDialog
{
    Label checkLabel = null;
    TextField textCheckOutput = null;
    Label textLabelInput = null;
    Button checkButton = null;
    Label checkLabelResult=null;
    Button checkButtonExit=null;
    CheckFormListener cfl=null;
    public CheckForm(Frame owner,String title, boolean modal) {
        super(owner, title, modal);
        setBounds(500, 300, 350, 200);
        setLayout(null);
        checkLabel = new Label("Введите указанное число:");
        textCheckOutput = new TextField();
        textLabelInput = new Label();
        checkButton = new Button("Подтвердить");
        checkLabelResult=new Label();
        checkButtonExit=new Button("Выход");
        checkLabelResult.setVisible(false);
        checkButton.setEnabled(true);
        checkButtonExit.setEnabled(false);
        checkLabel.setBounds(10, 10, 150, 30);
        textLabelInput.setBounds(170, 10, 100, 30);
        textCheckOutput.setBounds(170, 50, 100, 30);
        checkButton.setBounds(10, 50, 100, 30);
        checkLabelResult.setBounds(10,90,290,30);
        checkButtonExit.setBounds(10,130,80,30);
        add(textLabelInput);
        add(textCheckOutput);
        add(checkLabel);
        add(checkButton);
        add(checkLabelResult);
        add(checkButtonExit);
        cfl=new CheckFormListener(this);
        GeneralController.setCf(this);
        setVisible(true);
    }
    public void endCheck()
    {
        checkButton.setEnabled(false);
        checkButtonExit.setEnabled(true);
        textCheckOutput.setEnabled(false);
    }

    public Label getCheckLabel() {
        return checkLabel;
    }

    public void setCheckLabel(Label checkLabel) {
        this.checkLabel = checkLabel;
    }

    public TextField getTextCheckOutput() {
        return textCheckOutput;
    }

    public void setTextCheckOutput(TextField textCheckOutput) {
        this.textCheckOutput = textCheckOutput;
    }

    public Label getTextLabelInput() {
        return textLabelInput;
    }

    public void setTextLabelInput(Label textLabelInput) {
        this.textLabelInput = textLabelInput;
    }

    public Button getCheckButton() {
        return checkButton;
    }

    public void setCheckButton(Button checkButton) {
        this.checkButton = checkButton;
    }

    public Label getCheckLabelResult() { return checkLabelResult; }

    public void setCheckLabelResult(Label checkLabelResult) { this.checkLabelResult = checkLabelResult; }

    public Button getCheckButtonExit() { return checkButtonExit; }

    public void setCheckButtonExit(Button checkButtonExit) { this.checkButtonExit = checkButtonExit; }
}
