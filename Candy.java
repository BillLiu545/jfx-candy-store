public class Candy {
    private String name;
    private int qty;
    private double price;
    public Candy(String name, int qty, double price)
    {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }
    public String getName()
    {
        return name;
    }
    public int getQty() {
        return qty;
    }
    public double getPrice() {
        return price;
    }
    public void setQty(int qty)
    {
        this.qty = qty;
    }
    public String toString()
    {
        return qty + " " + name + " at $" + price + " each";
    }
}

