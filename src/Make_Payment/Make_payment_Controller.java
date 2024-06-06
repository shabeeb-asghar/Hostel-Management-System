package Make_Payment;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
import java.util.Random;

import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
        import javafx.scene.control.TextArea;
        
public class Make_payment_Controller {
        
    @FXML
    private ComboBox<String> Type;

    @FXML
    private Button Proceed_pay;

    @FXML
    private Button back;

    @FXML
    private TextArea account_number;

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

        // Generate a challan number
        String challanNumber = generateChallanNumber();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");

            if (paymentType.equalsIgnoreCase("cash")) {
                // Save data in cash table
                saveCashPayment(conn, challanNumber, accountNumber, price);
            } else if (paymentType.equalsIgnoreCase("card")) {
                // Implement card payment logic here
                  // Check if account number exists in the bank table
                if (!checkAccountExists(conn, accountNumber)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid Account",
                            "Account number does not exist.");
                    return;
                }

                // Check if balance is sufficient
                float balance = getAccountBalance(conn, accountNumber);
                if (balance < price) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Insufficient Balance",
                            "Your balance is insufficient for this transaction.");
                    return;
                }

                // Display confirmation message
                boolean proceed = showConfirmationAlert("Confirmation", "Proceed to Pay?",
                        "Account Number: " + accountNumber + "\nPrice: " + price + "\nChallan Number: " + challanNumber);
                if (!proceed) {
                    return; // User canceled the payment
                }

                // Deduct balance and update data
                updateBalance(conn, accountNumber, balance - price);
            
            savePayment(conn, paymentType, challanNumber, accountNumber, price);
            }

           

            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment Successful",
                    "Challan Number: " + challanNumber + "\nAccount Number: " + accountNumber + "\nPrice: " + price);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Database Error",
                    "An error occurred while processing your request.");
        }
    }

    // Method to generate a unique challan number
    private String generateChallanNumber() {
        // Implement your logic to generate a unique challan number
        Random random = new Random();
    int randomNumber = random.nextInt(900000) + 100000; // Generate a random number between 100000 and 999999

    // Concatenate with prefix
    return "CH" + randomNumber;
    }

    // Method to save cash payment data in the database
    private void saveCashPayment(Connection conn, String challanNumber, String accountNumber, float price)
            throws SQLException {
                String paymentQuery = "INSERT INTO Payment (type, challan, accNo, price) VALUES (?, ?, ?, ?)";
                String cashQuery = "INSERT INTO Cash (challan, accNo, price) VALUES (?, ?, ?)";
            
                try (PreparedStatement paymentStatement = conn.prepareStatement(paymentQuery);
                     PreparedStatement cashStatement = conn.prepareStatement(cashQuery)) {
            
                    // Insert data into the Payment table
                    paymentStatement.setString(1, "cash");
                    paymentStatement.setString(2, challanNumber);
                    paymentStatement.setString(3, accountNumber);
                    paymentStatement.setFloat(4, price);
                    paymentStatement.executeUpdate();
            
                    // Insert data into the Cash table
                    cashStatement.setString(1, challanNumber);
                    cashStatement.setString(2, accountNumber);
                    cashStatement.setFloat(3, price);
                    cashStatement.executeUpdate();
                }
    }

   // Method to check if an account exists in the bank table
   private boolean checkAccountExists(Connection conn, String accountNumber) throws SQLException {
    String query = "SELECT COUNT(*) FROM Bank WHERE accNo = ?";
    PreparedStatement statement = conn.prepareStatement(query);
    statement.setString(1, accountNumber);
    ResultSet resultSet = statement.executeQuery();
    resultSet.next();
    int count = resultSet.getInt(1);
    return count > 0;
}

// Method to retrieve account balance from the bank table
private float getAccountBalance(Connection conn, String accountNumber) throws SQLException {
    String query = "SELECT balance FROM Bank WHERE accNo = ?";
    PreparedStatement statement = conn.prepareStatement(query);
    statement.setString(1, accountNumber);
    ResultSet resultSet = statement.executeQuery();
    resultSet.next();
    return resultSet.getFloat("balance");
}

// Method to update account balance in the bank table
private void updateBalance(Connection conn, String accountNumber, float newBalance) throws SQLException {
    String query = "UPDATE Bank SET balance = ? WHERE accNo = ?";
    PreparedStatement statement = conn.prepareStatement(query);
    statement.setFloat(1, newBalance);
    statement.setString(2, accountNumber);
    statement.executeUpdate();
}

// Method to save payment data in the database
private void savePayment(Connection conn, String type, String challanNumber, String accountNumber, float price)
        throws SQLException {
    String paymentQuery = "INSERT INTO Payment (type, challan, accNo, price) VALUES (?, ?, ?, ?)";
    String cardQuery = "INSERT INTO Card (challan, accNo, price) VALUES (?, ?, ?)";

    try (PreparedStatement paymentStatement = conn.prepareStatement(paymentQuery);
         PreparedStatement cardStatement = conn.prepareStatement(cardQuery)) {

        // Insert data into the Payment table
        paymentStatement.setString(1, type);
        paymentStatement.setString(2, challanNumber);
        paymentStatement.setString(3, accountNumber);
        paymentStatement.setFloat(4, price);
        paymentStatement.executeUpdate();

        // Insert data into the Card table
        cardStatement.setString(1, challanNumber);
        cardStatement.setString(2, accountNumber);
        cardStatement.setFloat(3, price);
        cardStatement.executeUpdate();
    }
}

// Method to show an alert dialog
private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
}

// Method to show a confirmation dialog
private boolean showConfirmationAlert(String title, String headerText, String contentText) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;

}
}
