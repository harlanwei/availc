package Common;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *convert time to classNo
 */
public class ClassNo {

    /***
     * convert String to date
     * @param time value of input
     * @return date
     */
    private static Date getTime(String time){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d;
        try{
            d= df.parse(time);
            return d;
        }catch (Exception e){
            //this is impossible to throw an exception , so do nothing.
        }
        return null;
    }

    /***
     * store class time
     */
    private static final Date[] classTime={
            getTime("8:00"),
            getTime("8:45"),
            getTime("9:35"),
            getTime("10:35"),
            getTime("11:25"),
            getTime("12:15"),
            getTime("14:45"),
            getTime("15:35"),
            getTime("16:35"),
            getTime("17:25"),
            getTime("18:15"),
            getTime("19:45"),
            getTime("20:35"),
            getTime("21:25"),
            getTime("22:15")
    };

    /***
     * get now time and calculate the class number
     * 8:00-9:35 class num = 1-2,return 1-2
     * 9:35-12:15,class num = 3-5,return 3-5
     * 12:15-15:35,class num = 6-7,return 6-7
     * 15:35-18:15,class num =8-10,return 8-10
     * 18:15-20:35,class num = 11-12,return 11-12
     * 20:35-22:15,class num = 13-14,return 13-14
     * besides these time above, return -1,because the class rooms are closed
     * @return num of class
     */
    public static int[] getNowClassNo(){
        int[] res = new int[2];
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date nowDate = new Date();
        try{
            nowDate = df.parse(df.format(new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowDate);
        Calendar cpr = Calendar.getInstance();
        int len = classTime.length;
        int start = -1;
        for(int i=0;i<len;i++){
            cpr.setTime(classTime[i]);
            if(date.before(cpr)){
                start = i-1;
                break;
            }
        }
        switch (start){
            case 0: case 1:
                res[0]=1;res[1]=2;break;
            case 3: case 4: case 5:
                res[0]=3;res[1]=5;break;
            case 6: case 7:
                res[0]=6;res[1]=7;break;
            case 8: case 9: case 10:
                res[0]=8;res[1]=10;break;
            case 11:case 12:
                res[0]=11;res[1]=12;break;
            case 13: case 14:
                res[0]=13;res[1]=14;break;
        }
        return res;
    }

}
