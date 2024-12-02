import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Random;

public class BatchUpdatePerformance extends Application {

    private Connection connection;
    private TextField tfHost = new TextField("localhost");
    private TextField tfPort = new TextField("3306");
    private TextField tfDatabase = new TextField("testdb");
    private TextField tfUsername = new TextField("root");
    private PasswordField pfPassword = new PasswordField();
    private Label lblStatus = new Label("Not connected");

    @Override
    public void start(Stage primaryStage) {
        // Main UI
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER);

        Button btnConnect = new Button("Connect to Database");
        Button btnInsertNoBatch = new Button("Insert Without Batch");
        Button btnInsertWithBatch = new Button("Insert With Batch");

        btnInsertNoBatch.setDisable(true);
        btnInsertWithBatch.setDisable(true);

        lblStatus.setStyle("-fx-text-fill: red;");

        btnConnect.setOnAction(e -> showDBConnectionDialog(btnInsertNoBatch, btnInsertWithBatch));
        btnInsertNoBatch.setOnAction(e -> insertRecords(false));
        btnInsertWithBatch.setOnAction(e -> insertRecords(true));

        mainLayout.getChildren().addAll(btnConnect, btnInsertNoBatch, btnInsertWithBatch, lblStatus);

        Scene scene = new Scene(mainLayout, 400, 200);
        primaryStage.setTitle("Batch Update Performance");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDBConnectionDialog(Button btnInsertNoBatch, Button btnInsertWithBatch) {
        Stage dialog = new Stage();
        dialog.setTitle("Database Connection");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Host:"), 0, 0);
        gridPane.add(tfHost, 1, 0);
        gridPane.add(new Label("Port:"), 0, 1);
        gridPane.add(tfPort, 1, 1);
        gridPane.add(new Label("Database:"), 0, 2);
        gridPane.add(tfDatabase, 1, 2);
        gridPane.add(new Label("Username:"), 0, 3);
        gridPane.add(tfUsername, 1, 3);
        gridPane.add(new Label("Password:"), 0, 4);
        gridPane.add(pfPassword, 1, 4);

        Button btnConnect = new Button("Connect");
        gridPane.add(btnConnect, 1, 5);

        btnConnect.setOnAction(e -> {
            String url = "jdbc:mysql://" + tfHost.getText() + ":" + tfPort.getText() + "/" + tfDatabase.getText();
            String username = tfUsername.getText();
            String password = pfPassword.getText();

            try {
                connection = DriverManager.getConnection(url, username, password);
                lblStatus.setText("Connected to the database");
                lblStatus.setStyle("-fx-text-fill: green;");
                btnInsertNoBatch.setDisable(false);
                btnInsertWithBatch.setDisable(false);
                dialog.close();
            } catch (SQLException ex) {
                lblStatus.setText("Connection failed: " + ex.getMessage());
                lblStatus.setStyle("-fx-text-fill: red;");
            }
        });

        Scene scene = new Scene(gridPane, 300, 250);
        dialog.setScene(scene);
        dialog.show();
    }

    private void insertRecords(boolean useBatch) {
        if (connection == null) {
            lblStatus.setText("Not connected to a database.");
            return;
        }

        try {
            // Prepare SQL
            String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            Random random = new Random();
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 1000; i++) {
                double num1 = random.nextDouble();
                double num2 = random.nextDouble();
                double num3 = random.nextDouble();

                preparedStatement.setDouble(1, num1);
                preparedStatement.setDouble(2, num2);
                preparedStatement.setDouble(3, num3);

                if (useBatch) {
                    preparedStatement.addBatch();
                } else {
                    preparedStatement.executeUpdate();
                }

                if (useBatch && i % 100 == 0) {
                    preparedStatement.executeBatch();
                }
            }

            if (useBatch) {
                preparedStatement.executeBatch();
            }

            long endTime = System.currentTimeMillis();
            String message = useBatch
                    ? "Inserted 1000 records using batch in " + (endTime - startTime) + " ms"
                    : "Inserted 1000 records without batch in " + (endTime - startTime) + " ms";

            lblStatus.setText(message);
        } catch (SQLException e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
