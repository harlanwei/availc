package CLI;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class CLITest {
    /**
     * 测试Exception开头的方法时需要注释掉以下三段代码
     * 在测试NoException开头的方法时，通过更改输出流判断输出正确与否
     */
    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeClass
    public static void setOut() {
        System.setOut(new PrintStream(outContent));
    }

    @Before
    public void resetStream() {
        outContent.reset();
    }

    /**
     * 测试没有抛出异常时的运行结果
     * 正常运行返回能够使用的空教室
     */
    @Test
    public void NoException() {
        try {
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "-ev", "--non-headless");
            assertTrue(outContent.toString().contains("J5-101, J5-102, J5-103, J5-104, J5-105, J5-106, J5-201, J5-202, J5-203, J5-204, J5-210, J5-211, J5-304, J5-403, J5-404"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "-t", "1,14");
            assertTrue(outContent.toString().contains("J5-103"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "--json", "-t", "1,14");
            assertTrue(outContent.toString().contains("{\"results\":{\"j5-103\":true}}"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "-t", "8,10");
            assertTrue(outContent.toString().contains("J5-102, J5-103, J5-104, J5-105, J5-106, J5-201, J5-202, J5-403, J5-404"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "x1", "-w", "10", "-d", "mon", "--all", "-t", "6,7");
            assertTrue(outContent.toString().contains("1101, 1102, 1103, 1106, 1108, 1214, 1301, 1303, 1304, 1315, 1316, 1317, 1407"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "-am");
            assertTrue(outContent.toString().contains("J5-103, J5-106, J5-205, J5-208, J5-210, J5-304"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "-pm");
            assertTrue(outContent.toString().contains("J5-103, J5-104, J5-201, J5-202"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "-pmx");
            assertTrue(outContent.toString().contains("J5-103, J5-104, J5-201, J5-202"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--all", "-ev");
            assertTrue(outContent.toString().contains("J5-101, J5-102, J5-103, J5-104, J5-105, J5-106, J5-201, J5-202, J5-203, J5-204, J5-210, J5-211, J5-304, J5-403, J5-404"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-r", "j5-103", "-t", "1,14");
            assertTrue(outContent.toString().contains("可以"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-r", "j5-105", "-t", "1,14");
            assertTrue(outContent.toString().contains("不能"));
            outContent.reset();
            new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-r", "j5-103,j5-105", "-t", "1,14");
            assertTrue(outContent.toString().contains("J5-103"));
            outContent.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试没有抛出异常时的运行结果
     * 正常运行返回能够使用的空教室
     * 这是后来增添的一些测试用例
     */
    @Test
    public void NoExceptionAdd() {
        new CommandLine(new CLI()).execute("-b", "s5", "-d", "fri", "-w", "10", "--all", "-ev");
        assertTrue(outContent.toString().contains("J5-101, J5-102, J5-103, J5-104, J5-105, J5-106, J5-201, J5-202, J5-203, J5-204, J5-210, J5-211, J5-304, J5-403, J5-404"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "s5", "--all");
        assertTrue(outContent.toString().contains("J5-103, J5-104, J5-106, J5-203, J5-204, J5-205, J5-206, J5-208, J5-209, J5-210, J5-304, J5-305, J5-306, J5-308, J5-309, J5-403"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "x1");
        assertTrue(outContent.toString().contains("1102, 1106, 1108, 1305, 1306"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "s5", "-d", "fri", "-w", "10,11", "--all", "-ev");
        assertTrue(outContent.toString().contains("J5-101, J5-102, J5-103, J5-104, J5-105, J5-106, J5-201, J5-202, J5-203, J5-204, J5-210, J5-211, J5-304, J5-403, J5-404"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "s5", "-d", "fri", "-w", "10,13", "--all", "-ev");
        assertTrue(outContent.toString().contains("J5-102, J5-103, J5-104, J5-105, J5-106, J5-201, J5-202, J5-203, J5-204, J5-210, J5-211, J5-304, J5-403, J5-404"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "s5", "-d", "fri", "-w", "10", "--all", "-t", "5,8");
        assertTrue(outContent.toString().contains("J5-103, J5-104"));
    }

    /**
     * 单独测试limit参数的返回结果
     * 正常运行返回随机教室
     */
    @Test
    public void NoExceptionTestLimit() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-l=2", "-ev");
        assertTrue(outContent.toString().contains("J5-203, J5-304"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--limit=2", "-ev");
        assertTrue(outContent.toString().contains("J5-101, J5-202"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "--limit=7", "-ev");
        assertTrue(outContent.toString().contains("J5-104, J5-105, J5-202, J5-203, J5-210, J5-211, J5-404"));
        outContent.reset();
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-l=7", "-ev");
        assertTrue(outContent.toString().contains("J5-101, J5-103, J5-202, J5-203, J5-211, J5-403, J5-404"));
        outContent.reset();
    }

    /**
     * 测试缺少参数的情况
     * 抛出异常并打印--help信息
     */
    @Test
    public void ExceptionMissRequiredArgument() {
        new CommandLine(new CLI()).execute();
    }

    /**
     * 测试课程时间超范围的情况
     * 抛出异常IllegalArgumentException
     * 输入参数有误
     */
    @Test
    public void ExceptionOutOfRange() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-t", "1,20");
    }

    /**
     * 测试"-t"参数start>end的情况
     * 抛出异常IllegalArgumentException
     * 输入参数有误
     */
    @Test
    public void ExceptionStartLargerThanEnd() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-t", "4,3");
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-t", "2,1");
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-t", "4,1");
    }

    /**
     * 测试limit格式输入错误的情况
     * 无返回
     */
    @Test
    public void ExceptionWrongLimit() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-l 2");
    }

    /**
     * 测试错误教室输入的情况
     * 抛出异常NoSuchRoomException
     */
    @Test//没有这
    public void ExceptionNoSuchRoom() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fri", "-r", "j5-510");
        //        thrown.expect(Exception.class);
    }

    /**
     * 测试周数超范围的情况
     * 抛出异常IllegalArgumentException
     */
    @Test
    public void ExceptionOutOfTerm() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "20", "-d", "fri", "-ev");
    }

    /**
     * 测试星期输入错误的情况
     * 抛出异常Exception
     */
    @Test
    public void ExceptionWrongWeekday() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-d", "fra", "-ev");
    }

    /**
     * 测试教学楼输入错误的情况
     * 抛出异常RuntimeException
     */
    @Test
    public void ExceptionWrongBuilding() {
        new CommandLine(new CLI()).execute("-b", "s7", "-w", "10", "-d", "fri", "-ev");
    }

    /**
     * 测试没有教学楼输入的情况
     * 抛出异常并打印--help信息
     */
    @Test//没有教学楼输入测试
    public void ExceptionNoBuilding() {
        new CommandLine(new CLI()).execute("-b", "-w", "10", "-d", "fri", "-ev");
        new CommandLine(new CLI()).execute("-r", "j5-101", "-w", "10", "-d", "fri", "-ev");
        new CommandLine(new CLI()).execute("-r", "1103", "-w", "10", "-d", "fri", "-ev");
        new CommandLine(new CLI()).execute("-r", "1103", "-w", "10", "-d", "fri", "-am");
    }

    @Test//输入两个不同教学楼测试
    public void ExceptionDifferentBuilding() {
        new CommandLine(new CLI()).execute("-b", "s5,x1", "--all");
    }

    @Test//输入两个不同教室测试
    public void ExceptionDifferentRoom() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-r", "j5-101,1103", "-d", "fri", "-ev");
    }

    @Test//输入不对应教室，教室为优先测试
    public void ExceptionWrongRoom() {
        new CommandLine(new CLI()).execute("-b", "s5", "-w", "10", "-r", "j3-103", "-d", "fri", "-ev");
    }

    @Test//只输入"--all"测试
    public void ExceptionAll() {
        new CommandLine(new CLI()).execute("--all");
    }

    @Test//输入两个不同星期测试
    public void ExceptionWrongDate() {
        new CommandLine(new CLI()).execute("-b", "s5", "-d", "Thu,fri", "-w", "10", "--all", "-ev");
    }

}
