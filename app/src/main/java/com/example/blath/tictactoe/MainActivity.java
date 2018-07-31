package com.example.blath.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    boolean gameIsActive = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int [][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view){
        ImageView positionClicked = (ImageView) view;
        int tappedCounter = Integer.parseInt(positionClicked.getTag().toString());
        if(gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            positionClicked.setTranslationY(-1000f);
            if (activePlayer == 0) {
                positionClicked.setImageResource(R.drawable.zero);
                activePlayer = 1;
            } else {
                positionClicked.setImageResource(R.drawable.cross);
                activePlayer = 0;
            }
            positionClicked.animate().translationYBy(1000f).setDuration(300);

            for(int[] winningPosition: winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){
                    gameOver(false, gameState[winningPosition[0]]);
                    break;
                }
            }
            if(gameIsActive) {
                boolean gameIsOver = true;
                for (int counterState : gameState) {
                    if (counterState == 2)
                        gameIsOver = false;
                }

                if (gameIsOver) {
                    gameOver(true, -1);
                }
            }
        }
    }

    public void playAgain(View view){
        gameIsActive = true;
        View winningLayout = findViewById(R.id.winningLayout);
        winningLayout.setVisibility(View.GONE);

        activePlayer = 0;
        for(int i =0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.board);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    public void gameOver(boolean isDraw, int player){
        gameIsActive = false;
        TextView winnerMessage = findViewById(R.id.winningMessage);
        if (isDraw) {
            winnerMessage.setText("It's a draw !!");
        } else{
          winnerMessage.setText("Player " + (player + 1) + " has won !!");
        }

        View winningLayout = findViewById(R.id.winningLayout);
        winningLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
