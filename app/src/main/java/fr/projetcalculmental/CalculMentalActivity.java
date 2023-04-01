package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CalculMentalActivity extends AppCompatActivity {

    private TextView calculToDo;
    private EditText answerText;

    private int life;
    private int score;
    private int minimum;
    private int maximum;
    private int premierElement;
    private int deuxiemeElement;
    private String symbole;
    private double answer;

    private MenuItem lifeItem;
    private MenuItem scoreItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_mental);

        calculToDo = findViewById(R.id.calculToDo);
        answerText = findViewById(R.id.answerText);

        answerText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    verifyAnswer();
                    return true;
                }
                return false;
            }
        });

        life = 3;
        score = 0;
        minimum = 1;
        maximum = 10;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calculmental_toolbar, menu);

        lifeItem = menu.findItem(R.id.life_toolbar);
        scoreItem = menu.findItem(R.id.score_toolbar);

        updateToolBar();

        scoreItem.setOnMenuItemClickListener(view -> generateCalcul());

        return super.onCreateOptionsMenu(menu);
    }

    private void updateToolBar() {
        lifeItem.setTitle(getString(R.string.life) + " " + life);
        scoreItem.setTitle(getString(R.string.score) + " " + score);
    }

    private boolean generateCalcul() {
        Random random = new Random();
        premierElement = random.nextInt(maximum-minimum) + minimum;

        int result = random.nextInt(5);
        switch (result) {
            case 1:
                symbole = "+";
                break;
            case 2:
                symbole = "-";
                break;
            case 3:
                symbole = "*";
                break;
            case 4:
                symbole = "/";
                break;
        }

        deuxiemeElement = random.nextInt(maximum-minimum) + minimum;

        calculToDo.setText(premierElement + symbole + deuxiemeElement);
        calculAnswer();

        return true; // a retirer quand plus de clicklistenr sur score
    }

    private void calculAnswer() {
        switch (symbole) {
            case "+":
                answer = premierElement + deuxiemeElement;
                break;
            case "-":
                answer = premierElement - deuxiemeElement;
                break;
            case "*":
                answer = premierElement * deuxiemeElement;
                break;
            case "/":
                answer = (double) premierElement / deuxiemeElement;
                break;
        }
    }

    private void verifyAnswer(){
        try {
            String userAnswerText = answerText.getText().toString()
                                    .replace(",", ".");
            double userAnswer = Double.parseDouble(userAnswerText);
            if(answer == userAnswer) {
                Toast.makeText(this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                score++;
            } else {
                Toast.makeText(this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                life--;
            }
            if(life > 0) {
                updateToolBar();
                generateCalcul();
            } else {
                Intent intent = new Intent(this, RegistrationActivity.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Votre réponse est mal écrite !",
                            Toast.LENGTH_SHORT).show();
        }
    }
}