package CLI;

import Common.Room;
import Common.Weekday;
import Parser.Parser;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.A;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static Common.ClassNo.getNowClassNo;
import static Common.WeekdayNo.getDay;
import static Common.WeekdayNo.getNowWeekday;

public class CLI implements Runnable {
    @Option(names = {"-h", "--help"},usageHelp = true,
            description = "Displays this help message and quits.")
    private boolean helpRequested = true;

    /**
     * Start in the headless mode means that the browser window would not be shown.
     */
    private boolean headless = true;
    @Option(names = {"--non-headless"}, arity = "0", description = "Start in the headless mode means that the browser window would not be shown. This option turns headless mode off.")
    private void setHeadlessMode(String __) {
        headless = false;
    }

    //which building to choose
//    @Option(names = {"-b", "--building"}, description = "The code of the building, which should be in the format of [zs]-[0-9]+, e.g. x1 (Xueyuanlu Building 1), s3 (Shahe No.3 Teaching Buidling).")
//    private String building;
//
//    //record query rooms
//    private boolean shouldGetAllRoomInTheBuilding = true;
//    private Set<String> rooms;
//
//    @Option(names = {"-r", "--rome"},arity = "0..*", split = ",", description = "value of classrooms")
//    private void setRooms(Set<String> input) {
//        shouldGetAllRoomInTheBuilding = false;
//        rooms = input;
//    }

    @ArgGroup(exclusive =false,multiplicity = "1..2")
    queryRooms inputroom;

    static class queryRooms{
        @Option(names = {"-b", "--building"}, description = "The code of the building, which should be in the format of [zs]-[0-9]+, e.g. x1 (Xueyuanlu Building 1), s3 (Shahe No.3 Teaching Buidling).")
        String building;
        @Option(names = {"-r", "--rome"},arity = "1..*", split = ",", description = "value of classrooms")
        Set<String>rooms;
    }
    //get query weeks
    //default value the weekNo of now
    private int weekStartNum;
    private int weekEndNum;
    private boolean isNowWeekNum = true;

    @Option(names = {"-w", "--week"}, arity = "0...2", split = ",", description = "value of week in this semester,if not input this value,it would be set this week,getting from the school website.")
    private void setWeekNum(int[] t) {
        if (t.length == 0)
            isNowWeekNum = true;
        else if (t.length == 1) {
            weekStartNum = t[0];
            weekEndNum = t[0];
            isNowWeekNum = false;
        } else {
            weekStartNum = t[0];
            weekEndNum = t[1];
            isNowWeekNum = false;
        }
    }

    //record which day is selected
    //if not chosen, set it default value
    private boolean isSetDay = false;
    private String day = "";

    @Option(names = {"-d", "--day"}, arity = "0..1", description = "day of query, e.g. Sun, Mon.")
    private void Setday(String day) {
        this.day = day.toLowerCase();
        isSetDay = true;
    }


    //flag of time options,if input more than one options of time, this value will be set true.
    private boolean wasTimeSet = false;

    //the start and end values is used to record the query time,if not choose an option to set these values, we will set it to the latest time.
    //the range of these values is from 1 to 14.
    private int start = 0;
    private int end = 0;

    @Option(names = {"-t", "--time"}, arity = "1", split = ",", description = "value of class num,range from 1-14")
    void setVarTime(int[] t) {
        if (start != 0 || end != 0)
            wasTimeSet = true;
        start = t[0];
        end = t[1];
    }

    @Option(names = "-am", arity = "0", description = "this option means the query time is 1-5.")
    void setAmTime(int[] t) {
        if (start != 0 || end != 0)
            wasTimeSet = true;
        start = 1;
        end = 5;
    }

    @Option(names = "-pm", arity = "0", description = "this option means the query time is 6-10.")
    void setPmTime(int[] t) {
        if (start != 0 || end != 0)
            wasTimeSet = true;
        start = 6;
        end = 10;
    }

    @Option(names = "-ev", arity = "0", description = "this option means the query time is 11-14.")
    void setEvTime(int[] t) {
        if (start != 0 || end != 0)
            wasTimeSet = true;
        start = 11;
        end = 14;
    }

    /**
     * For outputting classrooms available both in the afternoon and in the evening.
     */
    @Option(names = "-pmx", arity = "0", description = "this option means the query time is 6-14")
    void setPmxTime(int[] t) {
        if (start != 0 || end != 0)
            wasTimeSet = true;
        start = 6;
        end = 14;
    }

    /**
     * Number of results to be shown.
     */
    private int showItemsNum;

    @Option(names = {"-l", "--limit"}, arity = "0..1", defaultValue = "5", description = " number of rooms to show, default value is 5")
    private void setShowItemsNum(String t) {
        if (t.equals(""))
            showItemsNum = 5;
        else
            try {
                showItemsNum = Integer.parseInt(t);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("set showItemsNum = 5");
                showItemsNum = 5;
            }
    }

    @Option(names = "--all", arity = "0", description = "get all the room information")
    private void showAllRooms(String __) {
        showItemsNum = Integer.MAX_VALUE;
    }

    //change query result to Json format data
    private boolean shouldGetJson;

    @Option(names = "--json", arity = "0", description = "get json data")
    private void setShouldGetJson(String tmp) {
        shouldGetJson = true;
        showAllRooms("");
    }

    /***
     * run the function
     * get input from args
     * run the query function
     * show the query results
     */
    @Override
    public void run() {
        try (Parser p = new Parser("mt16151056", "mengtao1219", this.headless)) {
            // stores query results
            Map<String, boolean[]> queryResult;
            Set<String> rooms;
            // run query function
            if (wasTimeSet)
                throw new Exception("you can't select more than one option to set query time");
            if (inputroom.rooms!=null)
                rooms = inputroom.rooms;
            else
                rooms = p.getRoomsInTheBuilding(inputroom.building);


            // choose query mode
            if (isNowWeekNum)
                queryResult = p.isAvailable(rooms);
            else
                queryResult = p.isAvailable(rooms, weekStartNum, weekEndNum);
            if(queryResult.size()==0)
                throw new Exception("没有您要查找的教室，请检查是否输入有误");
            // convert query result to room object
            Set<Room> roomResult = new HashSet<>();
            for (String roomName : queryResult.keySet()) {
                Room tmp = new Room(roomName, queryResult.get(roomName));
                roomResult.add(tmp);
            }

            // get Json data
            if (shouldGetJson) {
                Weekday queryDay;
                if (!isSetDay)
                    queryDay = getNowWeekday();
                else
                    queryDay = getDay(day);
                if (start == 0 && end == 0) {
                    int startAndEnd[] = getNowClassNo();
                    start = startAndEnd[0];
                    end = startAndEnd[1];
                }
                Map<String, Boolean> results = new HashMap<String, Boolean>();
                //query result
                for (Room r : roomResult) {
                    if (r.isAvailable(queryDay, start, end))
                        results.put(r.getName(), true);
                }
                //Json data
                Map<String, Object> json = new HashMap<String, Object>();
                json.put("results", results);
                Gson gson = new Gson();
                String jsonStr = gson.toJson(json);
                System.out.println(jsonStr);
            } else {
                Weekday queryDay;
                if (day.equals("")) {
                    queryDay = getNowWeekday();
                } else
                    // set default value
                    queryDay = getDay(day);
                if (start == 0 && end == 0) {
                    // get default week number
                    int[] startAndEnd = getNowClassNo();
                    start = startAndEnd[0];
                    end = startAndEnd[1];
                }
                if (isNowWeekNum)
                    System.out.println("在本周" + queryDay + ":");
                else {
                    if (weekStartNum != weekEndNum)
                        System.out.println("在" + weekStartNum + "-" + weekEndNum + "周" + queryDay + ":");
                    else
                        System.out.println("在" + weekStartNum + "周" + queryDay + ":");
                }

                if (roomResult.size() == 1)
                    for (Room room : roomResult) {
                        System.out.print(room.getName() + " 在第 " + start + " - " + end + " 节课:");
                        if (room.isAvailable(queryDay, start, end))
                            System.out.println("可以使用");
                        else
                            System.out.println("不能使用");
                    }
                else {
                    System.out.println("在第 " + start + " - " + end + " 节课，可使用的教室如下：");

                    int maximumOutputLength = Math.min(roomResult.size(), showItemsNum);
                    String output = roomResult
                            .stream()
                            .filter(el -> el.isAvailable(queryDay, start, end))
                            .limit(maximumOutputLength)
                            .map(Room::getName)
                            .map(String::toUpperCase)
                            .sorted()
                            .collect(Collectors.joining(", "));
                    System.out.println(output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Turn off the annoying "illegal reflective access" warning by the JRE.
     * Please refer to:
     * https://stackoverflow.com/questions/46454995/how-to-hide-warning-illegal-reflective-access-in-java-9-without-jvm-argument
     */
    private static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }

    public static void main(String[] args) {
        disableWarning();
//        List<String> aurgments= new ArrayList<String>();

        new CommandLine(new CLI()).execute(args);
    }
}