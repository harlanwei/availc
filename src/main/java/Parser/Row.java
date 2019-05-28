package Parser;

import org.jetbrains.annotations.NotNull;

class Row implements Comparable<Row> {
    final String name;
    final int index;
    final RoomQueryParams queryParams;

    /**
     * @implNote A private constructor so it won't be extended. The content of this function is
     * merely here to suppress the warnings about the final fields not being initialized.
     */
    private Row(String name, int index, RoomQueryParams queryParams) {
        this.name = name;
        this.index = index;
        this.queryParams = queryParams;
    }

    /**
     * @implNote This method is mainly for the convenience of implementation for the
     * {@code Parser} class. We usually would rows on the same page together, then sort
     * them according to their row indices. This is why its implementation differs
     * drastically with that of the {@code equals} method.
     */
    @Override
    public int compareTo(@NotNull Row rhs) {
        int queryParamComparisonResult = queryParams.compareTo(rhs.queryParams);
        if (queryParamComparisonResult != 0) return queryParamComparisonResult;

        return Integer.compare(index, rhs.index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row that = (Row) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
