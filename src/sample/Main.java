package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


import java.util.Random;

public class Main extends Application {
    private final char playerSymbol = 'X';
    private final char computerSymbol = 'O';
    boolean playable = true;
    private Square[][] square =  new Square[3][3];
    private final Label statusGame = new Label("Your move (Symbol X)");

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

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gameBoard);
        borderPane.setBottom(statusGame);

        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setTitle("XO game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public boolean isFull()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (square[i][j].getSymbol() == ' ')
                {
                    return false;
                }
            }
        }
        return true;
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

    public class Square extends Pane
    {
        private char symbol = ' ';

        public Square()
        {
            this.setStyle("-fx-border-color: black");
            this.setPrefSize(1600, 900);
            this.setOnMouseClicked(e -> playerTurn());
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

                if (isWinner(computerSymbol))
                {
                    statusGame.setTextFill(Color.GREEN);
                    statusGame.setText(computerSymbol + " won! The game is over");
                    playable=false;

                }
                else if (isFull())
                {
                    statusGame.setText("Draw! The game is over");
                    System.out.println("Fully");
                    playable=false;
                }

            }
        }

        private void playerTurn()
        {
            if (symbol == ' ' && playable)
            {
                setSymbol(playerSymbol);

                if (isWinner(playerSymbol))
                {
                    statusGame.setTextFill(Color.RED);
                    statusGame.setText(playerSymbol + " won! The game is over");
                    playable=false;

                }

                else if (isFull())
                {
                    statusGame.setText("Draw! The game is over");
                    playable=false;
                }

                else
                {
                    computerMove();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

