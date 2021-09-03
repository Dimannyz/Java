package scheme;

import controllers.GeneralController;

import java.util.ArrayList;

public class Boss extends Person
{
    private static Boss boss=null;
    private Boss(String name)
    {
        super(0,name);
    }
    public static Boss setBoss(String name)
    {
        if (boss==null) return boss=new Boss(name);
                else return boss;
    }

    public static Boss getBoss() { return boss; }
}
