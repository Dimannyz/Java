package starter;

import boss.BossForm;
import boss.BossFormListener;

import java.io.IOException;

public class Main {

    public static void main (String [] arg) throws IOException {

        BossForm bossForm = new BossForm();
        BossFormListener bossFormListener = new BossFormListener(bossForm);

    }

}
