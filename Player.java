import java.util.Random;
import java.util.Stack;

/**
 * Player class - More fonctionnalities for our own character
 * 
 * @author Sofyan Guillermet-Laouad
 */

public class Player {
    private String aNickname;
    private Room aCurrentRoom;
    private Stack<Room> aPreviousRooms;
    private ItemList aInventory;
    private double aMaxWeight;
    private double aCurrentWeight;
    private int aGoodActionCounter;
    private int aBadActionCounter;
    private FlagList aFlags;
    

    /**
     * Class constructor
     */

    public Player(final String pNickname, final Room pCurrentRoom) {
        this.aCurrentRoom = pCurrentRoom;
        this.aNickname = pNickname;
        this.aPreviousRooms = new Stack<Room>();
        this.aInventory = new ItemList("your inventory");
        this.aMaxWeight = 10;
        this.aCurrentWeight = 0.0;
        this.aGoodActionCounter = 0;
        this.aBadActionCounter = 0;
        this.aFlags = new FlagList();
    }

    /**
     * Add flag to Player
     * @param pFlag the flag to add
     */
    public void addFlag(final Flag pFlag) {
        this.aFlags.addFlag(pFlag);
    }

    /**
     * Check if the player has the flag
     * @return
     */
    public boolean hasFlag(final String pFlagName) {
        return this.aFlags.hasFlag(pFlagName);
    }

    /**
     * Return the FlagList
     * @return FlagList 
     */
    public FlagList getFlagList() {
        return this.aFlags;
    }
    
    /**
     * Return the flag with the given name
     * @return Flag
     */
    public Flag getFlag(final String pFlagName) {
        return this.aFlags.getFlag(pFlagName);
    } 

    // Getters

    /**
     * Get the nickname of the player
     * 
     * @return
     */
    public String getNickname() {
        return this.aNickname;
    }// getNickname()

    /**
     * Get the current room of the player
     * 
     * @return
     */
    public Room getCurrentRoom() {
        return this.aCurrentRoom;
    }// getCurrentRoom()

    /**
     * Get the previous rooms of the player
     * 
     * @return
     */
    public Stack<Room> getPreviousRooms() {
        return this.aPreviousRooms;
    }// getPreviousRooms()

    /**
     * Get the inventory of the player
     * 
     * @return
     */
    public ItemList getInventory() {
        return this.aInventory;
    }// getInventory()

    /**
     * Get the max weight of the player's inventory
     * 
     * @return
     */
    public double getMaxWeight() {
        return this.aMaxWeight;
    }// getMaxWeight()

    public void setMaxWeight(final double pMaxWeight) {
        this.aMaxWeight = pMaxWeight;
    }// setMaxWeight()

    /**
     * Get the current weight of the player's inventory
     * 
     * @return
     */
    public double getCurrentWeight() {
        return this.aCurrentWeight;
    }// getCurrentWeight()

    /**
     * Get all the items of the player's inventory in String
     * 
     * @return
     */
    public String getInventoryString() {
        return this.aInventory.getItemString();
    }// getInventoryString()

    // Action methods

    /**
     * Move the player in the indicated room
     * 
     * @param pNextRoom
     */
    public void goRoom(final String pNextRoom) {
        this.aPreviousRooms.push(this.aCurrentRoom);
        Room vNextRoom = this.aCurrentRoom.getExit(pNextRoom);
        this.aCurrentRoom = vNextRoom;
    }

    /**
     * Get the player back to the previous room
     */
    public void back() {
        this.aCurrentRoom = this.aPreviousRooms.pop();
    }

    /**
     * Add an item to the player's inventory
     */
    public void take(final String pItemName) {
        this.aInventory.addItem(pItemName, this.aCurrentRoom.getItem(pItemName));
        this.getCurrentRoom().getRoomItems().removeItem(pItemName);
        this.aCurrentWeight += this.aInventory.getItem(pItemName).getItemWeight();
    }// take()

    /**
     * Remove an item from the player's inventory
     */
    public void drop(final String pItemName) {
        this.getCurrentRoom().addItem(this.aInventory.getItem(pItemName));
        this.aCurrentWeight -= this.aInventory.getItem(pItemName).getItemWeight();
        this.aInventory.removeItem(pItemName);
    }// drop()

    /**
     * Make the character eat an item from the inventory
     * 
     * @param pItemName
     */
    public void eat(final String pItemName) {
        this.aCurrentWeight -= this.aInventory.getItem(pItemName).getItemWeight();
        this.aInventory.removeItem(pItemName);
    }// eat()

    /**
     * Check if the player can take the item in his inventory
     * 
     * @param pItemWeight
     * @return true if the player can take the item, false if not
     */
    public boolean canTake(final double pItemWeight) {
        if (this.getCurrentWeight() + pItemWeight <= this.aMaxWeight) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the current Room (equivalent of goRoom) 
     * @param pRoom the room to set
     */
    public void setCurrentRoom(final Room pRoom) {
        this.aCurrentRoom = pRoom;
    }

    /**
     * Charges the beamer that is passed as 
     * @param pRoom the room to charge
     * @param pBeamer the beamer to charge
     */
    public void charge(final Room pRoom, final Beamer pBeamer) {
        pBeamer.charge(pRoom);
    }

    /**
     * Fires the beamer that is passed as parameter
     * @param pBeamer the beamer to fire
     */
    public void fire(final Beamer pBeamer) {
        pBeamer.fire(this);
    }
    
    /**
     * Returns a String containing the good and bad actions
     * @return a String containing the good and bad actions for future use
     */
    public String getActionString() {
        return "Good actions : " + this.aGoodActionCounter + "\nBad actions : " + this.aBadActionCounter;
    }

    public void addGoodAction() {
        this.aGoodActionCounter += 1;
    }
    
    public void addBadAction() {
        this.aBadActionCounter += 1;
    }
    
    public int getGoodAction() {
        return this.aGoodActionCounter;
    }
    
    public int getBadAction() {
        return this.aBadActionCounter;
    }
}//Player