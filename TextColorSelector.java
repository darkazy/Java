import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TextColorSelector extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a Label to display text
        Label textLabel = new Label("Adjust My Color!");
        textLabel.setStyle("-fx-font-size: 24px;");

        // Create sliders for red, green, blue, and opacity
        Slider redSlider = createColorSlider();
        Slider greenSlider = createColorSlider();
        Slider blueSlider = createColorSlider();
        Slider opacitySlider = createOpacitySlider();

        // Create labels for each slider
        Label redLabel = new Label("Red:");
        Label greenLabel = new Label("Green:");
        Label blueLabel = new Label("Blue:");
        Label opacityLabel = new Label("Opacity:");

        // Add listeners to update the text color when sliders are adjusted
        ChangeListener<Number> colorChangeListener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            Color color = Color.rgb(
                (int) redSlider.getValue(),
                (int) greenSlider.getValue(),
                (int) blueSlider.getValue(),
                opacitySlider.getValue()
            );
            textLabel.setTextFill(color);
        };

        redSlider.valueProperty().addListener(colorChangeListener);
        greenSlider.valueProperty().addListener(colorChangeListener);
        blueSlider.valueProperty().addListener(colorChangeListener);
        opacitySlider.valueProperty().addListener(colorChangeListener);

        // Arrange the sliders and labels in a VBox
        VBox layout = new VBox(10,
            redLabel, redSlider,
            greenLabel, greenSlider,
            blueLabel, blueSlider,
            opacityLabel, opacitySlider,
            textLabel
        );
        layout.setStyle("-fx-padding: 20px;");

        // Create a Scene and display the Stage
        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setTitle("Text Color Selector");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a Slider for RGB values (0 to 255).
     */
    private Slider createColorSlider() {
        Slider slider = new Slider(0, 255, 0); // Range: 0 to 255, Default: 0
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        return slider;
    }

    /**
     * Creates a Slider for opacity values (0.0 to 1.0).
     */
    private Slider createOpacitySlider() {
        Slider slider = new Slider(0.0, 1.0, 1.0); // Range: 0.0 to 1.0, Default: 1.0
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        return slider;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
