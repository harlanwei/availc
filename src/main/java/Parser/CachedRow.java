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

    /**
     * This method should only be used by {@code CachedRowDeserializer}.
     * @param toStringOutput The {@code String} output by the {@code toString} method.
     */
    CachedRow(String toStringOutput) {
        String[] params = toStringOutput.split(",");
        this.classroom = params[0];
        this.start = Integer.valueOf(params[1]);
        this.end = Integer.valueOf(params[2]);
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

    /**
     * @implNote This {@code toString} method is tightly coupled with the GSON library. Basically, when the
     * {@code Cache} class exports caches to the disk, it calls GSON methods, which by default use the
     * {@code toString} method for the {@code Map} keys as the JSON attribute name. Here for the convenience
     * of implementation, we didn't implement a serializer but chose to go with the default behavior. If this
     * class is to be inherited in the future, remember to implement a separate serializer to preserve the
     * functionality of {@code Cache} class.
     */
    @Override
    public String toString() {
        return String.format("%s,%d,%d", classroom, start, end);
    }
}
