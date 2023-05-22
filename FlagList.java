import java.util.HashMap;

/**
 * The FlagList that mutualize the flags of the game
 * 
 * @author Sofyan Guillermet-Laouad (with the help of Aloïs Fournier)
 */
public class FlagList {
    private HashMap<String, Flag> aFlags;

    /**
     * Constructor of the class FlagList
     */
    public FlagList() {
        this.aFlags = new HashMap<>();
    }
    /**
     * Add a flag to the list of flags
     * 
     * @param pFlag
     */
    public void addFlag(final Flag pFlag) {
        this.aFlags.put(pFlag.getFlagName(), pFlag);
    }

    /**
     * Returns the flag with the given name
     * 
     * @param pFlagName
     * @return the flag with the given name
     */
    public Flag getFlag(final String pFlagName) {
        return this.aFlags.get(pFlagName);
    }

    /**
     * Return true if the flag exists, false otherwise
     * 
     * @param pFlagName
     */
    public boolean hasFlag(final String pFlagName) {
        return this.aFlags.containsKey(pFlagName);
    }

    /**
     * Remove the flag with the given name
     * 
     * @param pFlagName
     */
    public void removeFlag(final String pFlagName) {
        this.aFlags.remove(pFlagName);
    }
}
