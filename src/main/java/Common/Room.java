package Common;

import org.checkerframework.checker.units.qual.C;

import java.util.Arrays;
import java.util.Objects;

import static Common.ClassNo.getNowClassNo;
import static Common.WeekdayNo.getNowWeekday;

public class Room {

    //基本属性用于记录教室的基本属性
    private String building;
    private String room;
    //缓存一周内的教室占用情况，若可用来自习，则应将值设为true，反之设为false。
    private boolean[][] isAvailable;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    //属性的getter，setter

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean[][] getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean[][] isAvailable) {
        this.isAvailable = isAvailable;
    }


    /***
     * convert the option -b parameters to its full name
     * @param sn
     * @return full name
     */
    private String convertBuildingShortNametoFullname(String sn) {
        switch (sn){
            case "x1" : return "Xuyuanlu 1";
            case "x3" : return "Xuyuanlu 4";
            case "x7" : return "Xueyuanlu Main Building";
            case "x8" : return "Xueyuanlu New Main Building";
            case "s1" : return "Shahe J1";
            case "s3" : return "Shahe J3";
            case "s4" : return "Shahe J4";
            case "s5" : return "Shahe 5";
            default:
                return "error!";//this is impossible to happen
        }
    }

    /***
     * set the information for the classrooms
     * @param building
     * short name of the building
     * @param room
     * @param isAvailable
     * resize the boolean array
     */
    public Room(String building,String room,boolean isAvailable[]) {
        final int DAY_IN_WEEK = 7;
        final int CLASS_IN_DAY = 6;
        this.building = convertBuildingShortNametoFullname(building);
        this.room = room;
        this.isAvailable = new boolean[DAY_IN_WEEK][CLASS_IN_DAY];
        for(int i=0;i<DAY_IN_WEEK;i++){
            for(int j=0;j<CLASS_IN_DAY;j++){
                this.isAvailable[i][j] = isAvailable[i*CLASS_IN_DAY+j];
            }
        }
    }

    /***
     * reset the class no from 1-14 to 0-5(long class consists of two or three classes)
     * @param val
     * @return
     */
    private int resetStartAndEndValue(int val){
        switch (val){
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
            case 5:
                return 1;
            case 6:
            case 7:
                return 2;
            case 8:
            case 9:
            case 10:
                return 3;
            case 11:
            case 12:
                return 4;
            case 13:
            case 14:
                return 5;
            default:
                return -1;
        }
    }


    /***
     * 这段函数用于查询在指定时间段内，教室是否处于自习状态，若可以自习，则返回true,否则返回false。
     * 若输入日期不合法，则抛出异常。
     * 查询某一天的可使用情况
     * @param weekday
     * @param start
     * @param end
     * @return
     * @throws IllegalArgumentException
     */
    public boolean isAvailable(Weekday weekday, int start, int end) throws IllegalArgumentException{
        start = resetStartAndEndValue(start);
        end = resetStartAndEndValue(end);
        if(start >5 || start <0 || end >5 || end<0 || start > end)
            throw new IllegalArgumentException("输入参数有误!");
        else
        {
            int day = -1;
            switch (weekday) {
                case Sunday:
                    day = 0;
                    break;
                case Monday:
                    day = 1;break;
                case Tuesday:
                    day = 2;break;
                case Wednesday:
                    day = 3;break;
                case Thursday:
                    day = 4;break;
                case Friday:
                    day = 5;break;
                case Saturday:
                    day = 6;break;
            }
            for(int i=start;i<=end;i++) {
                if (!isAvailable[day][i])
                    return false;
            }
            return true;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room1 = (Room) o;
        return Objects.equals(building, room1.building) &&
                Objects.equals(getRoom(), room1.getRoom()) &&
                Arrays.equals(getIsAvailable(), room1.getIsAvailable());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(building, getRoom());
        result = 31 * result + Arrays.hashCode(getIsAvailable());
        return result;
    }
}