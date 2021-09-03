package scheme;

public class Person
{
    private int id;
    private String name;
    protected Person(int id,String name)
    {
        this.id=id;
        this.name=name;
    }
    public String getInfo()
    {
        return String.valueOf(id)+CSVStorage.getSeparator()+name;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
