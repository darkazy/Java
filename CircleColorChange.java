import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleColorChange extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a Pane to hold the circle
        Pane pane = new Pane();

        // Create a circle with initial settings
        Circle circle = new Circle(200, 200, 100); // Center (200, 200), radius 100
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        // Add mouse event handlers to the circle
        circle.setOnMousePressed((MouseEvent e) -> {
            circle.setFill(Color.BLACK); // Change color to black on press
        });

        circle.setOnMouseReleased((MouseEvent e) -> {
            circle.setFill(Color.WHITE); // Change color to white on release
        });

        // Add the circle to the pane
        pane.getChildren().add(circle);

        // Create a scene and set it on the stage
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Circle Color Change");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
