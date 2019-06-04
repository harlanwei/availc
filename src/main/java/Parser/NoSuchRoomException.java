package Parser;

/**
 * An exception indicating that user has provided a room whose name is not in the database.
 */
class NoSuchRoomException extends RuntimeException {
    /**
     * Create an exception indicating that user has provided a room whose name is not in the database.
     */
    NoSuchRoomException(String roomName) {
        super(roomName);
    }
}
