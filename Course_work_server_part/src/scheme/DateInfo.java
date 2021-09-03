package scheme;

import java.time.LocalDate;

public class DateInfo
{
    private LocalDate date;
    private int status;
    private boolean isAttended;
    DateInfo(LocalDate date, int status)
    {
        this.date=date;
        this.status=status;
        this.isAttended=false;
    }
    DateInfo(LocalDate date, int status,boolean isAttended)
    {
        this.date=date;
        this.status=status;
        this.isAttended=isAttended;
    }
    public String getInfo()
    {
        return String.valueOf(date)+CSVStorage.getSeparator()+status+CSVStorage.getSeparator()+isAttended;
    }
    public LocalDate getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public boolean isAttended() {
        return isAttended;
    }
    public void setStatus(int status) throws IllegalArgumentException{
        if (status>2 || status<0) throw new IllegalArgumentException("Статус дня может быть 0, 1, 2");
        this.status = status;
    }

    public void setAttended(boolean attended) {
        isAttended = attended;
    }
}
