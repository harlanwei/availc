package CLI;

import Common.Room;
import Common.Weekday;
import Parser.Parser;
import com.google.gson.Gson;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static Common.ClassNo.getNowClassNo;
import static Common.WeekdayNo.getDay;
import static Common.WeekdayNo.getNowWeekday;

public class CLI implements Runnable {


    @Option(names = { "-h", "--help" }, usageHelp = true,
            description = "Displays this help message and quits.")
    private boolean helpRequested = false;

    //whether to show head
    private boolean shouldHeadLess = true;
    @Option(names = {"--headless"},arity = "0",description = "headless,default = true")
    private void setShouldHeadLess(String tmp){
        shouldHeadLess = false;
    }

    //which building to choose
    @Option(names = {"-b","--building"},required = true,description = "this value is the name of building,you must input this.for example,it could be x1(xueyuanlu 1),s3(shahe j3)")
    private String building;

    //record query rooms
    private boolean shouldGetAllRoomInTheBuilding = true;
    private Set<String>rooms;
    @Option(names = {"-r","--rome"},arity = "0..*",split = ",",description = "value of classrooms")
    private void setRooms(Set<String> tmp){
        shouldGetAllRoomInTheBuilding = false;
        rooms = tmp;
    }

    //get query weeks
    //default value the weekNo of now
    private int weekStartNum;
    private int weekEndNum;
    private boolean isNowWeekNum = true;
    @Option(names = {"-w","--week"},arity ="0...2",split = ",",description = "value of week in this semester,if not input this value,it would be set this week,getting from the school website.")
    private void setWeekNum(int[] t){
        if(t.length==0)
            isNowWeekNum=true;
        else if(t.length==1){
            weekStartNum = t[0];
            weekEndNum = t[0];
            isNowWeekNum = false;
        }else{
            weekStartNum=t[0];
            weekEndNum=t[1];
            isNowWeekNum = false;
        }
    }

    //record which day is selected
    //if not chosen, set it default value
    private boolean isSetDay = false;
    private String day="";
    @Option(names = {"-d","--day"},arity = "0..1",description = "day of query,Sun,Mon……")
    private void Setday(String day){
        this.day = day;
        isSetDay = true;
    }


    //flag of time options,if input more than one options of time, this value will be set true.
    private boolean iSetTimeAgain=false;

    //the start and end values is used to record the query time,if not choose an option to set these values, we will set it to the latest time.
    //the range of these values is from 1 to 14.
    private int start=0;
    private int end=0;
    @Option(names = {"-t","--time"},arity ="1",split = ",",description = "value of class num,range from 1-14")
    void setVarTime(int[] t){
        if(start != 0 || end !=0)
            iSetTimeAgain = true;
        start = t[0];
        end = t[1];
    }

    @Option(names = "-am",arity="0",description = "this option means the query time is 1-5.")
    void setAmTime(int[] t){
        if(start != 0 || end !=0)
            iSetTimeAgain = true;
        start = 1;
        end = 5;
    }
    @Option(names = "-pm",arity = "0",description = "this option means the query time is 6-10.")
    void setPmTime(int[] t){
        if(start != 0 || end !=0)
            iSetTimeAgain = true;
        start = 6;
        end = 10;
    }

    @Option(names = "-ev",arity = "0",description = "this option means the query time is 11-14.")
    void setEvTime(int[] t){
        if(start != 0 || end !=0)
            iSetTimeAgain = true;
        start = 11;
        end = 14;
    }
    //pm + ev
    @Option(names = "-pmx",arity = "0",description = "this option means the query time is 6-14")
    void setPmxTime(int[] t){
        if(start != 0 || end !=0)
            iSetTimeAgain = true;
        start = 6;
        end = 14;
    }

    //record number of show result
    private int showItemsNum;
    @Option(names = {"-l","--limit"},arity = "0..1",defaultValue = "5",description = " number of rooms to show, default value is 5")
    private void setShowItemsNum(String t){
        if(t.equals(""))
            showItemsNum = 5;
        else
            try {
                showItemsNum = Integer.parseInt(t);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("set showItemsNum = 5");
                showItemsNum = 5;
            }
    }

    //whether to show all the query results
    private boolean shouldShowAllRoom = false;
    @Option(names = "--all",arity = "0",description = "get all the room information")
    void setShouldShowAllRoom(String tmp){
        shouldShowAllRoom = true;
    }

    //change query result to Json format data
    private boolean shouldGetJson;
    @Option(names = "--json",arity = "0",description = "get json data")
    void setShouldGetJson(String tmp){
        shouldGetJson = true;
        shouldShowAllRoom = true;
    }

    /***
     * run function
     * get input from args
     * run query function
     * show query result
     */
    @Override
    public void run(){
        Parser p = new Parser("mt16151056","mengtao1219");
        Map<String,boolean[]> queryResult;//store query result
        //run query function
        try{
            if(iSetTimeAgain)
                throw new Exception("you can't select more than one option to set query time");
            if(shouldGetAllRoomInTheBuilding)
                rooms = p.getRoomsInTheBuilding(building);
            //choose query mode
            if(isNowWeekNum)
                queryResult = p.isAvailable(rooms);
            else
                queryResult = p.isAvailable(rooms,weekStartNum,weekEndNum);
            Set<Room> roomResult = new HashSet<Room>();
            //convert query result to room object
            for(String roomValue : queryResult.keySet()){
                Room tmp = new Room(building,roomValue,queryResult.get(roomValue));
                roomResult.add(tmp);
            }
            //get Json data
            if(shouldGetJson){
                Weekday queryDay;
                if(isSetDay==false)
                    queryDay =getNowWeekday();
                else
                    queryDay = getDay(day);
                if(start==0 && end ==0)
                {
                    int startAndEnd[] =getNowClassNo();
                    start =startAndEnd[0];
                    end =startAndEnd[1];
                }
                Map<String,Boolean> results = new HashMap<String, Boolean>();
                //query result
                for(Room r : roomResult){
                    if(r.isAvailable(queryDay,start,end))
                        results.put(r.getRoom(),true);
                }
                //Json data
                Map<String,Object> json = new HashMap<String,Object>();
                json.put("results",results);
                Gson gson = new Gson();
                String jsonStr = gson.toJson(json);
                System.out.println(jsonStr);
            }
            else {
                Weekday queryDay;
                if(day.equals("")){
                    queryDay = getNowWeekday();
                }
                else
                    // set default value
                    queryDay = getDay(day);
                if(start ==0 && end ==0){
                    //get default week number
                    int startAndEnd[] =getNowClassNo();
                    start =startAndEnd[0];
                    end =startAndEnd[1];
                    //convert get class to 1-14
                }
                if(isNowWeekNum == true)
                    System.out.println("在本周"+queryDay+":");
                else{
                    if(weekStartNum!=weekEndNum)
                        System.out.println("在"+weekStartNum+"-"+weekEndNum+"周"+queryDay+":");
                    else
                        System.out.println("在"+weekStartNum+"周"+queryDay+":");
                }

                if(roomResult.size()==0)
                    System.out.println("没有查询到教室!");
                else {
                    if (roomResult.size() == 1)
                        for (Room r : roomResult) {
                            System.out.print(r.getBuilding() + " " + r.getRoom() + " ");
                            System.out.println("在第" + start + "-" + end + "节课:");
                            if (r.isAvailable(queryDay, start, end))
                                System.out.println("可以使用");
                            else
                                System.out.println("不能使用");
                        }
                    else {
                        System.out.println("在第" + start + "-" + end + "节课:");
                        System.out.println("可使用的教室如下：");
                        int showMin = roomResult.size();
                        if (shouldShowAllRoom == false)
                            showMin = Math.min(roomResult.size(), showItemsNum);
                        for (Room r : roomResult) {
                            if (showMin == -1)
                                break;
                            if (r.isAvailable(queryDay, start, end)) {
                                showMin--;
                                System.out.print(r.getRoom() + "、");
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            p.close();
        }
    }
    public static void main(String[] args) {
        new CommandLine(new CLI()).execute(args);
    }
}