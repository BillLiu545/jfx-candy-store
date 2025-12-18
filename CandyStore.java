import java.util.*;
import javafx.scene.control.*;
import javafx.collections.*;
public class CandyStore extends LinkedList<Candy> {
    public CandyStore()
    {
        init();
    }
    private LinkedList<Candy> candyBag = new LinkedList();
    // Initialize the store with candy
    public void init()
    {
        clear();
        candyBag.clear();
        add(new Candy("Chocolate Bar", 10, 1.50));
        add(new Candy("Gummy Bears", 15, 0.99));
        add(new Candy("Lollipop", 20, 0.75));
        add(new Candy("Sour Worms", 12, 1.25));
        add(new Candy("Jelly Beans", 18, 1.10));
    }
    // Convert to observable list for use in JavaFX table
    public ObservableList<Candy> toObservableList() {
        ObservableList<Candy> obsList = FXCollections.observableArrayList();
        Iterator<Candy> iter = this.iterator();
        while (iter.hasNext()) {
            Candy next = iter.next();
            obsList.add(next);
        }
        return obsList;
    }
    // Find candy in the store by name
    private Candy findCandy(String name) {
        Iterator<Candy> iter = this.iterator();
        while (iter.hasNext()) {
            Candy next = iter.next();
            if (next.toString().contains(name)) {
                return next;
            }
        }
        return null;
    }
    // Find candy in the bag by name
    private Candy findBagCandy(String name) {
        Iterator<Candy> iter = candyBag.iterator();
        while (iter.hasNext()) {
            Candy next = iter.next();
            if (next.toString().contains(name)) {
                return next;
            }
        }
        return null;
    }
    // Buy candy based on input
    public Candy buy_input()
    {
        // name of candy
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Buy Candy");
        dialog.setTitle("Buy Candy");
        dialog.setContentText("Choose the name of which candy to buy.");
        Optional<String> nameOpt = dialog.showAndWait();
        String name = nameOpt.get();
        // num of candy
        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setHeaderText("Buy Candy");
        dialog2.setTitle("Buy Candy");
        dialog2.setContentText("Choose how many of that specific candy to buy.");
        Optional<String> numOpt = dialog2.showAndWait();
        String num_str = numOpt.get();
        Integer num_bought = null;
        try
        {
            num_bought = Integer.parseInt(num_str);
        }
        catch (NumberFormatException e)
        {
            num_bought = 1;
        }
        return buyCandy(name, num_bought);
    }
    // Buy candy from the store
    public Candy buyCandy(String name, int qty) {
        Candy found = findCandy(name);
        if (found != null && found.getQty() >= qty) {
            found.setQty(found.getQty() - qty);
            if (found.getQty() == 0) {
                remove(found);
            }
            Candy purchased = new Candy(found.getName(), qty, found.getPrice());
            candyBag.add(purchased);
            return purchased;
        }
        return null;
    }
    // Return the candy bag
    public LinkedList<Candy> getCandyBag()
    {
        return candyBag;
    }
    // Total price of all candy in the bag
    public double totalCostInBag()
    {
        double totalPrice = 0;
        Iterator<Candy> iter = candyBag.iterator();
        while (iter.hasNext())
        {
            Candy next = iter.next();
            totalPrice += next.getPrice() * next.getQty();
        }
        return totalPrice;
    }
    // Remove candy based on input
    public Candy remove_input()
    {
        // name of candy
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Remove Candy");
        dialog.setTitle("Remove Candy");
        dialog.setContentText("Choose the name of which candy to remove from bag.");
        Optional<String> nameOpt = dialog.showAndWait();
        String name = nameOpt.get();
        // num of candy
        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setHeaderText("Remove Candy");
        dialog2.setTitle("Remove Candy");
        dialog2.setContentText("Choose how many of that specific candy to remove.");
        Optional<String> numOpt = dialog2.showAndWait();
        String num_str = numOpt.get();
        Integer num_removed = null;
        try
        {
            num_removed = Integer.parseInt(num_str);
        }
        catch (NumberFormatException e)
        {
            num_removed = 1;
        }
        return returnCandy(name, num_removed);
    }
    // private method to add candy back to the store
    private void addCandyToStore(Candy candy) {
        Candy found = findCandy(candy.getName());
        if (found != null) {
            found.setQty(found.getQty() + candy.getQty());
        } else {
            add(candy);
        }
    }
    // Put candy back to the store
    public Candy returnCandy(String name, int qty) {
        Candy found = findBagCandy(name);
        if (found != null && found.getQty() >= qty) {
            found.setQty(found.getQty() - qty);
            Candy returned = new Candy(found.getName(), qty, found.getPrice());
            addCandyToStore(returned);
            if (found.getQty() == 0) {
                candyBag.remove(found);
            }
            return returned;
        }
        return null;
    }
    // Main method to ensure data structure works
    public static void main(String[] args) {
        CandyStore store = new CandyStore();
        store.init();
        System.out.println(store.buyCandy("Gummy Bears", 5));
        System.out.println(store.returnCandy("Gummy Bears", 3));
        System.out.println(store.returnCandy("Gummy Bears", 2));
    }
}