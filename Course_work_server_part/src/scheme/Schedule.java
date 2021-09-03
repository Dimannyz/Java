package scheme;

import exceptions.DateNotFoundException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Schedule
{
    private ArrayList<DateInfo> dates=new ArrayList<>();
    //Рабочий день
    private int fines;
    private static final int WORKDAY=0;
    //Выходной
    private static final int DAYOFF=1;
    //Отпуск
    private static final int HOLIDAY=2;
    private final static int YEAR=2021;
    private final static DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("d'-'M'-'yyyy");
    public Schedule(int month) throws ParseException {
        if (month<1 || month>12) throw new IllegalArgumentException("Неверно введен месяц.");
        else {
            fines=0;
            Calendar dateBegin = GregorianCalendar.getInstance();
            dateBegin.set(YEAR, month-1, 1);
            Calendar dateEnd = GregorianCalendar.getInstance();
            if (dateBegin.get(Calendar.MONTH)==Calendar.JANUARY) dateEnd.set(YEAR,month-1,31);
            else dateEnd.set(YEAR, month, 1);
            while (dateBegin.before(dateEnd)) {
                String strDay = String.valueOf(dateBegin.get(Calendar.DAY_OF_MONTH));
                String strMonth = String.valueOf(dateBegin.get(Calendar.MONTH) + 1);
                String strYear = String.valueOf(YEAR);
                if ((dateBegin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (dateBegin.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))
                    dates.add(new DateInfo(LocalDate.parse(strDay + '-' + strMonth + '-' + strYear, dateFormat), DAYOFF));
                else
                    dates.add(new DateInfo(LocalDate.parse(strDay + '-' + strMonth + '-' + strYear, dateFormat), WORKDAY));
                dateBegin.add(Calendar.DAY_OF_MONTH, 1);
            }
            }
    }
    public Schedule(){}
    public static ArrayList<Schedule> defaultSchedule()
    {
        ArrayList<Schedule> schedules=new ArrayList<>(12);
        for (int month=1;month<=12;month++)
        {
            try {
                Schedule schedule=new Schedule(month);
                schedules.add(schedule);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return schedules;
    }

    public String getInfo()
    {
        StringBuilder out=new StringBuilder();
        out.append(fines).append(CSVStorage.getSeparator());
        for (DateInfo date:dates)
        {
            out.append(date.getInfo());
            out.append(CSVStorage.getSeparator());
        }
        return out.toString();
    }
   public DateInfo getDate(String lDate) throws DateNotFoundException
   {
       LocalDate formattedDate=LocalDate.parse(lDate,dateFormat);
       for (DateInfo date:this.dates)
       {
           if (date.getDate().equals(formattedDate)) return date;
       }
       throw new DateNotFoundException();
   }

    public static int getYEAR() { return YEAR; }

    public static DateTimeFormatter getDateFormat() { return dateFormat; }

    public ArrayList<DateInfo> getDates() { return dates; }

    public int getFines() {
        return fines;
    }

    public void setFines(int fines) {
        this.fines = fines;
    }
}
