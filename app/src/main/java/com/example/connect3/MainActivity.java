package com.example.connect3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    //0:yellow, 1: red 2:empty
    int activeplayer = 0;

    int[] gamestate = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningpositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameactive = true;

    public void appear(View view) {
        ImageView counter = (ImageView) view;
        int tappedcounter = Integer.parseInt(counter.getTag().toString());
        if (gamestate[tappedcounter] == 2 && gameactive) {
            gamestate[tappedcounter] = activeplayer;
            counter.setTranslationY(-2000);
            if (activeplayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activeplayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activeplayer = 0;
            }
            counter.animate().translationYBy(2000).setDuration(500);

            boolean hasWinner = false;
            for (int[] arr : winningpositions) {
                System.out.println(gamestate[arr[0]]);
                if ((gamestate[arr[0]] == gamestate[arr[1]]) && (gamestate[arr[1]] == gamestate[arr[2]]) && gamestate[arr[0]] != 2) {
                    gameactive = false;
                    String winner = "";
                    if (activeplayer == 0) {
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }


                    Button playagainbutton = (Button) findViewById(R.id.playagainbutton);
                    TextView winnertextview = (TextView) findViewById(R.id.winnertextView);

                    winnertextview.setText(winner + " has won!");
                    playagainbutton.setVisibility(View.VISIBLE);
                    winnertextview.setVisibility(View.VISIBLE);
                    hasWinner = true;
                    break;
                }
            }
            if (!hasWinner && isDraw()) {
                gameactive = false;
                Button playagainbutton = (Button) findViewById(R.id.playagainbutton);
                TextView winnertextview = (TextView) findViewById(R.id.winnertextView);
                winnertextview.setText("It's a draw!");
                playagainbutton.setVisibility(View.VISIBLE);
                winnertextview.setVisibility(View.VISIBLE);
            }
        }
    }

    public void playagain(View view) {
        Button playagainbutton = (Button) findViewById(R.id.playagainbutton);
        TextView winnertextview = (TextView) findViewById(R.id.winnertextView);

        playagainbutton.setVisibility(View.INVISIBLE);
        winnertextview.setVisibility(View.INVISIBLE);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof FrameLayout) {
                FrameLayout frameLayout = (FrameLayout) child;
                for (int j = 0; j < frameLayout.getChildCount(); j++) {
                    View innerChild = frameLayout.getChildAt(j);
                    if (innerChild instanceof ImageView) {
                        ((ImageView) innerChild).setImageDrawable(null);
                    }
                }
            }
        }

        activeplayer = 0;

        for (int i = 0; i < gamestate.length; i++) {
            gamestate[i] = 2;
        }
        gameactive = true;
    }

    private boolean isDraw() {
        for (int state : gamestate) {
            if (state == 2) {
                return false;
            }
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
