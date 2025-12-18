
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.Alert.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.collections.*;
import javafx.scene.control.TableColumn.*;
import javafx.application.*;
import javafx.scene.chart.*;
public class CandyStoreApp extends Application
{
    private CandyStore store = new CandyStore();
    private final ObservableList<Candy> storeList = store.toObservableList();
    // Find candy in the store by name
    private Candy candyinObs(String name) {
        Iterator<Candy> iter = storeList.iterator();
        while (iter.hasNext()) {
            Candy next = iter.next();
            if (next.toString().contains(name)) {
                return next;
            }
        }
        return null;
    }
    // Start method
    public void start(Stage mainStage)
    {
        // Set up the root
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 600, 500);
        mainStage.setScene(mainScene);
        // Set up the Main layout
        VBox mainLayout = new VBox();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.SALMON, CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(10);
        root.setCenter(mainLayout);
        mainLayout.setSpacing(20);
        // Add the title Label
        Label titleLabel = new Label("Candy Store");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
        titleLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.getChildren().addAll(titleLabel);
        // Set up the table necessary
        TableView tableView = new TableView();
        tableView.setStyle("-fx-font-size: 15");
        tableView.setItems(storeList);
        mainLayout.getChildren().add(tableView);
        // Add the columns to the table
        // name
        TableColumn<Candy, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        // price
        TableColumn<Candy, Double> priceCol = new TableColumn<>("Price (Each)");
        priceCol.setMinWidth(150);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        // quantity
        TableColumn<Candy, Integer> qtyCol = new TableColumn<>("Available");
        qtyCol.setMinWidth(150);
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        // All columns added to table
        tableView.getColumns().addAll(nameCol, qtyCol, priceCol);
        tableView.setMaxWidth(450);
        
        // Button row
        HBox buttonRow = new HBox();
        buttonRow.setSpacing (20);
        buttonRow.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(buttonRow);
        buttonRow.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        buttonRow.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        Button buyButton = new Button("Buy Candy");
        buyButton.setOnAction((event)->
        {
            Candy bought = store.buy_input();
            if (bought != null)
            {
                Candy found = candyinObs(bought.getName());
                if (found != null)
                {
                    int val = found.getQty();
                    found.setQty(val);
                    if (val == 0)
                    {
                        storeList.remove(found);
                    }
                }
            }
            tableView.refresh();
        });
        Button sellButton = new Button("Remove Candy");
        sellButton.setOnAction((event)->
        {
            Candy sold = store.remove_input();
            if (sold != null)
            {
                Candy found = candyinObs(sold.getName());
                if (found != null)
                {
                    int sum = found.getQty();
                    found.setQty(sum);
                }
                else
                {
                    storeList.add(sold);
                }
            }
            tableView.refresh();
        });
        buttonRow.getChildren().addAll(buyButton, sellButton);
        // Menu
        MenuBar topMenu = new MenuBar();
        root.setTop(topMenu);
        // File Menu
        Menu fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-font-size: 18");
        topMenu.getMenus().add(fileMenu);
        // Item - All candy in bag
        MenuItem allCandyItem = new MenuItem("Your Candy Bought");
        allCandyItem.setOnAction((event)->
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Your Candy Purchased");
            alert.setHeaderText("Your Candy Purchased");
            StringBuilder sb = new StringBuilder();
            LinkedList<Candy> candyBag = store.getCandyBag();
            Iterator<Candy> iter = candyBag.iterator();
            while (iter.hasNext())
            {
                Candy next = iter.next();
                sb.append(next.toString() + ";");
            }
            alert.setContentText(sb.toString());
            alert.showAndWait();
        });
        // Item - Checkout and reset
        MenuItem resetItem = new MenuItem("Checkout/Reset");
        resetItem.setOnAction((event)->
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thank you for your purchase");
            alert.setHeaderText("Thank you for your purchase");
            StringBuilder sb = new StringBuilder();
            double totalPrice = store.totalCostInBag();
            alert.setContentText("Your total cost is: " + totalPrice);
            alert.showAndWait();
            store.init();
            storeList.clear();
            Iterator<Candy> iter = store.toObservableList().iterator();
            while (iter.hasNext())
            {
                storeList.add(iter.next());
            }
            tableView.refresh();
        });
        MenuItem quitItem = new MenuItem("Quit");
        quitItem.setOnAction((event)->
        {
            mainStage.close();
            System.exit(0);
        });
        fileMenu.getItems().addAll(allCandyItem, resetItem, quitItem);
        
        // Show once all elements are added
        mainStage.show();
        
    }
}
