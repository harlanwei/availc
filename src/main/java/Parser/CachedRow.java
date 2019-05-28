package Parser;

import java.util.Objects;

class CachedRow {
    public final String classroom;
    public final int start;
    public final int end;

    CachedRow(String classroom, int start, int end) {
        this.classroom = classroom;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CachedRow that = (CachedRow) o;
        return start == that.start &&
                end == that.end &&
                classroom.equals(that.classroom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classroom, start, end);
    }
}
