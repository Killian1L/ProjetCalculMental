package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.projetcalculmental.database.ScoreBaseHelper;
import fr.projetcalculmental.database.ScoreDao;
import fr.projetcalculmental.entities.Score;

public class RegistrationActivity extends AppCompatActivity {

    private TextView yourScoreTextView;
    private EditText pseudoEditText;
    private Button saveTheScoreButton;
    private Button backToHomeButton;
    private int score;
    private ScoreDao scoreDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        yourScoreTextView = findViewById(R.id.yourScoreTextView);
        pseudoEditText = findViewById(R.id.pseudoEditText);
        saveTheScoreButton = findViewById(R.id.saveTheScoreButton);
        backToHomeButton = findViewById(R.id.backToHomeButton);

        Intent intent = getIntent();
        score = intent.getIntExtra("SCORE", 0);

        yourScoreTextView.setText(yourScoreTextView.getText().toString() + " " + score);

        saveTheScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean scoreSaved = saveScore();
                if(scoreSaved)
                    backHome();
            }
        });

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });

        scoreDao = new ScoreDao(new ScoreBaseHelper(this, "BDD", 1));
    }

    private boolean saveScore(){
        String pseudo = pseudoEditText.getText().toString();
        if(pseudo.isEmpty()) {
            Toast.makeText(this, "Le pseudo ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pseudo.length() > 30) {
            Toast.makeText(this, "Le pseudo doit faire moins de 30 caractères", Toast.LENGTH_SHORT).show();
            return false;
        }

        Score scoreToSave = new Score();
        scoreToSave.setPseudo(pseudo);
        scoreToSave.setScore(score);
        scoreDao.create(scoreToSave);

        return true;
    }

    private void backHome(){
        Intent mainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        RegistrationActivity.this.startActivity(mainIntent);
    }
}