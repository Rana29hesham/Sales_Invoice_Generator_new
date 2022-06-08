
package model;

public class Item {
    private String itemName;
    private double itemPrice;
    private int count;
    private Invoice invoice;

    public Item() {
    }

    public Item(String item, double price, int count, Invoice invoice) {
        this.itemName = item;
        this.itemPrice = price;
        this.count = count;
        this.invoice = invoice;
    }

    public double getItemTotal() {
        return itemPrice * count;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItem() {
        return itemName;
    }

    public void setItem(String item) {
        this.itemName = item;
    }

    public double getPrice() {
        return itemPrice;
    }

    public void setPrice(double price) {
        this.itemPrice = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + invoice.getNum() + ", item=" + itemName + ", price=" + itemPrice + ", count=" + count + '}';
    }

    public Invoice getInvoice() {
        return invoice;
    }
    
    public String getAsCSV() {
        return invoice.getNum() + "," + itemName + "," + itemPrice + "," + count;
    }
    
}
