import java.util.HashMap;
import java.util.Set;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author Sofyan Guillermet-Laouad
 * @version 2023.17.02
 * 
 */
public class Room {
    private String aDescription;
    private HashMap<String, Room> aExits; // Hashmap stocking the exits of the room
    private String aImage;
    //private Item aItem;
    private ItemList aRoomItems;
    private Event aEvent;

    /**
     * Constructeur de la classe Room
     * 
     * @param pRoomName
     */
    public Room(final String pRoomName, final String pImage) {
        this(pRoomName, pImage, null);
    }

    public Room(final String pRoomName, final String pImage, final Event pEvent) {
        this.aDescription = pRoomName;
        this.aExits = new HashMap<String, Room>();
        this.aImage = pImage;
        this.aRoomItems = new ItemList("the room");
    }

    /**
     * Run the event of the room
     */
    public void runEvent(final GameEngine pGameEngine) {
        if (!this.hasEvent()) {
            return;
        }
        this.aEvent.run(pGameEngine);
    }

    public Event getEvent() {
        return this.aEvent;
    }
    /**
     * Add event to Room
     * @param pEvent The event to add
     */
    public void addEvent(final Event pEvent) {
        this.aEvent = pEvent;
    }

    public boolean hasEvent() {
        return this.aEvent != null ? true : false;
    }

    public boolean isEventDone() {
        return this.aEvent.isEventDone();
    }
    /**
     * Retourne la description de la salle
     * 
     * @return String
     */
    public String getDescription() {
        return this.aDescription;
    }

    /**
     * Retourne une longue description de la salle, de la forme:
     * Vous êtes dans la cuisine.
     * Sortie: Nord-Ouest
     * 
     * @return String
     */
    public String getLongDescription() {
        return "You are " + this.aDescription + ".\n" + this.getExitString() + "\n" + this.getRoomItemsString();
    }

    public String getImageName() {
        return this.aImage;
    }

    /**
     * Définit les sorties de la salle dans la direction indiquée
     * 
     * @param pDirection
     * @param pRoom
     */
    public void setExit(final String pDirection, final Room pRoom) {
        this.aExits.put(pDirection, pRoom);
    }

    /**
     * Retourne la salle dans la direction indiquée
     * 
     * @param pDirection
     * @return Room
     */
    public Room getExit(final String pDirection) {
        return this.aExits.get(pDirection);
    }

    /**
     * Retourne les sorties d'une salle en une String
     * 
     * @return String
     */
    public String getExitString() {

        String exit = "Exits: ";
        Set<String> keys = this.aExits.keySet();

        for (String exitKey : keys) {
            exit += exitKey + " ";
        }

        return exit;
    }

    /**
     * Add an item to the room
     * 
     * @param pItem The item to add
     */
    public void addItem(final Item pItem) {
        this.aRoomItems.addItem(pItem.getItemName(), pItem);
        ;
    }

    /**
     * Get the item present in the room
     * 
     * @param pItem The key of the item we want to get
     * @return The intem linked to the key
     */
    public Item getItem(final String pItem) {
        return this.aRoomItems.getItem(pItem);
    }// getItem()

    /**
     * The item's character chain getter
     * 
     * @return The item's character chain
     */
    public String getRoomItemsString() {
        return this.aRoomItems.getItemString();
    }// getRoomItemsString()

    /**
     * The list of the items in the room
     * 
     * @return The items in the room
     */
    public ItemList getRoomItems() {
        return this.aRoomItems;
    }// getRoomItems()
} // Room
