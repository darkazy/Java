import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class StaffManager extends Application {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    // Text fields for staff details
    private TextField tfId = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfFirstName = new TextField();
    private TextField tfMi = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfTelephone = new TextField();
    private TextField tfEmail = new TextField();

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane for the form
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add form labels and fields
        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(tfId, 1, 0);
        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(tfLastName, 1, 1);
        gridPane.add(new Label("First Name:"), 0, 2);
        gridPane.add(tfFirstName, 1, 2);
        gridPane.add(new Label("MI:"), 0, 3);
        gridPane.add(tfMi, 1, 3);
        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(tfAddress, 1, 4);
        gridPane.add(new Label("City:"), 0, 5);
        gridPane.add(tfCity, 1, 5);
        gridPane.add(new Label("State:"), 0, 6);
        gridPane.add(tfState, 1, 6);
        gridPane.add(new Label("Telephone:"), 0, 7);
        gridPane.add(tfTelephone, 1, 7);
        gridPane.add(new Label("Email:"), 0, 8);
        gridPane.add(tfEmail, 1, 8);

        // Add buttons
        Button btnView = new Button("View");
        Button btnInsert = new Button("Insert");
        Button btnUpdate = new Button("Update");

        gridPane.add(btnView, 0, 9);
        gridPane.add(btnInsert, 1, 9);
        gridPane.add(btnUpdate, 2, 9);

        // Button actions
        btnView.setOnAction(e -> viewRecord());
        btnInsert.setOnAction(e -> insertRecord());
        btnUpdate.setOnAction(e -> updateRecord());

        // Set up the scene and stage
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setTitle("Staff Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewRecord() {
        String id = tfId.getText();
        if (id.isEmpty()) {
            showAlert("Error", "ID cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM Staff WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tfLastName.setText(resultSet.getString("lastName"));
                tfFirstName.setText(resultSet.getString("firstName"));
                tfMi.setText(resultSet.getString("mi"));
                tfAddress.setText(resultSet.getString("address"));
                tfCity.setText(resultSet.getString("city"));
                tfState.setText(resultSet.getString("state"));
                tfTelephone.setText(resultSet.getString("telephone"));
                tfEmail.setText(resultSet.getString("email"));
            } else {
                showAlert("Info", "No record found for ID: " + id, Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void insertRecord() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, tfId.getText());
            statement.setString(2, tfLastName.getText());
            statement.setString(3, tfFirstName.getText());
            statement.setString(4, tfMi.getText());
            statement.setString(5, tfAddress.getText());
            statement.setString(6, tfCity.getText());
            statement.setString(7, tfState.getText());
            statement.setString(8, tfTelephone.getText());
            statement.setString(9, tfEmail.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert("Success", "Record inserted successfully.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateRecord() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, tfLastName.getText());
            statement.setString(2, tfFirstName.getText());
            statement.setString(3, tfMi.getText());
            statement.setString(4, tfAddress.getText());
            statement.setString(5, tfCity.getText());
            statement.setString(6, tfState.getText());
            statement.setString(7, tfTelephone.getText());
            statement.setString(8, tfEmail.getText());
            statement.setString(9, tfId.getText());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Success", "Record updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "No record found with ID: " + tfId.getText(), Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
