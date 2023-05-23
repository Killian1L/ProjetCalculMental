package fr.projetcalculmental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Random;

public class CalculMentalActivity extends AppCompatActivity {


    // Partie génération du calcul / gestion du jeu
    private TextView calculToDo;
    private int life;
    private int score;
    private int minimum;
    private int maximum;
    private int premierElementCalculToDo;
    private int deuxiemeElementCalculToDo;
    private String symboleCalculToDo;
    private double answer;
    private CountDownTimer countDownTimer;
    private long timeLeft = 0;


    private MenuItem lifeItem;
    private MenuItem scoreItem;
    private TextView timeLeftTextView;

    // Partie bouton

    private Button bouton_0;
    private Button bouton_1;
    private Button bouton_2;
    private Button bouton_3;
    private Button bouton_4;
    private Button bouton_5;
    private Button bouton_6;
    private Button bouton_7;
    private Button bouton_8;
    private Button bouton_9;
    private Button bouton_result;
    private Button bouton_substract;
    private Button bouton_dot;
    private Button bouton_pause;
    private Button bouton_clear;
    private Button bouton_remove;
    private TextView textViewCalcul;
    private String userAnswerString;

    boolean pause = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_mental);

        

        calculToDo = findViewById(R.id.calculToDo);
        timeLeftTextView = findViewById(R.id.timeLeftTextView);

        // Bouton des chiffres

        bouton_0 = findViewById(R.id.bouton_0);
        bouton_0.setOnClickListener(view -> ajouterUnChiffre(0));

        bouton_1 = findViewById(R.id.bouton_1);
        bouton_1.setOnClickListener(view -> ajouterUnChiffre(1));

        bouton_2 = findViewById(R.id.bouton_2);
        bouton_2.setOnClickListener(view -> ajouterUnChiffre(2));

        bouton_3 = findViewById(R.id.bouton_3);
        bouton_3.setOnClickListener(view -> ajouterUnChiffre(3));

        bouton_4 = findViewById(R.id.bouton_4);
        bouton_4.setOnClickListener(view -> ajouterUnChiffre(4));

        bouton_5 = findViewById(R.id.bouton_5);
        bouton_5.setOnClickListener(view -> ajouterUnChiffre(5));

        bouton_6 = findViewById(R.id.bouton_6);
        bouton_6.setOnClickListener(view -> ajouterUnChiffre(6));

        bouton_7 = findViewById(R.id.bouton_7);
        bouton_7.setOnClickListener(view -> ajouterUnChiffre(7));

        bouton_8 = findViewById(R.id.bouton_8);
        bouton_8.setOnClickListener(view -> ajouterUnChiffre(8));

        bouton_9 = findViewById(R.id.bouton_9);
        bouton_9.setOnClickListener(view -> ajouterUnChiffre(9));

        bouton_result = findViewById(R.id.bouton_result);
        bouton_result.setOnClickListener(view -> verifyAnswer());

        bouton_substract = findViewById(R.id.bouton_substract);
        bouton_substract.setOnClickListener(view -> ajouterSubStract());

        bouton_remove = findViewById(R.id.bouton_remove);
        bouton_remove.setOnClickListener(view -> remove());

        bouton_dot = findViewById(R.id.bouton_dot);
        bouton_dot.setOnClickListener(view -> ajouterDot());

        bouton_clear = findViewById(R.id.bouton_clear);
        bouton_clear.setOnClickListener(view -> clear());

        bouton_pause = findViewById(R.id.bouton_pause);
        bouton_pause.setOnClickListener(view -> pause());

        textViewCalcul = findViewById(R.id.textViewCalcul);

        life = 3;
        score = 0;
        minimum = 1;
        maximum = 10;
        timeLeft = (10000*(long) 1+1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calculmental_toolbar, menu);

        lifeItem = menu.findItem(R.id.life_toolbar);
        scoreItem = menu.findItem(R.id.score_toolbar);

        updateToolBar();
        generateCalcul();
        startTimer();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPause() {
        super.onPause();

        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        if(countDownTimer != null) {
            startTimer();
        }
    }


    private void updateToolBar() {
        lifeItem.setTitle(getString(R.string.life) + " " + life);
        scoreItem.setTitle(getString(R.string.score) + " " + score);
    }

    private void pause() {
        if(pause) {
            clickableAllButton(true);
            pause = false;
            bouton_pause.setText("pause");
            generateCalcul();
            startTimer();
        } else {
            pause = true;
            bouton_pause.setEnabled(false);
            printWarnToast(getString(R.string.game_will_be_paused));
        }
    }

    private void clickableAllButton(boolean lockStatus) {
        bouton_0.setEnabled(lockStatus);
        bouton_1.setEnabled(lockStatus);
        bouton_2.setEnabled(lockStatus);
        bouton_3.setEnabled(lockStatus);
        bouton_4.setEnabled(lockStatus);
        bouton_5.setEnabled(lockStatus);
        bouton_6.setEnabled(lockStatus);
        bouton_7.setEnabled(lockStatus);
        bouton_8.setEnabled(lockStatus);
        bouton_9.setEnabled(lockStatus);
        bouton_dot.setEnabled(lockStatus);
        bouton_substract.setEnabled(lockStatus);
        bouton_result.setEnabled(lockStatus);
        bouton_clear.setEnabled(lockStatus);
        bouton_remove.setEnabled(lockStatus);
    }


    // -------------------------- PARTIE CALCUL --------------------------


    private void generateCalcul() {
        userAnswerString = "";
        majTextView("...");

        Random random = new Random();
        premierElementCalculToDo = random.nextInt(maximum-minimum) + minimum;

        int result = random.nextInt(4-1)+1;
        switch (result) {
            case 1:
                symboleCalculToDo = "+";
                break;
            case 2:
                symboleCalculToDo = "-";
                break;
            case 3:
                symboleCalculToDo = "*";
                break;
            case 4:
                symboleCalculToDo = "/";
                break;
        }

        deuxiemeElementCalculToDo = random.nextInt(maximum-minimum) + minimum;

        calculToDo.setText(premierElementCalculToDo + symboleCalculToDo + deuxiemeElementCalculToDo);
        calculAnswer();
    }

    private void calculAnswer() {
        switch (symboleCalculToDo) {
            case "+":
                answer = premierElementCalculToDo + deuxiemeElementCalculToDo;
                break;
            case "-":
                answer = premierElementCalculToDo - deuxiemeElementCalculToDo;
                break;
            case "*":
                answer = premierElementCalculToDo * deuxiemeElementCalculToDo;
                break;
            case "/":
                answer = (double) premierElementCalculToDo / deuxiemeElementCalculToDo;
                break;
        }
    }

    private void verifyAnswer(){
        try {
            double userAnswer = Double.parseDouble(textViewCalcul.getText().toString());

            if(answer == userAnswer) {
                printGreenToast(getString(R.string.good_response));
                score++;
            } else {
                life--;
            }
            if(life > 0) {
                if(answer != userAnswer)
                    printRedToast(getString(R.string.bad_response));
                updateToolBar();
                if(!pause)
                    generateCalcul();
                else {
                    bouton_pause.setEnabled(true);
                    clickableAllButton(false);
                    pauseTimer();
                    bouton_pause.setText("unpause");
                }
            } else {
                printRedToast(getString(R.string.loose));
                toGameOver();
            }
        } catch (NumberFormatException e) {
            printWarnToast(getString(R.string.poorly_written_answer));
        }
    }

    private void toGameOver() {
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }

    // -------------------------- PARTIE TEXTE --------------------------

    private void printRedToast(String message){
        printCustomToast(message, R.drawable.custom_toast_red);
    }

    private void printRedToast(String message, int duration){
        printCustomToast(message, R.drawable.custom_toast_red, duration);
    }

    public void printGreenToast(String message) {
        printCustomToast(message, R.drawable.custom_toast_green);
    }

    public void printWarnToast(String message){
        printCustomToast(message, R.drawable.custom_toast_warn, 1500);
    }

    public void printCustomToast(String message, int custom_layout_toast_id) {
        printCustomToast(message, custom_layout_toast_id, 1000);
    }

    public void printCustomToast(String message, int custom_layout_toast_id, int duration) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout, null);
        layout.setBackgroundResource(custom_layout_toast_id);

        TextView textView = layout.findViewById(R.id.custom_toast_text);
        textView.setText(message);

        PopupWindow popupWindow = new PopupWindow(
                layout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                false
        );
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(false);

        int offsetX = 0;
        int offsetY = 16;
        int gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        popupWindow.showAtLocation(layout, gravity, offsetX, offsetY);

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, duration);
    }

    private void ajouterUnChiffre(Integer chiffreAAjouter){
        if(userAnswerString == null) {
            userAnswerString = String.valueOf(chiffreAAjouter);
        } else {
            userAnswerString = userAnswerString + chiffreAAjouter;
        }
        majTextView(userAnswerString);
    }

    private void ajouterSubStract(){
        if(userAnswerString == null || userAnswerString.length() == 0) {
            userAnswerString = "-";
            majTextView(userAnswerString);
        } else {
            printWarnToast(getString(R.string.substract_error));
        }
    }

    private void ajouterDot(){
        if(userAnswerString == null || userAnswerString.length() == 0) {
            printWarnToast(getString(R.string.dot_start_error));
        } else if(userAnswerString.contains(".")) {
            printWarnToast(getString(R.string.dot_2_error));
        } else {
            userAnswerString = userAnswerString + ".";
            majTextView(userAnswerString);
        }
    }

    private void remove() {
        if(userAnswerString == null || userAnswerString.length() == 0) {
            printWarnToast(getString(R.string.remove_nothing_error));
        } else {
            StringBuilder sb = new StringBuilder(userAnswerString);
            sb.deleteCharAt(sb.length()-1);
            userAnswerString = sb.toString();
            majTextView(userAnswerString);
        }
    }

    private void clear() {
        userAnswerString = "";
        majTextView(userAnswerString);
    }


    private void majTextView(String newUserAnswer){
        textViewCalcul.setText(newUserAnswer);
    }

    private void pauseTimer() {
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long tempsRestant) {
                timeLeft = tempsRestant;
                timeLeftTextView.setText(getString(R.string.time_left) + " " + tempsRestant / 1000);
            }

            @Override
            public void onFinish() {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                    printRedToast(getString(R.string.time_is_up), 2500);
                    clickableAllButton(false);
                    bouton_pause.setEnabled(false);

                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    toGameOver();
                                }
                            },
                            2500
                    );}
            }
        };
        countDownTimer.start();
    }

}