package fr.projetcalculmental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.projetcalculmental.entities.Score;

public class ScoreAdapter extends ArrayAdapter<Score> {

    private Context context;
    private List<Score> scores;

    public ScoreAdapter(Context context, List<Score> scores) {
        super(context, R.layout.list_view_score, scores);
        this.context = context;
        this.scores = sortScores(scores);
    }

    private List<Score> sortScores(List<Score> scoreList){
        for(int i = 0; i < scoreList.size(); i++) {
            int max = i;
            for(int y = i+1; y < scoreList.size(); y++) {
                if(scoreList.get(y).getDifficulty() > scoreList.get(max).getDifficulty()) {
                    max = y;
                } else if(scoreList.get(y).getDifficulty() == scoreList.get(max).getDifficulty()) {
                    if(scoreList.get(y).getScore() > scoreList.get(max).getScore()) {
                        max = y;
                    }
                }
            }

            Score save = scoreList.get(i);
            scoreList.set(i, scoreList.get(max));
            scoreList.set(max, save);
        }

        return scoreList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_score, null);
        }

        TextView pseudoTextView = view.findViewById(R.id.pseudoTextView);
        TextView scoreTextView = view.findViewById(R.id.scoreTextView);
        TextView difficultyTextView = view.findViewById(R.id.difficultyTextView);

        Score score = scores.get(position);

        pseudoTextView.setText(score.getPseudo());
        scoreTextView.setText(String.valueOf(score.getScore()));

        String diffultyName = "Easy";
        if(score.getDifficulty() == 2) {
            diffultyName = "Hard";
        } else if(score.getDifficulty() == 1) {
            diffultyName = "Medium";
        } else if(score.getDifficulty() == 3) {
            diffultyName = "Impossible";
        }

        difficultyTextView.setText(diffultyName);

        return view;
    }
}
