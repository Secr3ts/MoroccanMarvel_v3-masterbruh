import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used to generate a random room for the player to enter.
 * 
 * @author Sofyan Guillermet-Laouad and Aloïs Fournier 19.05.23
 */

public class RoomRandormizer {
    private List<Room> aRooms;
    private Random aRandom;

    /**
     * Constructor of the class RoomRandomizer
     */
    public RoomRandormizer() {
        this.aRooms = new ArrayList<>();
        this.aRandom = new Random();
    }

    /**
     * Add a room to the list of rooms
     * 
     * @param pRoom
     */
    public void addRoom(final Room pRoom) {
        this.aRooms.add(pRoom);
    }

    /**
     * Get the wole collection
     * @return the list of rooms
     */
    public ArrayList<Room> getRooms() {
        return new ArrayList<Room>(this.aRooms);
    }

    /**
     * Returns a random room from the list of rooms
     * 
     * @return a random room
     */
    public Room getRandomRoom() {
        if (this.aRooms.isEmpty()) {
            return null;
        }
        return this.aRooms.get(this.aRandom.nextInt(this.aRooms.size()));
    }
}