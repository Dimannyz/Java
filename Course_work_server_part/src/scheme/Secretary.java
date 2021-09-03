package scheme;


import java.text.ParseException;

public class Secretary extends Person
{
    private static Secretary secretary=null;
    private Secretary(String name)
    {
        super(1,name);
    }
    public static Secretary setSecretary(String name)
    {
        if (secretary==null) return secretary=new Secretary(name);
        else return secretary;
    }

    public static Secretary getSecretary() { return secretary; }

    public Schedule getSchedule(int month)
    {
        try {
            return new Schedule(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getInfo() {
        return super.getInfo();
    }
}
