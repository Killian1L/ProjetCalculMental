package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyActivity extends AppCompatActivity {

    private Button boutonEasy;
    private Button boutonMedium;
    private Button boutonHard;
    private Button boutonImpossible;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        boutonEasy = findViewById(R.id.boutonEasy);
        boutonMedium = findViewById(R.id.boutonMedium);
        boutonHard = findViewById(R.id.boutonHard);
        boutonImpossible = findViewById(R.id.boutonImpossible);

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

        backToHomeButton = findViewById(R.id.backToHomeDifficultyButton);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
    }

    private void backHome(){
        Intent mainIntent = new Intent(DifficultyActivity.this, MainActivity.class);
        DifficultyActivity.this.startActivity(mainIntent);
    }

    private void launchGame(int difficulty) {
        Intent intent = new Intent(DifficultyActivity.this, CalculMentalActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}