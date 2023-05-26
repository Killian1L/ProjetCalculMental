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

import java.util.ArrayList;
import java.util.List;

import fr.projetcalculmental.database.ScoreBaseHelper;
import fr.projetcalculmental.database.ScoreDao;
import fr.projetcalculmental.entities.Score;

public class RegistrationActivity extends AppCompatActivity {

    private TextView yourScoreTextView;
    private EditText pseudoEditText;
    private Button saveTheScoreButton;
    private Button backToHomeButton;
    private int score;
    private int difficulty;
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
        difficulty = intent.getIntExtra("difficulty", 0);

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

        if(isPseudoIncorrect(pseudo)) {
            return false;
        }

        Score scoreToSave = new Score();
        scoreToSave.setPseudo(pseudo);
        scoreToSave.setScore(score);
        scoreToSave.setDifficulty(difficulty);

        scoreDao.create(scoreToSave);
        printGreenToast(getString(R.string.score_registred));
        return true;
    }

    private boolean isPseudoIncorrect(String pseudo) {
        if(pseudo.isEmpty()) {
            printWarnToast(getString(R.string.empty_pseudo_error));
            return true;
        }
        if(pseudo.length() > 30) {
            printWarnToast(getString(R.string.too_long_pseudo_error));
            return true;
        }

        List<String> correctCarac = getCorrectCarac();
        boolean check = false;

        for (int i = 0; i < pseudo.length(); i++) {
            String character = Character.toString(pseudo.charAt(i));
            if (!correctCarac.contains(character)) {
                check = true;
                break;
            }
        }

        if(check) {
            printWarnToast(getString(R.string.contains_invalid_character));
            return true;
        }


        return false;
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

    private List<String> getCorrectCarac() {
        List<String> characters = new ArrayList<>();

        // Ajouter les lettres majuscules A à Z
        for (char c = 'A'; c <= 'Z'; c++) {
            characters.add(Character.toString(c));
        }

        // Ajouter les lettres minuscules a à z
        for (char c = 'a'; c <= 'z'; c++) {
            characters.add(Character.toString(c));
        }

        // Ajouter les chiffres 0 à 9
        for (int i = 0; i <= 9; i++) {
            characters.add(Integer.toString(i));
        }

        characters.add(" ");
        characters.add("『");
        characters.add("Ø");
        characters.add("!");
        characters.add("』");
        characters.add("à");
        characters.add("é");
        characters.add("è");
        characters.add("ù");
        characters.add("$");
        characters.add(".");
        characters.add("ô");
        characters.add("œ");
        characters.add("-");
        characters.add("_");
        characters.add("@");
        characters.add("#");
        characters.add("&");
        characters.add("(");
        characters.add(")");
        characters.add("/");
        characters.add("\\");
        characters.add("<");
        characters.add(">");
        characters.add(":");
        characters.add(",");
        characters.add("?");
        characters.add("§");
        characters.add(";");
        characters.add("\"");
        characters.add("'");
        characters.add("?");
        characters.add("*");
        characters.add("£");
        characters.add("¤");
        characters.add("^");
        characters.add("%");
        characters.add("+");
        characters.add("ç");
        characters.add("Ç");
        characters.add("|");
        characters.add("`");
        characters.add("[");
        characters.add("]");
        characters.add("{");
        characters.add("}");
        characters.add("~");
        characters.add("²");
        characters.add("Ç");
        characters.add("©");
        characters.add("$");
        characters.add("✓");
        characters.add("✔");
        characters.add("¥");
        characters.add("°");
        characters.add("™");

        return characters;
    }
}