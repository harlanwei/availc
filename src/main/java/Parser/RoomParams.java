package Parser;

public class RoomParams {
    public final int pageNo;
    public final int pageCount;
    public final int PageXiaoqu;
    public final String pageLhdm;
    public final int index;

    /**
     * @implNote A private constructor so it won't be extended. The content of this function is
     * merely here to suppress the warnings about the final fields not being initialized.
     */
    private RoomParams(int pageNo, int pageCount, int pageXiaoqu, String pageLhdm, int index) {
        this.pageNo = pageNo;
        this.pageCount = pageCount;
        this.PageXiaoqu = pageXiaoqu;
        this.pageLhdm = pageLhdm;
        this.index = index;
    }
}
