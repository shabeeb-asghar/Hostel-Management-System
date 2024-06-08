package Users_Package;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Setting_Package.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

public class LostFoundController implements Initializable {

	private static final String URL = "jdbc:mysql://localhost:3306/sda_project_final_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "shabeeb";
    @FXML
    private Button HomeButton;

    @FXML
    private Button add;
    @FXML
    private Button export;
    @FXML
    private Button undo;
    @FXML
    private Button delete;
    @FXML
    private ComboBox<String> status;
    @FXML
    private DatePicker date;
    @FXML
    private TextField item;
    @FXML
    private TextArea descrp;
    @FXML
    private TextArea descArea;
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> locationCol;
    @FXML
    private TableColumn<Item, String> itemCol;
    @FXML
    private TableColumn<Item, String> statusCol;
    @FXML
    private TableColumn<Item, String> IDCol;
    @FXML
    private TableColumn<Item, String> descpCol;
    @FXML
    private TableColumn<Item, LocalDate> dateCol;
    @FXML
    private ComboBox<String> Location;
    @FXML
    private TextField ID;
    @FXML
    private ListView<String> listView;
    
    @FXML
    private Label message;

    String statusList[] = { "Lost", "Found" };
    String locations[] = { "Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Guest Room", "Kitchen (Basement)", "Kitchen (Ground Floor)", "Washroom" };
    String list[] = { "Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Guest Room", "Kitchen (Basement)", "Kitchen (Ground Floor)", "Washroom", "All Locations" };

    ObservableList<Item> data = FXCollections.observableArrayList();
    ObservableList<Item> backup = FXCollections.observableArrayList();

    @FXML
    public void addButton(ActionEvent event) {
        listView.getSelectionModel().selectLast();

        Item newItem = new Item(Location.getValue(), item.getText(), date.getValue(),
                status.getValue(), descrp.getText(), ID.getText());
        tableView.getItems().add(newItem);

        saveToDatabase(newItem);

        message.setText("A new record is added");
        item.clear();
        descrp.clear();
        ID.clear();
        updateAddButtonState();
    }

    private void saveToDatabase(Item item) {
        String query = "INSERT INTO items (location, item, date, status, description, item_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, item.getLocation());
            preparedStatement.setString(2, item.getItem());
            preparedStatement.setDate(3, java.sql.Date.valueOf(item.getDate()));
            preparedStatement.setString(4, item.getStatus());
            preparedStatement.setString(5, item.getDescription());
            preparedStatement.setString(6, item.getID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            message.setText("Failed to add record to database.");
        }
    }

    @FXML
    public void dateField(ActionEvent event) {
        date.getValue();
    }

    @FXML
    public void deleteButton(ActionEvent event) {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        backup.add(selectedItem);

        TableViewSelectionModel<Item> x = tableView.getSelectionModel();
        tableView.setSelectionModel(null);
        tableView.getItems().remove(selectedItem);
        tableView.setSelectionModel(x);

        descArea.clear();
        undo.setDisable(false);
        message.setText("The record has been successfully deleted");

        deleteFromDatabase(selectedItem);
    }

    private void deleteFromDatabase(Item item) {
        String query = "DELETE FROM items WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(item.getID()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            message.setText("Failed to delete record from database.");
        }
    }

    @FXML
    public void undoButton(ActionEvent event) {
        tableView.getItems().addAll(backup);
        undo.setDisable(true);
        backup.clear();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listView.getItems().addAll(list);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                descArea.clear();
                listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                if (!data.isEmpty() && !listView.getSelectionModel().getSelectedItem().equals("All Locations")) {
                    FilteredList<Item> filteredData = new FilteredList<>(data, e -> e.getLocation().equals(listView.getSelectionModel().getSelectedItem()));
                    tableView.setItems(filteredData);

                    tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
                        @Override
                        public void changed(ObservableValue<? extends Item> observable, Item oldItem, Item newItem) {
                            if (newItem != null) {
                                descArea.setText("Location: " + newItem.getLocation() + "\r\n" +
                                        "Item: " + newItem.getItem() + "\r\n" +
                                        "Date: " + newItem.getDate() + "\r\n" +
                                        "Status: " + newItem.getStatus() + "\r\n" +
                                        "Description: " + newItem.getDescription());
                            }
                        }
                    });
                } else {
                    tableView.setItems(data);
                }
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldItem, Item newItem) {
                if (newItem != null) {
                    descArea.setText("Location: " + newItem.getLocation() + "\r\n" +
                            "Item: " + newItem.getItem() + "\r\n" +
                            "Date: " + newItem.getDate() + "\r\n" +
                            "Status: " + newItem.getStatus() + "\r\n" +
                            "Description: " + newItem.getDescription());
                }
            }
        });

        ObservableList<String> locationsList = FXCollections.observableArrayList(locations);
        Location.setItems(locationsList);

        ObservableList<String> statusListItems = FXCollections.observableArrayList(statusList);
        status.setItems(statusListItems);

        date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });

        locationCol.setCellValueFactory(new PropertyValueFactory<Item, String>("location"));
        itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("item"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Item, LocalDate>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Item, String>("status"));
        descpCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        IDCol.setCellValueFactory(new PropertyValueFactory<Item, String>("ID"));

        tableView.setItems(data);

        // Add listeners to required fields to update add button state
        Location.valueProperty().addListener((observable, oldValue, newValue) -> updateAddButtonState());
        item.textProperty().addListener((observable, oldValue, newValue) -> updateAddButtonState());
        date.valueProperty().addListener((observable, oldValue, newValue) -> updateAddButtonState());
        status.valueProperty().addListener((observable, oldValue, newValue) -> updateAddButtonState());
        descrp.textProperty().addListener((observable, oldValue, newValue) -> updateAddButtonState());

        // Initial state update
        updateAddButtonState();
    }

    private void updateAddButtonState() {
        boolean isDisabled = Location.getValue() == null || item.getText().isEmpty() ||
                date.getValue() == null || status.getValue() == null || descrp.getText().isEmpty();
        add.setDisable(isDisabled);
    }

      @FXML
    void onClickHome(ActionEvent event) {
                    Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
    
             stage.setScene(App.getHome());

    }
}
