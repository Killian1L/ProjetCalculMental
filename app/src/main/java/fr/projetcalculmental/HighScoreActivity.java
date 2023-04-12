package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.projetcalculmental.database.ScoreBaseHelper;
import fr.projetcalculmental.database.ScoreDao;
import fr.projetcalculmental.entities.Score;

public class HighScoreActivity extends AppCompatActivity {

    private ListView listViewHighScores;
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

    }
}