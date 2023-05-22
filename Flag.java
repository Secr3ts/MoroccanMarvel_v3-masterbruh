/**
 * Flag class used to track the advancement of the Player
 */
public class Flag {
    private String aFlagName;

    /**
     * Constructor of the class Flag
     * @param pFlagName
     */
    public Flag(final String pFlagName) {
        this.aFlagName = pFlagName;
    }

    /**
     * Returns the name of the flag
     * @return the name of the flag
     */
    public String getFlagName() {
        return this.aFlagName;
    }
}
