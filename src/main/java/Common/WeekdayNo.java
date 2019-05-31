package Common;

import java.util.Calendar;

//get and set option -d parameters
public class WeekdayNo {

    private static final Weekday[] DAY=
    {
        Weekday.Sunday,
        Weekday.Monday,
        Weekday.Tuesday,
        Weekday.Wednesday,
        Weekday.Thursday,
        Weekday.Friday,
        Weekday.Saturday,

    };

    /***
     * read the parameters of -d,and convert the parameters to a Weekday enum object
     * @param day
     * @return
     * @throws Exception
     */
    public static Weekday getDay(String day)throws  Exception{
        switch (day){
            case "Sun":
                return DAY[0];
            case "Mon":
                return DAY[1];
            case "Tue":
                return DAY[2];
            case "Wed":
                return DAY[3];
            case "Thu":
                return DAY[4];
            case "Fri":
                return DAY[5];
            case "Sat":
                return DAY[6];
            default:
                throw new Exception("日期输入有误:"+day);
        }
    }

    /***
     * get day in a week,Sunday ……
     * @return
     */
    public static Weekday getNowWeekday(){
        Calendar now = Calendar.getInstance();
        int no=now.get(Calendar.DAY_OF_WEEK)-1;
        return DAY[no];
    }
}
