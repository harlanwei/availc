package Parser;

import org.jetbrains.annotations.NotNull;

class Room implements Comparable<Room> {
    final String name;
    final int index;
    final String page;

    /**
     * @implNote A private constructor so it won't be extended. The content of this function is
     * merely here to suppress the warnings about the final fields not being initialized.
     */
    private Room(String name, String page, int index) {
        this.name = name;
        this.page = page;
        this.index = index;
    }

    /**
     * @implNote This method is mainly for the convenience of implementation for the
     * {@code Parser} class. We usually would rows on the same page together, then sort
     * them according to their row indices. This is why its implementation differs
     * drastically with that of the {@code equals} method.
     */
    @Override
    public int compareTo(@NotNull Room rhs) {
        int buildingComparisonResult = page.compareTo(rhs.page);
        return buildingComparisonResult == 0
                ? Integer.compare(index, rhs.index)
                : buildingComparisonResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room that = (Room) o;

        // The only thing two rooms might differ from each other is their names.
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        // See `Room::equals` for why.
        return name.hashCode();
    }
}
