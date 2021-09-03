package scheme;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Record
{
    private int id;
    private int employeeId;
    private GregorianCalendar dateTime;
    private boolean isPassedTest;
    private String note=null;
    private boolean isSaved=false;
    public Record(int employeeId,boolean isPassedTest)
    {
        this.employeeId=employeeId;
        dateTime=new GregorianCalendar();
        this.isPassedTest=isPassedTest;
    }
    public Record(int employeeId,boolean isPassedTest,String note)
    {
        this(employeeId,isPassedTest);
        this.note=note;
    }
    //Для чтения из файла
    public Record(int id,int employeeId,GregorianCalendar dateTime,boolean isPassedTest,String note)
    {
        this.id=id;
        this.employeeId=employeeId;
        this.dateTime=dateTime;
        this.isPassedTest=isPassedTest;
        this.note=note;
    }
    public String getInfo()
    {
        String data=String.valueOf(dateTime.get(Calendar.DAY_OF_MONTH))+' '+ (dateTime.get(Calendar.MONTH)+1)+' '
                +dateTime.get(Calendar.YEAR)+' '+dateTime.get(Calendar.HOUR_OF_DAY)+':'+dateTime.get(Calendar.MINUTE)+':'+dateTime.get(Calendar.SECOND);
        return String.valueOf(id)+CSVStorage.getSeparator()+String.valueOf(employeeId)+CSVStorage.getSeparator()
                +data+CSVStorage.getSeparator()+isPassedTest+CSVStorage.getSeparator()+note;
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public boolean isPassedTest() {
        return isPassedTest;
    }

    public String getNote() {
        return note;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public GregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setId(int id) {
        this.id = id;
    }
}
