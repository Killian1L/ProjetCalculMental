package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.projetcalculmental.database.ScoreBaseHelper;
import fr.projetcalculmental.database.ScoreDao;
import fr.projetcalculmental.entities.Score;

public class HighScoreActivity extends AppCompatActivity {

    private ListView listViewHighScores;
    private TextView backToHomeButton;
    private ScoreDao scoreDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        listViewHighScores = findViewById(R.id.listViewHighScores);

        scoreDao = new ScoreDao(new ScoreBaseHelper(this, "BDD", 1));
        List<Score> bestScores = scoreDao.getBestScore();

        ScoreAdapter adapter = new ScoreAdapter(this, bestScores);
        listViewHighScores.setAdapter(adapter);

        backToHomeButton = findViewById(R.id.backToHomeHighScoreButton);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
    }

    /* TODO :
    - réduire le temps d'apparition des message "bonne réponse" et "mauvaise réponse"
    - mettre plus d'espace entre les Toast et les boutons
    - rajouter un textView sur HighScoreActivity ?
    - rajouter des choses dans le About
     */

    private void backHome(){
        Intent mainIntent = new Intent(HighScoreActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        HighScoreActivity.this.startActivity(mainIntent);
    }
}