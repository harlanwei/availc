package Parser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class RoomQueryParams implements Comparable<RoomQueryParams> {
    public final int pageNo;
    public final int pageCount;
    public final int PageXiaoqu;
    public final String pageLhdm;

    /**
     * @implNote A private constructor so it won't be extended. The content of this function is
     * merely here to suppress the warnings about the final fields not being initialized.
     */
    private RoomQueryParams(int pageNo, int pageCount, int pageXiaoqu, String pageLhdm, int index) {
        this.pageNo = pageNo;
        this.pageCount = pageCount;
        this.PageXiaoqu = pageXiaoqu;
        this.pageLhdm = pageLhdm;
    }

    @Override
    public int compareTo(@NotNull RoomQueryParams rhs) {
        int pageLhdmComparisonResult = pageLhdm.compareTo(rhs.pageLhdm);
        if (pageLhdmComparisonResult != 0) return pageLhdmComparisonResult;
        return Integer.compare(pageNo, rhs.pageNo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomQueryParams that = (RoomQueryParams) o;
        return pageNo == that.pageNo &&
                pageLhdm.equals(that.pageLhdm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNo, pageLhdm);
    }
}
