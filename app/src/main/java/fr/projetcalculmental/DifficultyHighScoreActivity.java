package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyHighScoreActivity extends AppCompatActivity {

    private Button boutonEasy;
    private Button boutonMedium;
    private Button boutonHard;
    private Button boutonImpossible;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_high_score);

        boutonEasy = findViewById(R.id.boutonEasyHighScore);
        boutonMedium = findViewById(R.id.boutonMediumHighScore);
        boutonHard = findViewById(R.id.boutonHardHighScore);
        boutonImpossible = findViewById(R.id.boutonImpossibleHighScore);

        boutonEasy.setOnClickListener(view -> {
            launchGame(0);
        });
        boutonMedium.setOnClickListener(view -> {
            launchGame(1);
        });
        boutonHard.setOnClickListener(view -> {
            launchGame(2);
        });
        boutonImpossible.setOnClickListener(view -> {
            launchGame(3);
        });

        backToHomeButton = findViewById(R.id.backToHomeDifficultyHighScoreButton);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
    }

    private void backHome(){
        Intent mainIntent = new Intent(DifficultyHighScoreActivity.this, MainActivity.class);
        DifficultyHighScoreActivity.this.startActivity(mainIntent);
    }

    private void launchGame(int difficulty) {
        Intent intent = new Intent(DifficultyHighScoreActivity.this, HighScoreActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}