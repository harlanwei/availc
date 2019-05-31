package Common;

import java.util.Calendar;

//get and set option -d parameters
public class WeekdayNo {

    private static final Weekday[] DAY = {
            Weekday.Sunday,
            Weekday.Monday,
            Weekday.Tuesday,
            Weekday.Wednesday,
            Weekday.Thursday,
            Weekday.Friday,
            Weekday.Saturday,
    };

    /***
     * Read the parameters of -d, and convert the parameters to a {@code Weekday} enum object
     */
    public static Weekday getDay(String day) throws Exception {
        switch (day) {
            case "sun":
                return DAY[0];
            case "mon":
                return DAY[1];
            case "tue":
                return DAY[2];
            case "wed":
                return DAY[3];
            case "thu":
                return DAY[4];
            case "fri":
                return DAY[5];
            case "sat":
                return DAY[6];
            default:
                throw new Exception("日期输入有误:" + day);
        }
    }

    /***
     * Get day in a week, e.g. Sunday.
     */
    public static Weekday getNowWeekday() {
        Calendar now = Calendar.getInstance();
        int no = now.get(Calendar.DAY_OF_WEEK) - 1;
        return DAY[no];
    }
}
