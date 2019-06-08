package Parser;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class ParserTest {
    /**
     * 测试是否能获得教学楼的教室，分为有账号登录和无账号两种情况
     * 正常运行返回教学楼中的所有教室
     * 错误输入无返回
     */
    @Test
    public void TestGetRoomInTheBuilding() {
        try (Parser p = new Parser()) {
            Set<String> classroom = p.getRoomsInTheBuilding("s1");
            assertNotNull(classroom);
            System.out.println(classroom);
            classroom = p.getRoomsInTheBuilding("s3");
            assertNotNull(classroom);
            System.out.println(classroom);
        }
    }

    @Test
    public void TestGetRoomInTheBuildingLogIn() {
        try (Parser p = new Parser("mt16151056", "mengtao1219", true)) {
            Set<String> classroom = p.getRoomsInTheBuilding("s1");
            assertNotNull(classroom);
            System.out.println(classroom);
            classroom = p.getRoomsInTheBuilding("s3");
            assertNotNull(classroom);
            System.out.println(classroom);
        }
    }

    /**
     * 测试判断教室是否为空的功能
     * 正常运行返回true或false
     * 错误输入跑出异常NullPointException
     */
    @Test
    public void TestIsAvailable1() {
        try (Parser p = new Parser("mt16151056", "mengtao1219", true)) {
            Set<String> classroom = new HashSet<>();
            classroom.add("j1-201");
            System.out.println("Logging in as: mt16151056");
            System.out.println(
                    "J1-201 is free on Tuesday week [1,2]: " + p.isAvailable(classroom, 1, 2).get("j1-201")[0]
            );
            assertFalse(p.isAvailable(classroom, 1, 2).get("j1-201")[0]);
        }
    }

    @Test
    public void TestIsAvailable2() {
        try (Parser p = new Parser("mt16151056", "mengtao1219", true)) {
            Set<String> classroom = new HashSet<>();
            classroom.add("j1-210");
            System.out.println("Logging in as: mt16151056");
            System.out.println(
                    "J1-201 is free on Tuesday this week: " + p.isAvailable(classroom).get("j1-210")[0]
            );
            assertTrue(p.isAvailable(classroom, 1, 2).get("j1-210")[0]);
        }
    }
}
