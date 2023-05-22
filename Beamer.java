/**
 * A beamer is an item that can be charged with a room and then teleport the
 * player to that room.
 * 
 * @author Sofyan Guillermet-Laouad and Aloïs Fournier 19.05.23
 */
public class Beamer extends Item {
    private Room aChargedRoom;
    private boolean aIsCharged;

    /**
     * Constructor of the class Beamer
     * 
     * @param pName
     * @param pDescription
     * @param pWeight
     */
    public Beamer(final String pName, final String pDescription, final double pWeight) {
        super(pName, pDescription, pWeight);
        this.aChargedRoom = null;
        this.aIsCharged = false;
    }

    /**
     * Charges the beamer with the room
     */
    public void charge(final Room pRoom) {
        this.aChargedRoom = pRoom;
        this.aIsCharged = true;
    }

    /**
     * Returns the state of the beamer
     * @return true if the beamer is charged, false otherwise
     */
    public boolean isCharged() {
        return this.aIsCharged;
    }

    /**
     * Teleport the player to the charged room
     * 
     * @param pPlayer
     */
    public void fire(final Player pPlayer) {
        if (this.aIsCharged && this.aChargedRoom != null) {
            pPlayer.setCurrentRoom(this.aChargedRoom);
            this.aIsCharged = false;
        } else {
            System.out.println("The beamer is not charged or the saved room is nul.");
        }
    }
}