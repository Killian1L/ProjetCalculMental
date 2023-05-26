package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import fr.projetcalculmental.database.ScoreBaseHelper;
import fr.projetcalculmental.database.ScoreDao;
import fr.projetcalculmental.entities.Score;

public class HighScoreActivity extends AppCompatActivity {

    private ListView listViewHighScores;
    private Button backToLastPageButton;
    private ScoreDao scoreDao;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        listViewHighScores = findViewById(R.id.listViewHighScores);

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("difficulty", 0);

        scoreDao = new ScoreDao(new ScoreBaseHelper(this, "BDD", 1));
        List<Score> bestScores = scoreDao.getBestScore(difficulty);

        ScoreAdapter adapter = new ScoreAdapter(this, bestScores);
        listViewHighScores.setAdapter(adapter);

        backToLastPageButton = findViewById(R.id.backToLastPageHighScoreButton);

        backToLastPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLastPage();
            }
        });
    }

    private void backToLastPage(){
        finish();
    }
}