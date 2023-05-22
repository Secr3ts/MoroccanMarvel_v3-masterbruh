import java.util.ArrayList;

/**
 * TransporterRoom is a type of room that teleports the player to a random room
 */
public class TransporterRoom extends Room {

    private RoomRandormizer aRandomizer;

    /**
     * Constructor of the class TransporterRoom
     * @param pName the name of the room
     * @param pImage the image of the room 
     */
    public TransporterRoom(final String pName, final String pImage) {
        super(pName, pImage);
        this.aRandomizer = new RoomRandormizer();
    }

    /**
     * Add a room to the list of rooms
     * 
     * @param pRoom the room to add
     */
    public void addRoom(final Room pRoom) {
        this.aRandomizer.addRoom(pRoom);
    }

    /**
     * Returns the list of rooms
     * 
     * @return the list of rooms
     */
    public ArrayList<Room> getExits() {
        return new ArrayList<Room>(this.aRandomizer.getRooms());
    }

    /**
     * Returns a random room from the list of rooms
     * 
     * @return a random room
     */
    public Room getRandomExit() {
        return this.aRandomizer.getRandomRoom();
    }
}