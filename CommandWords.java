 
 
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration table of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
     // a constant array that will hold all valid command words
    private final String[] aValidCommands;
    // a constant array that will hold all valid direction words
    private static final String[] aValidDirection = {"north", "south", "east", "west", "up", "down"};
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        this.aValidCommands = new String[16];
        this.aValidCommands[0] = "go";
        this.aValidCommands[1] = "help";
        this.aValidCommands[2] = "quit";
        this.aValidCommands[3] = "look";
        this.aValidCommands[4] = "eat";
        this.aValidCommands[5] = "back";
        this.aValidCommands[6] = "take";
        this.aValidCommands[7] = "drop";
        this.aValidCommands[8] = "inventory";
        this.aValidCommands[9] = "charge";
        this.aValidCommands[10] = "fire";
        this.aValidCommands[11] = "test";
        this.aValidCommands[12] = "alea";
        this.aValidCommands[13] = "items";
        this.aValidCommands[14] = "1";
        this.aValidCommands[15] = "2";
    } // CommandWords()
    /**
     * Check whether a given String is a valid direction word.
     * @param pString
     * @return true if a given string is a valid direction, false otherwise.
     */
    public static boolean isDirection(final String pString) {
        for (int vI = 0; vI < aValidDirection.length; vI++) {
            if (aValidDirection[vI].equals(pString)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand( final String pString )
    {
        for ( int vI=0; vI<this.aValidCommands.length; vI++ ) {
            if ( this.aValidCommands[vI].equals( pString ) )
                return true;
        } // for
        // if we get here, the string was not found in the commands :
        return false;
    } // isCommand()
    /**
     * Print all valid commands to System.out.
     */
    public String getCommandList() {
        String vCommandList = "";
        for ( String command : this.aValidCommands ) {
            vCommandList += command + " ";
        } // for
        return vCommandList;
    } // showAll()
} // CommandWords