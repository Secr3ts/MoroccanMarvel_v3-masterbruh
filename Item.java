public class Item {
    private String aItemName;
    private String aItemDescription;
    private double aItemWeight;

    /**
     * Item constructor
     * 
     * @param pItemName Item name
     * @param pItemDescription Item description
     * @param pItemWeight Item weight
     */
    public Item(final String pItemName, final String pItemDescription, final double pItemWeight) {
        this.aItemName = pItemName;
        this.aItemDescription = pItemDescription;
        this.aItemWeight = pItemWeight;
    } //Item()

    /**
     * Item name getter
     * 
     * @return Item name
     */
    public String getItemName() {
        return this.aItemName;
    } //getItemName()

    /**
     * Item description getter
     * 
     * @return Item description
     */
    public String getItemDescription() {
        return this.aItemDescription;
    }//getItemDescription()

    /**
     * Item weight getter
     * 
     * @return Item weight
     */
    public double getItemWeight() {
        return this.aItemWeight;
    }//getItemWeight()

    /**
    * return short description of the item
    *
    * @return character chain of the description
    */
    public String getShortDescription() {
        return this.aItemName + " (" + this.aItemWeight + "kg)";
    } //getShortDescription()

    /**
     * return long description of the item
     * 
     * @return character chain of the long description
     */
    public String getLongDescription() {
        return "Item: " + this.aItemName + ", " + this.aItemDescription +" (" + this.aItemWeight + "kg)";
    } //getLongDescription()

    public void setItemWeight(final double pItemWeight) {
        this.aItemWeight = pItemWeight;
    }



}//Item
