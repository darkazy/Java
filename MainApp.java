import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI Components for Player Names
        TextField player1Field = new TextField();
        player1Field.setPromptText("Player 1 Name");

        TextField player2Field = new TextField();
        player2Field.setPromptText("Player 2 Name");

        // Start and New Game Buttons
        Button startGameButton = new Button("Start Game");
        Button newGameButton = new Button("New Game");
        newGameButton.setDisable(true);

        HBox playerInputBox = new HBox(10, player1Field, player2Field);
        playerInputBox.setAlignment(Pos.CENTER);

        HBox controlButtonsBox = new HBox(10, startGameButton, newGameButton);
        controlButtonsBox.setAlignment(Pos.CENTER);

        // Game Grid
        GridPane gameGrid = new GridPane();
        gameGrid.setAlignment(Pos.CENTER);
        gameGrid.setVgap(5);
        gameGrid.setHgap(5);

        Text statusText = new Text("Enter names and press Start Game.");

        VBox mainLayout = new VBox(10, playerInputBox, controlButtonsBox, gameGrid, statusText);
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 400, 400);

        GameController gameController = new GameController(gameGrid, statusText);

        startGameButton.setOnAction(e -> {
            String player1 = player1Field.getText().trim();
            String player2 = player2Field.getText().trim();

            if (!player1.isEmpty() && !player2.isEmpty()) {
                gameController.startGame(player1, player2);
                startGameButton.setDisable(true);
                newGameButton.setDisable(false);
                player1Field.setDisable(true);
                player2Field.setDisable(true);
            } else {
                statusText.setText("Both player names must be entered.");
            }
        });

        newGameButton.setOnAction(e -> {
            gameController.resetGame();
            startGameButton.setDisable(false);
            newGameButton.setDisable(true);
            player1Field.setDisable(false);
            player2Field.setDisable(false);
            player1Field.clear();
            player2Field.clear();
            statusText.setText("Enter names and press Start Game.");
        });

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class GameController {
    private final GridPane gameGrid;
    private final Text statusText;
    private String currentPlayer;
    private String player1;
    private String player2;
    private final TicTacToeButton[][] buttons = new TicTacToeButton[3][3];

    public GameController(GridPane gameGrid, Text statusText) {
        this.gameGrid = gameGrid;
        this.statusText = statusText;
        initializeGrid();
    }

    private void initializeGrid() {
        gameGrid.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeButton button = new TicTacToeButton();
                buttons[i][j] = button;
                gameGrid.add(button, j, i);
                int row = i;
                int col = j;
                button.setOnAction(e -> handleMove(button, row, col));
            }
        }
    }

    public void startGame(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        statusText.setText(currentPlayer + "'s turn (X)");
        initializeGrid();
    }

    private void handleMove(TicTacToeButton button, int row, int col) {
        if (button.isMarked()) return;

        button.mark(currentPlayer.equals(player1) ? "X" : "O");
        if (checkWin()) {
            statusText.setText(currentPlayer + " wins!");
            disableAllButtons();
        } else if (isBoardFull()) {
            statusText.setText("It's a draw!");
        } else {
            currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
            statusText.setText(currentPlayer + "'s turn (" + (currentPlayer.equals(player1) ? "X" : "O") + ")");
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText()) &&
                !buttons[i][0].getText().isEmpty()) {
                return true;
            }
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText()) &&
                !buttons[0][i].getText().isEmpty()) {
                return true;
            }
        }
        return (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()) ||
               (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().isEmpty());
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!buttons[i][j].isMarked()) return false;
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }

    public void resetGame() {
        initializeGrid();
        statusText.setText("Game reset. Enter names and press Start Game.");
    }
}

class TicTacToeButton extends Button {
    private boolean marked = false;

    public TicTacToeButton() {
        setMinSize(80, 80);
        setStyle("-fx-font-size: 24;");
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark(String symbol) {
        setText(symbol);
        marked = true;
    }
}
