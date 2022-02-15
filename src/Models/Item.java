package Models;

import Database.ItemsDB;

import java.math.BigDecimal;

public class Item {
    private String itemID, itemName, tagName;
    private BigDecimal itemPrice;
    private static int itemNumber = 0;

    public Item(String itemName, String tagName, BigDecimal itemPrice, Seller seller) {
        setItemID(itemName);
        setItemName(itemName);
        setItemPrice(itemPrice);
        setTagName(tagName);
        ItemsDB.addToDB(getItemID(), getItemName(), getItemPrice(), getTagName(), seller.getUsername());
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemName) {
        if (ItemsDB.countItems(itemName) == 0) {
            itemID = formatID(itemNumber) + "-1"; // "-1" signifies that it's the first of its kind
            itemNumber++;
        } else {
            String previousItemID = ItemsDB.getItemID(itemName);
            int duplicateNumber = ItemsDB.countItems(itemName) + 1;
            itemID = previousItemID.substring(0, 8) + "-" + duplicateNumber;
        }
    }

    public String formatID (int itemNumber) { // formats the ID as an 8-digit number (with leading zeros)
        String itemID = String.valueOf(itemNumber);
        int numberOfZeros = 8 - itemID.length();
        for (int i = 0; i < numberOfZeros; i++) {
            itemID = "0" + itemID;
        }
        return itemID;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
}