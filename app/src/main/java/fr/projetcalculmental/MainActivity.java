package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boutonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonPlay = findViewById(R.id.boutonPlay);
        boutonPlay.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CalculMentalActivity.class);
            startActivity(intent);
        });
    }
}