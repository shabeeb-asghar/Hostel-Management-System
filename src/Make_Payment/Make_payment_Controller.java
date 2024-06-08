package Make_Payment;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
        import javafx.scene.control.TextArea;
        
public class Make_payment_Controller {
    private static final String URL = "jdbc:mysql://localhost:3306/sda_project_final_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "shabeeb";

    @FXML
    private ComboBox<String> Type; // Updated to match the id in FXML

    @FXML
    private Button Proceed_pay; // Updated to match the id in FXML

    @FXML
    private Button back; // Updated to match the id in FXML

    @FXML
    private TextArea account_number;

    @FXML
    void initialize() {
        // Initialize the ComboBox with payment types
        Type.setItems(FXCollections.observableArrayList("Cash", "Card"));

    }

    @FXML
    void BackButton(ActionEvent event) {
        // Add logic to navigate back to the previous page
    }

    @FXML
    void PayButton(ActionEvent event) {
        String paymentType = Type.getValue();
        String accountNumber = account_number.getText();
        float price = 100; // Assuming a default price for demonstration

        if (paymentType == null || accountNumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Information",
                    "Please select payment type and enter account number.");
            return;
        }

        String challanNumber = generateChallanNumber();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false); // Start transaction

            if (paymentType.equalsIgnoreCase("cash")) {
                saveCashPayment(conn, challanNumber, accountNumber, price);
            } else if (paymentType.equalsIgnoreCase("Card")) {
                System.out.println(accountNumber);
                if (!checkAccountExists(conn, accountNumber)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid Account",
                            "Account number does not exist.");
                    return;
                }
                float balance = getAccountBalance(conn, accountNumber);
                if (balance < price) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Insufficient Balance",
                            "Your balance is insufficient for this transaction.");
                    return;
                }
                boolean proceed = showConfirmationAlert("Confirmation", "Proceed to Pay?",
                        "Account Number: " + accountNumber + "\nPrice: " + price + "\nChallan Number: " + challanNumber);
                if (!proceed) {
                    return;
                }
                updateBalance(conn, accountNumber, balance - price);
                savePayment(conn, "card", challanNumber, accountNumber, price);
                saveCardPayment(conn, challanNumber, accountNumber, price);
            }

            conn.commit(); // Commit transaction
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment Successful",
                    "Challan Number: " + challanNumber + "\nAccount Number: " + accountNumber + "\nPrice: " + price);

        } catch (SQLException e) {
            e.printStackTrace();
            
            showAlert(Alert.AlertType.ERROR, "Error", "Database Error",
                    "An error occurred while processing your request.");
        }
    }

    private String generateChallanNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return "CH" + randomNumber;
    }

    private void saveCashPayment(Connection conn, String challanNumber, String accountNumber, float price)
    throws SQLException {
System.out.print("cash");

String paymentQuery = "INSERT INTO Payment (type, challan, accNo, student_id, booking_id, hostel_id) VALUES (?, ?, ?, ?, ?, ?)";
String cashQuery = "INSERT INTO Cash (id) VALUES (LAST_INSERT_ID())";

try (PreparedStatement paymentStatement = conn.prepareStatement(paymentQuery);
     PreparedStatement cashStatement = conn.prepareStatement(cashQuery)) {

    paymentStatement.setString(1, "cash");
    paymentStatement.setString(2, challanNumber);
    paymentStatement.setString(3, accountNumber);
    paymentStatement.setInt(5, 1); // Assuming studentId is provided
    paymentStatement.setInt(6, 1); // Assuming bookingId is provided
    paymentStatement.setInt(7, 1); // Assuming hostelId is provided

    paymentStatement.executeUpdate(); // Insert payment data into Payment table
    cashStatement.executeUpdate(); // Insert data into Cash table
}
}


    private void saveCardPayment(Connection conn, String challanNumber, String accountNumber, float price)
            throws SQLException {
                System.out.print("card");
        String cardQuery = "INSERT INTO CreditCard (id, balance) VALUES (LAST_INSERT_ID(), ?)";
        try (PreparedStatement cardStatement = conn.prepareStatement(cardQuery)) {
            cardStatement.setFloat(1, price);
            cardStatement.executeUpdate();
        }
    }

    private boolean checkAccountExists(Connection conn, String accountNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM Bank b JOIN Payment p ON b.id = p.id WHERE p.accNo = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println(query);
                return count > 0;
            }
        }
        return false;
    }

    private float getAccountBalance(Connection conn, String accountNumber) throws SQLException {
        String query = "SELECT c.balance FROM CreditCard c JOIN Payment p ON c.id = p.id WHERE p.accNo = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getFloat("balance");
            }
        }
        return 0;
    }

    private void updateBalance(Connection conn, String accountNumber, float newBalance) throws SQLException {
        String query = "UPDATE CreditCard c JOIN Payment p ON c.id = p.id SET c.balance = ? WHERE p.accNo = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
statement.setFloat(1, newBalance);
statement.setString(2, accountNumber);
statement.executeUpdate();
}
}


private void savePayment(Connection conn, String type, String challanNumber, String accountNumber, float price)
        throws SQLException {
            System.out.print("pay");
            String query = "INSERT INTO Payment (type, challan, accNo, price, student_id, booking_id, hostel_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, type);
                statement.setString(2, challanNumber);
                statement.setString(3, accountNumber);
                statement.setFloat(4, price);
                statement.setInt(5, 1);
                statement.setInt(6, 1);
                statement.setInt(7, 1);
                statement.executeUpdate();
            }
}

private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
}

private boolean showConfirmationAlert(String title, String headerText, String contentText) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
}
}