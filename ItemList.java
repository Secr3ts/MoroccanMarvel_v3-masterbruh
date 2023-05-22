import java.util.HashMap;
import java.util.Set;

public class ItemList{
    private HashMap<String, Item> aItemList;
    private String aLocation;

    public ItemList(String pLocation){
        this.aItemList = new HashMap<String, Item>();
        this.aLocation = pLocation;
    }

    public void addItem(final String pItemName, final Item pItem){
        this.aItemList.put(pItemName, pItem);
    }

    public Item getItem(final String pItemName){
        return this.aItemList.get(pItemName);
    }

    public void removeItem(final String pItemName){
        this.aItemList.remove(pItemName);
    }

    public boolean hasItem(final String pItemName){
        if(this.aItemList.containsKey(pItemName)){
            return true;
        } else{
            return false;
        }
    }

    public String getItemString() {
        if(this.aItemList.isEmpty()){
            return "There is nothing here in " + this.aLocation + ".";  
        }

        StringBuilder vItems = new StringBuilder("Items in " + this.aLocation + ":");
        Set<String> vKeys = this.aItemList.keySet();
        for(String vItem : vKeys) {
            vItems.append(" " + vItem);
        }
        return vItems.toString();
    }//getItemString()

}