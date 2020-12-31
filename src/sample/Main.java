package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.Random;

public class Main extends Application
{
    private boolean playable = true;
    private final Square[][] square =  new Square[3][3];
    private final char playerSymbol = 'X';
    private final char computerSymbol = 'O';

    @Override
    public void start(Stage primaryStage)
    {
        GridPane gameBoard = new GridPane();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                gameBoard.add(square[i][j] = new Square(), i, j);
            }
        }

        Button button = new Button();
        button.setText("New Game");
        button.setOnAction(event ->
                {
                    for (int i = 0; i < 3; i++)
                    {
                        for (int j = 0; j < 3; j++)
                        {
                            square[i][j].setSymbol(' ');
                        }
                    }
                });

        BorderPane borderPane = new BorderPane();
        GridPane borderBottom = new GridPane();
        GridPane.setMargin(borderBottom, new Insets(5, 10, 5, 10));

        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Help");

        MenuItem menuSave = new MenuItem("Save");
        MenuItem menuLoad = new MenuItem("Load");
        MenuItem menuExit = new MenuItem("Exit");
        MenuItem menuInstruction = new MenuItem("Instruction");

        menuSave.setOnAction(event -> System.out.println("Save"));

        menuLoad.setOnAction(event -> System.out.println("Load"));

        menuExit.setOnAction(event -> primaryStage.close());

        menuInstruction.setOnAction(event -> System.out.println("instruction"));

        menuFile.getItems().addAll(menuSave, menuLoad, menuExit);
        menuHelp.getItems().add(menuInstruction);
        menuBar.getMenus().addAll(menuFile, menuHelp);

        VBox vBox = new VBox(menuBar);
        vBox.setStyle("-fx-border-color: black");
        borderPane.setTop(vBox);

        borderBottom.add(button,0,0);
        borderPane.setCenter(gameBoard);
        borderPane.setBottom(borderBottom);

        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setTitle("XO game");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void isFull()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (square[i][j].getSymbol() == ' ')
                {
                    return;
                }
            }
        }
        playable=false;
    }

    public boolean isWinner(char symbol)
    {
        for (int i = 0; i < 3; i++)
        {
            if (square[i][0].getSymbol() == symbol && square[i][1].getSymbol() == symbol && square[i][2].getSymbol() == symbol)
            {
                return true;
            }
        }

        for (int j = 0; j < 3; j++)
        {
            if (square[0][j].getSymbol() == symbol && square[1][j].getSymbol() == symbol && square[2][j].getSymbol() == symbol)
            {
                return true;
            }
        }

        if (square[0][0].getSymbol() == symbol && square[1][1].getSymbol() == symbol && square[2][2].getSymbol() == symbol)
        {
            return true;
        }

        return square[0][2].getSymbol() == symbol && square[1][1].getSymbol() == symbol && square[2][0].getSymbol() == symbol;
    }
    private void computerMove()
    {
        boolean move = true;
        while (move)
        {
            Random random = new Random();
            int i = random.nextInt(3);
            int j = random.nextInt(3);

            if (square[i][j].getSymbol() == ' ')
            {
                square[i][j].setSymbol(computerSymbol);
                move=false;
            }
        }
    }

    public class Square extends Pane
    {
        private char symbol = ' ';

        public Square()
        {
            this.setStyle("-fx-border-color: black");
            this.setPrefSize(1600, 900);
            setOnMouseClicked(mouseEvent ->
            {
                if(!playable)
                    return;

                if(getSymbol()==' '&&playable)
                {
                    setSymbol(playerSymbol);
                    isFull();
                    if(isWinner(playerSymbol))
                    {
                        playable=false;
                    }

                    if(playable)
                    {
                        computerMove();
                        if(isWinner(computerSymbol))
                        {
                            playable=false;
                        }
                    }
                }
            });
        }

        public char getSymbol()
        {
            return symbol;
        }

        public void setSymbol(char symbolSelected)
        {
            this.symbol = symbolSelected;

            if (this.symbol == 'X')
            {
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                line1.setStroke(Color.RED);

                Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.setStroke(Color.RED);

                this.getChildren().addAll(line1, line2);

            }
            else if (this.symbol == 'O')
            {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,  this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.GREEN);
                ellipse.setFill(Color.TRANSPARENT);

                this.getChildren().add(ellipse);
            }
            else if(this.symbol==' ')
            {
                this.getChildren().removeAll();
                this.getChildren().setAll();
                playable=true;
            }
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

