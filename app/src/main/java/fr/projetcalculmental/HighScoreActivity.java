package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import fr.projetcalculmental.database.ScoreBaseHelper;
import fr.projetcalculmental.database.ScoreDao;
import fr.projetcalculmental.entities.Score;

public class HighScoreActivity extends AppCompatActivity {

    private TextView bestScorePseudo;
    private TextView bestScoreScore;
    private ScoreDao scoreDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        bestScorePseudo = findViewById(R.id.bestScorePseudo);
        bestScoreScore = findViewById(R.id.bestScoreScore);

        scoreDao = new ScoreDao(new ScoreBaseHelper(this, "BDD", 1));
        Score bestScore = scoreDao.getBestScore();

        if(bestScore != null ) {
            bestScorePseudo.setText(getString(R.string.bestScorePseudo) + " " + bestScore.getPseudo());
            bestScoreScore.setText(getString(R.string.bestScoreScore) + " " + bestScore.getScore());
        } else {
            bestScorePseudo.setText("Aucun score n'a été trouvé");
            bestScoreScore.setText("");
        }

    }
}