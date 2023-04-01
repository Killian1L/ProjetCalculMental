package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boutonPlay;
    private Button highScoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonPlay = findViewById(R.id.boutonPlay);
        highScoreButton = findViewById(R.id.highScoreButton);

        boutonPlay.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CalculMentalActivity.class);
            startActivity(intent);
        });
        highScoreButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
            startActivity(intent);
        });
    }
}