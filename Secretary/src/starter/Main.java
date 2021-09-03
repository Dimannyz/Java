package starter;

import secretary.SecretaryForm;
import secretary.SecretaryFormListener;

public class Main {

    public static void main (String [] arg) {

        SecretaryForm secretaryForm = new SecretaryForm();
        SecretaryFormListener secretaryFormListener = new SecretaryFormListener(secretaryForm);

    }

}