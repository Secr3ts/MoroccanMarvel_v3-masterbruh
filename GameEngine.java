import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

/**
 * Classe GameEngine - le moteur du jeu d'aventure Zuul.
 *
 * @author Sofyan Guillermet-Laouad
 */
public class GameEngine {
    private Room aCurrentRoom;
    private Parser aParser;
    private UserInterface aGUI;
    private Room aPreviousRoom; // The previous room obviously
    private Player aPlayer; // The player of the game
    private String aFakeRandomRoom;
    private boolean aIsInTestMode = false;
    private Event[] aEvents;
    private HashMap<String, Room> aRooms;
    private boolean win;

    /**
     * Create the game and the rooms.
     */
    public GameEngine() {
        this.aParser = new Parser();
        this.createRooms();
    }

    /**
     * Crée l'interface graphique
     *
     * @param pUserInterface Interface Graphique
     */
    public void setGUI(final UserInterface pUserInterface) {
        this.aGUI = pUserInterface;
        this.printWelcome();
    } // setGUI(.)

    /**
     * Creates all the rooms and link them.
     */
    private void createRooms() {
        // Creates the character and his hometown.

        Room vBirth = new Room("in your childhood bedroom", "images/ChildRoom.png");
        Room vLaStreet = new Room("in the main street of your native town", "images/LaStreet.png");
        Room vNLaStreet = new Room("at the northern road", "images/Rue Nord.png");
        Room vSLaStreet = new Room("at the southern road", "images/Rue Sud.png");
        Room vRiverWay = new Room("on the way to the river", "images/Chemin de la riviere.png");
        Room vRiver = new Room("at a beautiful river where the teenagers play and the old people go to rest", "images/Riviere.png");
        // Childhood: just hanging around playing with rocks and animals
        Room vChildLR = new Room("in a common living room", "images/Salon.png");
        Room vChildMadrasah = new Room("at school", "images/Madrasa.png");
        // Teenage: Just discovering the outside world
        Room vTeenJama3 = new Room("thinking it's time to get closer to Allah in the mosque", "images/Jama3.png");
        // Adulthood: Spending your time and energy working
        Room vAdultFieldWay = new Room("on the way to the wheat field", "images/Chemin du champs.png");
        Room vAdultField = new Room("in the wheat field in which you work", "images/Champs.png");
        Room vAdultZawiya = new Room("in a mystical religious building in which you glorify Allah", "images/Zawiya.png");
        TransporterRoom vAdultZawiyaCloset = new TransporterRoom("entering the closet of the Zawiya", "images/Armoire.jpeg");
        // Senior: Most of your life is already behind you. You spend your time
        // reminiscing about your younger days
        Room vSeniorRiver2 = new Room(
                "walking next to your beloved river, yet you feel that something inevitable is calling you", "images/Route Mysterieuse.png");
        Room vDeath = new Room("dead, after a long life.", "images/Tombe.png");
        Room vJannah = new Room("in Jannah, you lived a pious life by helping others.", "images/Jannah.jpeg");
        Room vJahannam = new Room("in Jahannam. You lived a life full of hatred, here are the consequences.", "images/Jahannam.png");

        this.aRooms = new HashMap<String, Room>();
        this.aRooms.put("birth", vBirth);
        this.aRooms.put("childlr", vChildLR);
        this.aRooms.put("lastreet", vLaStreet);
        this.aRooms.put("nlastreet", vNLaStreet);
        this.aRooms.put("slastreet", vSLaStreet);
        this.aRooms.put("teenjama3", vTeenJama3);
        this.aRooms.put("childmadrasah", vChildMadrasah);
        this.aRooms.put("adultzawiya", vAdultZawiya);
        this.aRooms.put("adultzawiyacloset", vAdultZawiyaCloset);
        this.aRooms.put("adultfieldway", vAdultFieldWay);
        this.aRooms.put("adultfield", vAdultField);
        this.aRooms.put("riverway", vRiverWay);
        this.aRooms.put("river", vRiver);
        this.aRooms.put("seniorriver2", vSeniorRiver2);
        this.aRooms.put("death", vDeath);
        this.aRooms.put("jannah", vJannah);
        this.aRooms.put("jahannam", vJahannam);

        // Character creation

        this.aPlayer = new Player("Tamashe", vBirth);

        // Set exits
        vBirth.setExit("east", vChildLR);

        vChildLR.setExit("east", vLaStreet);

        vLaStreet.setExit("north", vNLaStreet);
        vLaStreet.setExit("south", vSLaStreet);
        vLaStreet.setExit("east", vRiverWay);
        vLaStreet.setExit("west", vChildLR);

        vNLaStreet.setExit("north", vTeenJama3);
        vNLaStreet.setExit("south", vLaStreet);
        vNLaStreet.setExit("west", vChildMadrasah);

        vSLaStreet.setExit("north", vLaStreet);
        vSLaStreet.setExit("east", vAdultFieldWay);
        vSLaStreet.setExit("west", vAdultZawiya);

        vTeenJama3.setExit("south", vNLaStreet);

        vChildMadrasah.setExit("east", vNLaStreet);

        vAdultZawiya.setExit("east", vSLaStreet);
        vAdultZawiya.setExit("west", vAdultZawiyaCloset);

        vAdultFieldWay.setExit("east", vAdultField);
        vAdultFieldWay.setExit("west", vSLaStreet);

        vAdultField.setExit("west", vAdultFieldWay);

        vRiverWay.setExit("east", vRiver);
        vRiverWay.setExit("west", vLaStreet);
        vRiver.setExit("east", vSeniorRiver2);

        vRiver.setExit("west", vRiverWay);

        vSeniorRiver2.setExit("east", vDeath);

        // if (this.aPlayer.getGoodAction() >= 3) {
        vDeath.setExit("up", vJannah);// VICTOIRE
        // } else if (this.aPlayer.getBadAction() >= 3) {
        vDeath.setExit("down", vJahannam);// DEFAITE
        // }

        // Initial Room
        this.aCurrentRoom = vBirth;
        this.aParser = new Parser();

        /**
         * Item creation
         */
        Item vWoodenToy = new Item("wooden-toy", "A wooden lion your grandfather made for you", 0.250);
        // TODO: Break Toy ?
        Item vCouscous = new Item("couscous", "A plate of couscous that your mother made for you", 0.300);
        Item vMessyClothes = new Item("messy-clothes", "Some dirty clothes of yours that should be washed", 0.350);
        // TODO: Clean up ?
        Item vWaterJar = new Item("water-jar", "A jar of water that you just found in the street", 0.500);
        // TODO: Feed thirsty cat ?
        Item vGoldenCoins = new Item("golden-coins", "A bag of golden coins that you made by working in the fields",
                0.100);
        // TODO: Give to beggar ?
        Item vOldBook = new Item("old-book", "An old book that some kids gave you to read them a story", 0.200);
        // TODO: Read book to kids ?
        // A msemmen is a traditional Moroccan crepe
        Item vMagicMsemmen = new Item("magic-msemmen",
                "A magic msemmen you found in the Zawiya that can double your inventory's capacity", 0.75);
        Beamer vMagicIncense = new Beamer("magicincense",
                "A magic incense that can teleport you to the last room you used it in", 0.100);

        // Event Creation
        Event vBirthEvent = new Event(this.aGUI, "You feel a strange urge to break your toy.", "Leave your toy be",
                "Break your toy", 1, vWoodenToy, null);
        Event vChildEvent = new Event(this.aGUI, "Your mother told you earlier to clean the pile of dirty clothes.",
                "Clean up your clothes", "Don\'t listen to your mother", 1, vMessyClothes, null);
        Event vTeenagerEvent = new Event(this.aGUI, "You see a cat that looks thirsty.", "Feed the cat",
                "Leave the cat thirstier than ever", 1, vWaterJar, null);
        Event vAdultEvent = new Event(this.aGUI, "You see a beggar in the street.", "Give him some coins", "Ignore him",
                1, vGoldenCoins, null);
        Event vSeniorEvent = new Event(this.aGUI,
                "A bunch of excited kids run at you and ask you to read them a story.", "Read them a story",
                "Tell them to go bother someone else", 1, vOldBook, null);

        // Add Events to Room
        vBirth.addEvent(vBirthEvent);
        vChildLR.addEvent(vChildEvent);
        vLaStreet.addEvent(vTeenagerEvent);
        vSLaStreet.addEvent(vAdultEvent);
        vRiverWay.addEvent(vSeniorEvent);

        this.aEvents = new Event[] { vBirthEvent, vChildEvent, vTeenagerEvent, vAdultEvent, vSeniorEvent };

        // Flags creation
        Flag vBabyFlag = new Flag("baby");
        Flag vChildFlag = new Flag("child");
        Flag vTeenagerFlag = new Flag("teenager");
        Flag vAdultFlag = new Flag("adult");
        Flag vSeniorFlag = new Flag("senior");

        // Add Initial Flag to Player
        this.aPlayer.addFlag(vBabyFlag);

        // Add Flags to Rooms
        vBirthEvent.addFlag(vChildFlag);
        vChildEvent.addFlag(vTeenagerFlag);
        vTeenagerEvent.addFlag(vAdultFlag);
        vAdultEvent.addFlag(vSeniorFlag);

        // Item placement
        vBirth.getRoomItems().addItem("wooden-toy", vWoodenToy);
        vChildLR.getRoomItems().addItem("messy-clothes", vMessyClothes);
        vChildLR.getRoomItems().addItem("couscous", vCouscous);
        vLaStreet.getRoomItems().addItem("water-jar", vWaterJar);
        vAdultField.getRoomItems().addItem("golden-coins", vGoldenCoins);
        vRiverWay.getRoomItems().addItem("old-book", vOldBook);
        vAdultZawiya.getRoomItems().addItem("magic-msemmen", vMagicMsemmen);
        vAdultZawiya.getRoomItems().addItem("magicincense", vMagicIncense);

        // Add Rooms to TransporterRoom
        vAdultZawiyaCloset.addRoom(vBirth);
        vAdultZawiyaCloset.addRoom(vChildLR);
        vAdultZawiyaCloset.addRoom(vLaStreet);
        vAdultZawiyaCloset.addRoom(vNLaStreet);
        vAdultZawiyaCloset.addRoom(vSLaStreet);
        vAdultZawiyaCloset.addRoom(vTeenJama3);
        vAdultZawiyaCloset.addRoom(vChildMadrasah);
        vAdultZawiyaCloset.addRoom(vAdultZawiya);
        vAdultZawiyaCloset.addRoom(vAdultFieldWay);
        vAdultZawiyaCloset.addRoom(vAdultField);
        vAdultZawiyaCloset.addRoom(vRiverWay);
        vAdultZawiyaCloset.addRoom(vRiver);

        // Add events to Rooms

    }

    /**
     * Shows informations about the room in which the player is
     */
    private void printLocationInfo() {
        this.aGUI.println(this.aPlayer.getCurrentRoom().getLongDescription());
        this.aGUI.println(this.aPlayer.getActionString());
        if (this.aPlayer.getCurrentRoom().getImageName() != null) {
            this.aGUI.showImage(this.aPlayer.getCurrentRoom().getImageName());
        }
    }
    //The player looks around in the room
    private void look() {
        this.aGUI.println(this.aPlayer.getCurrentRoom().getLongDescription());
    }
    //The player eat something
    private void eat(final Command pCommand) {

        if (!pCommand.hasSecondWord()) {
            this.aGUI.println("Eat what?");
            return;
        }

        String vItemName = pCommand.getSecondWord();
        if (this.aPlayer.getInventory().hasItem(vItemName) == false) {
            this.aGUI.println("You don't have that item.");
            return;
        }

        Item vItem = this.aPlayer.getInventory().getItem(vItemName);
        if (this.aPlayer.getInventory().hasItem(vItemName) && vItem.getItemName().equals("golden-coins")) {
            this.aGUI.println("Are you crazy? You can't eat that!");
            return;
        } else if (vItem.getItemName().equals("couscous")) {
            this.aPlayer.eat(vItemName);
            this.aGUI.println("Yummy, nothing better than some good food");
            return;
        } else if (vItem.getItemName().equals("magic-msemmen")) {
            this.aPlayer.eat(vItemName);
            this.aGUI.println("Something mystical is happening to your body");
            this.aPlayer.setMaxWeight(this.aPlayer.getMaxWeight() * 2);
            return;
        } else {
            this.aPlayer.eat(vItemName);
        }
    }

    /**
     * Changes the room in which the player is
     * 
     * @param pCommand The command
     */
    public void goRoom(final Command pCommand) {
        if (!CommandWords.isDirection(pCommand.getSecondWord())) {
            this.aGUI.println("Where even is that?");
            return;
        }

        if (pCommand.hasSecondWord() == false) {
            this.aGUI.println("Go Where ?");
            return;
        }

        if (this.aPlayer.getCurrentRoom() instanceof TransporterRoom) {
            TransporterRoom vTransporter = (TransporterRoom) this.aPlayer.getCurrentRoom();
            if (this.aIsInTestMode && !this.aFakeRandomRoom.isEmpty()) {
                ArrayList<Room> vRooms = new ArrayList<Room>(vTransporter.getExits());
                for (Room pRoom : vRooms) {
                    String vRoomName = pRoom.getDescription().toLowerCase();
                    if (this.aFakeRandomRoom.contains(vRoomName.toLowerCase())) {
                        this.aPlayer.setCurrentRoom(pRoom);
                        this.printLocationInfo();
                        return;
                    }
                }
                this.printLocationInfo();
                return;
            }
            this.aPlayer.setCurrentRoom(vTransporter.getRandomExit());
            this.aPlayer.getPreviousRooms().clear();
            return;
        }

        if (this.aPlayer.getCurrentRoom().hasEvent()) {
            if (this.aPlayer.getCurrentRoom().getEvent().isEventRan()) {
                if (!this.aPlayer.getCurrentRoom().isEventDone()) {
                    this.aGUI.println("You can't go anywhere until you finish the event.");
                    return;
                }
            }
        }

        String vDirection = pCommand.getSecondWord();
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit(vDirection);

        if (vNextRoom == null) {
            this.aGUI.println("There is no door !");
            return;
        }

        if(vNextRoom.equals(this.aRooms.get("seniorriver2")) && !this.aPlayer.hasFlag("senior")) {
                this.aGUI.println("There is nothing there... yet?");
                return;
            }

        if (this.aPlayer.getCurrentRoom().equals(this.aRooms.get("death"))) {
                if (this.aPlayer.getBadAction() >= 3) {
                    if (!vNextRoom.equals(this.aRooms.get("jahannam"))) {
                        this.aGUI.println("You do not belong there.");
                        return;
                    }
                } else if (this.aPlayer.getGoodAction() >= 3) {
                    if (!vNextRoom.equals(this.aRooms.get("jannah"))) {
                        this.aGUI.println("You do not belong there.");
                        return;
                    }
                }
            }

        this.aPlayer.goRoom(vDirection);
        if (vNextRoom.hasEvent() && vNextRoom.getEvent().getEventItem().getItemName().equals("old-book")) {
            if (this.aPlayer.hasFlag("senior")) {
                this.aPlayer.getCurrentRoom().runEvent(this);
            }
        } else if (vNextRoom.hasEvent() && vNextRoom.getEvent().getEventItem().getItemName().equals("golden-coins")
                && !this.aPlayer.getInventory().hasItem("golden-coins")) {
            this.aGUI.println("You\'re an adult now, you should probably get to work now");
        } else {
            this.aPlayer.getCurrentRoom().runEvent(this);
        }
        this.printLocationInfo();
        if (this.aPlayer.getCurrentRoom().equals(this.aRooms.get("jannah")) || this.aPlayer.getCurrentRoom().equals(this.aRooms.get("jahannam"))) {
            this.end();
        } 
    }

    //Check if the player lost or won the game.
    private void end() {
        if(this.aPlayer.getGoodAction() >= 3) {
            this.win = true;
        } else if(this.aPlayer.getBadAction() >= 3) {
            this.win = false;
        }
        if (this.win) {
            this.aGUI.println("You won the game, you are now in Jannah and can rest in eternal peace");
        } else {
            this.aGUI.println("You lost the game, you are doomed to eternal suffering in Hell.");
        }
    }

    /**
     * Makes the player go back to the previous room
     * 
     * @param pCommand The command to execute
     */
    private void back(final Command pCommand) {

        if (pCommand.hasSecondWord()) {
            this.aGUI.println("What do you mean go back there?");
            return;
        }

        Room vNextRoom = this.aPreviousRoom;
        if (vNextRoom == null) {
            this.aGUI.println("There is nowhere to go back to.");
            return;
        }

        this.aPlayer.back(); // The player go back to the previous room.
        this.printLocationInfo(); // Show room info
    }// back()

    /**
     * Makes the player pick up an item
     * 
     * @param pCommand The command to execute
     */
    private void take(final Command pCommand) {
        if (!pCommand.hasSecondWord()) {
            this.aGUI.println("Take what?");
            return;
        }

        String vItemName = pCommand.getSecondWord();
        Item vItem = this.aPlayer.getCurrentRoom().getItem(vItemName);
        if (vItem.equals(null)) {
            this.aGUI.println("There is no " + vItemName + " here.");
            return;
        }
        if (!this.aPlayer.canTake(vItem.getItemWeight())) {
            this.aGUI.println("This is too heavy for you!");
            return;
        } else {
            this.aPlayer.take(vItemName);
            this.aGUI.println("You picked up " + vItemName + ".");
        }
    }// take()

    /**
     * Makes the player drop an item
     * 
     * @param pCommand The command to execute
     */
    private void drop(final Command pCommand) {
        if (!pCommand.hasSecondWord()) {
            this.aGUI.println("Drop what?");
            return;
        }

        String vItemName = pCommand.getSecondWord();
        boolean vHasItem = this.aPlayer.getInventory().hasItem(vItemName);
        if (vHasItem == false) {
            this.aGUI.println("You don't have " + vItemName + ".");
            return;
        }

        this.aPlayer.drop(vItemName);
        this.aGUI.println("You dropped " + vItemName + ".");
    }// drop()

    /**
     * Welcoming message for the player
     */
    private void printWelcome() {
        this.aGUI.println(
                "Welcome to Moroccan Marvel! \nMoroccan Marvel is a new incredible adventure game. \nType \'help\' if you need help.");
        this.printLocationInfo();

        this.aPlayer.getCurrentRoom().getEvent().setGUI(this.aGUI);
        this.aPlayer.getCurrentRoom().runEvent(this);
        for (Event e : this.aEvents) {
            e.setGUI(this.aGUI);
        }
    }

    /**
     * Shows help for the player
     */
    private void printHelp() {
        this.aGUI.println("You were born into this world.\nYou want to live a good life.\n");
        this.aGUI.println(this.aParser.showCommands());
    }

    /**
     * Quits the game
     */
    private void endGame() {
        this.aGUI.println("Thank you for playing. See you next time!");
        this.aGUI.enable(false);
    }// engGame

    /**
     * Charges the Beamer
     * 
     * @param pCommand The command to execute
     */
    public void charge(final Command pCommand) {
        if (!pCommand.hasSecondWord()) {
            this.aGUI.println("Charge what?");
            return;
        }

        String vItemName = pCommand.getSecondWord();

        if (!this.aPlayer.getInventory().hasItem(vItemName)) {
            this.aGUI.println("You don't have " + vItemName + ".");
            return;
        }

        if (!(this.aPlayer.getInventory().getItem(vItemName) instanceof Beamer)) {
            this.aGUI.println("You can't charge " + vItemName + " !");
            return;
        }

        Item vItem = this.aPlayer.getInventory().getItem(vItemName);
        Beamer vBeamer = (Beamer) vItem;

        if (vBeamer.isCharged()) {
            this.aGUI.println("You can't charge " + vItemName + " ! \n It is already charged.");
            return;
        }

        this.aPlayer.charge(this.aPlayer.getCurrentRoom(), vBeamer);
        this.aGUI.println("You charged " + vItemName + ".");

    }

    /**
     * Fires the Beamer
     * 
     * @param pCommand
     */
    public void fire(final Command pCommand) {
        if (!pCommand.hasSecondWord()) {
            this.aGUI.println("Fire what?");
            return;
        }

        String vItemName = pCommand.getSecondWord();

        if (!this.aPlayer.getInventory().hasItem(vItemName)) {
            this.aGUI.println("You don't have " + vItemName + ".");
            return;
        }

        if (!(this.aPlayer.getInventory().getItem(vItemName) instanceof Beamer)) {
            this.aGUI.println("You can't fire " + vItemName + " !");
            return;
        }

        Item vItem = this.aPlayer.getInventory().getItem(vItemName);
        Beamer vBeamer = (Beamer) vItem;

        if (!vBeamer.isCharged()) {
            this.aGUI.println("You can't fire " + vItemName + " ! \n It is not charged.");
            return;
        }

        this.aPlayer.fire(vBeamer);
        this.aGUI.println("You fired " + vItemName + ".");
        this.printLocationInfo();
    }

    /**
     * Transform the input into a readable command and interprets it.
     * 
     * @param pCommandLine
     */
    public void interpretCommand(final String pCommandLine) {
        this.aGUI.println("> " + pCommandLine);
        Command vCommand = this.aParser.getCommand(pCommandLine);

        if (vCommand.isUnknown()) {
            this.aGUI.println("I don't know what you mean...");
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        vCommandWord = vCommandWord.toLowerCase();
        switch (vCommandWord) {
            case "quit":
                this.endGame();
                break;
            case "help":
                this.printHelp();
                break;
            case "look":
                this.look();
                break;
            case "eat":
                this.eat(vCommand);
                break;
            case "test":
                this.test(vCommand);
                break;
            case "back":
                this.back(vCommand);
                break;
            case "take":
                this.take(vCommand);
                break;
            case "drop":
                this.drop(vCommand);
                break;
            case "go":
                this.goRoom(vCommand);
                break;
            case "items":
                this.printInventory(vCommand);
                break;
            case "charge":
                this.charge(vCommand);
                break;
            case "fire":
                this.fire(vCommand);
                break;
            case "alea":
                this.alea(vCommand);
                break;
            case "1":
                this.choice(vCommand);
                break;
            case "2":
                this.choice(vCommand);
                break;
            default:
                this.aGUI.println("I don't know what you mean...");
                break;
        }
    }

    private void choice(final Command pCommand) {
        if (pCommand.hasSecondWord()) {
            this.aGUI.println(pCommand.getCommandWord() + " what ?");
            return;
        }

        if (!this.aPlayer.getCurrentRoom().hasEvent()) {
            this.aGUI.println("There is no event to choose from.");
            return;
        }

        if (this.aPlayer.getCurrentRoom().getEvent().testChoice(Integer.parseInt(pCommand.getCommandWord()))) {
            this.aPlayer.getCurrentRoom().getEvent().setEventDone();
            if (this.aPlayer.getCurrentRoom().getEvent().getEventItem().getItemName().equals("wooden-toy")) {
                this.aPlayer.getInventory().removeItem("wooden-toy");
            } else if (this.aPlayer.getCurrentRoom().getEvent().getEventItem().getItemName().equals("messy-clothes")) {
                this.interpretCommand("drop messy-clothes");
            } else if (this.aPlayer.getCurrentRoom().getEvent().getEventItem().getItemName().equals("water-jar")) {
                this.aPlayer.getInventory().removeItem("water-jar");
            } else if (this.aPlayer.getCurrentRoom().getEvent().getEventItem().getItemName().equals("golden-coins")) {
                this.aPlayer.getInventory().getItem("golden-coins").setItemWeight(0.09);
            }
            this.aPlayer.addGoodAction();
        } else {
            this.aPlayer.getCurrentRoom().getEvent().setEventDone();
            this.aPlayer.addBadAction();
        }

        this.aPlayer.addFlag(this.aPlayer.getCurrentRoom().getEvent().getEventFlag());
    }

    private void alea(Command vCommand) {
        if (!vCommand.hasSecondWord()) {
            this.aGUI.println("Alea what?");
            return;
        }

        if (!this.aIsInTestMode) {
            this.aGUI.println("You can't use this command if you are not in test mode.");
            return;
        }
        String vRoomName = vCommand.getSecondWord();

        if (this.aFakeRandomRoom.isEmpty()) {
            this.aFakeRandomRoom = vRoomName;
        } else {
            this.aFakeRandomRoom = "";
        }
    }

    /**
     * Shows the current room's description
     */
    private void getCurrentRoom() {
        this.aGUI.println(this.aPlayer.getCurrentRoom().getDescription());
    }

    /**
     * Test method
     */
    private void test(final Command pCommand) {
        this.aIsInTestMode = true;
        if (pCommand.hasSecondWord() == false) { // We want to know if there is a second word
            this.aGUI.println("Test what?");
            return;
        }
        // We use the second word
        String vTest = pCommand.getSecondWord();
        try {
            Scanner vScanner = new Scanner(new File("" + vTest + ".txt"));
            this.aGUI.println("" + vTest + "is being tested...");
            while (vScanner.hasNextLine()) {
                interpretCommand(vScanner.nextLine());
            }
        } catch (FileNotFoundException pE) {// If the file is not existing
            this.aGUI.println("File not found.");
        }
        this.aIsInTestMode = false;

    }

    /**
     * Get the items of the player's inventory
     */
    public ItemList getInventory() {
        return this.aPlayer.getInventory();
    }// getInventory()

    /**
     * Print the items of the player's inventory
     */
    private void printInventory(final Command pCommand) {
        if (pCommand.hasSecondWord()) {
            this.aGUI.println("What do you mean by that?");
            return;
        }
        this.aGUI.println(this.aPlayer.getInventory().getItemString());
        this.aGUI.println("You are carrying " + this.aPlayer.getCurrentWeight() + "kg.");
        this.aGUI.println("You can carry " + this.aPlayer.getMaxWeight() + "kg.");
    }// printInventory()

} // GameEngine
