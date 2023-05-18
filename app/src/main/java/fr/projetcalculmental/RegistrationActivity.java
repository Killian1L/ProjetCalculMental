package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            printWarnToast(getString(R.string.empty_pseudo_error));
            return false;
        }
        if(pseudo.length() > 30) {
            printWarnToast(getString(R.string.too_long_pseudo_error));
            return false;
        }

        Score scoreToSave = new Score();
        scoreToSave.setPseudo(pseudo);
        scoreToSave.setScore(score);
        scoreDao.create(scoreToSave);
        printGreenToast(getString(R.string.score_registred));
        return true;
    }

    private void backHome(){
        Intent mainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        RegistrationActivity.this.startActivity(mainIntent);
    }

    @Override
    public void onBackPressed()
    {
        backHome();
    }

    private void printGreenToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        layout.setBackgroundResource(R.drawable.custom_toast_green);
        TextView text = (TextView) layout.findViewById(R.id.toast_text);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void printWarnToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        layout.setBackgroundResource(R.drawable.custom_toast_warn);
        TextView text = (TextView) layout.findViewById(R.id.toast_text);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}