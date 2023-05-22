 

/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author Sofyan Guillermet-Laouad
 */
public class Command
{
    private String aCommandWord;
    private String aSecondWord;
    
    public Command(final String pFirstWord, final String pSecondWord) {
        this.aCommandWord = pFirstWord;
        this.aSecondWord = pSecondWord;
    }
    
    public String getCommandWord() {
        return this.aCommandWord;
    }
    
    public String getSecondWord() {
        return this.aSecondWord;
    }
    
    public void setCommandWord(final String pNewCommandWord) {
        this.aCommandWord = pNewCommandWord;
    }
    
    public void setSecondWord(final String pNewSecondWord) {
        this.aSecondWord = pNewSecondWord;
    }
    
    public boolean hasSecondWord() {
        return this.aSecondWord == null ? false : true;
    }
    
    public boolean isUnknown() {
        return this.aCommandWord == null ? true : false;
    }
} // Command
