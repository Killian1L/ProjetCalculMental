package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;

public class CalculMentalActivity extends AppCompatActivity {

    private TextView calculToDo;

    private int life;
    private int score;
    private int minimum;
    private int maximum;
    private int premierElement;
    private int deuxiemeElement;
    private String symbole;

    private MenuItem lifeItem;
    private MenuItem scoreItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_mental);

        calculToDo = findViewById(R.id.calculToDo);

        life = 3;
        score = 0;
        minimum = 1;
        maximum = 100;
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

        return true; // a retirer quand plus de clicklistenr sur score
    }
}