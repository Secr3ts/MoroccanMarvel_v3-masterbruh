/**
 * An even is fired up when you enter certain Rooms, impeaching you to exit while the condition is not met.
 * @author Sofyan Guillermet-Laouad and Aloïs Fournier 19.05.23
 */
public class Event {
    private String aName;
    private UserInterface aGUI;
    private boolean aEventDone = false;
    private String choice1;
    private String choice2;
    private int correctChoice;
    private Item aItem;
    private GameEngine aGameEngine;
    private Flag aFlag;
    private boolean aRanEvent = false;

    public Event(final UserInterface pGUI, final String pName, final String pChoice1, final String pChoice2) {
        this(pGUI, pName, pChoice1, pChoice2, 1, null, null);
    }
    public Event(final UserInterface pGUI, final String pName, final String pChoice1, final String pChoice2, final int pCorrectChoice, final Item pItem, final Flag pFlag) {
        this.aGUI = pGUI;
        this.aName = pName;
        this.choice1 = pChoice1;
        this.choice2 = pChoice2;
        this.correctChoice = pCorrectChoice;
        this.aItem = pItem;
        this.aFlag = pFlag;
    }
    
    
    public void displayChoice() {
        this.aGUI.println("You have to choose between two options :");
        this.aGUI.println("1. " + this.choice1);
        this.aGUI.println("2. " + this.choice2);
        
    }
    
    public boolean testChoice(final int pChoice) {
        if (pChoice == this.correctChoice) {
            return true;
        } else {
            return false;        
        }
    }
    
    public void run(final GameEngine pGameEngine) {
        if (!aRanEvent) {
            this.aRanEvent = true;
            this.aGUI.println("You entered a Room with an event in it, you can not leave until you have completed the event.");
            this.aGUI.println("You have to find a way to exit the Room.");
            this.displayChoice();
            this.aGameEngine = pGameEngine;
            if(!this.aItem.getItemName().equals("golden-coins")){
            this.aGameEngine.interpretCommand("take " + this.aItem.getItemName());
            }
        }
    }
        
    public boolean isEventRan() {
        return this.aRanEvent;
    }
    
    public void setEventDone() {
        this.aEventDone = true;
        this.aGUI.println("The event has been completed !");
        this.aGUI.println("Check your bad/good action counter to see if you made the right choice !");
        if (this.aItem.getItemName().equals("messy-clothes")) {
            this.aGameEngine.interpretCommand("drop messy-clothes");
        }
    }

    public void setGUI(final UserInterface pGUI) {
        this.aGUI = pGUI;
    }
    public boolean isEventDone() {
        return this.aEventDone;
    }

    public Item getEventItem() {
        return this.aItem;
    }

    public Flag getEventFlag() {
        return this.aFlag;
    }

    public void addFlag(final Flag pFlag) {
        this.aFlag = pFlag;
    }
}
