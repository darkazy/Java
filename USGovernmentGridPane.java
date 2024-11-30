import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class USGovernmentGridPane extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Horizontal gap between images
        gridPane.setVgap(10); // Vertical gap between images

        // Load images of the United States Government
        Image image1 = new Image("file:us_flag.jpg");
        Image image2 = new Image("file:capitol.jpg");
        Image image3 = new Image("file:white_house.jpg");
        Image image4 = new Image("file:statue_of_liberty.jpg");

        // Create ImageView objects
        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        ImageView imageView4 = new ImageView(image4);

        // Set image properties
        imageView1.setFitWidth(150);
        imageView1.setFitHeight(150);
        imageView2.setFitWidth(150);
        imageView2.setFitHeight(150);
        imageView3.setFitWidth(150);
        imageView3.setFitHeight(150);
        imageView4.setFitWidth(150);
        imageView4.setFitHeight(150);

        // Add images to the GridPane
        gridPane.add(imageView1, 0, 0); // (col=0, row=0)
        gridPane.add(imageView2, 1, 0); // (col=1, row=0)
        gridPane.add(imageView3, 0, 1); // (col=0, row=1)
        gridPane.add(imageView4, 1, 1); // (col=1, row=1)

        // Create a Scene and set the GridPane in it
        Scene scene = new Scene(gridPane, 350, 350);

        // Set the Stage
        primaryStage.setTitle("United States Government Images");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
