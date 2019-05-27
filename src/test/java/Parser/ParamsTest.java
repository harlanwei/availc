package Parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParamsTest {
    @Test
    public void readParamsWorks() {
        int pageCount = Params.getParams().rooms.get("J4-101").pageCount;
        assertEquals(pageCount, 2);
    }
}