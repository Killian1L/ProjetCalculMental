package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import fr.projetcalculmental.database.ScoreBaseHelper;

public class MainActivity extends AppCompatActivity {

    private Button boutonPlay;
    private Button highScoreButton;
    private Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonPlay = findViewById(R.id.boutonPlay);
        highScoreButton = findViewById(R.id.highScoreButton);
        aboutButton = findViewById(R.id.aboutButton);


        boutonPlay.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DifficultyActivity.class);
            startActivity(intent);
        });
        highScoreButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DifficultyHighScoreActivity.class);
            startActivity(intent);
        });
        aboutButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}