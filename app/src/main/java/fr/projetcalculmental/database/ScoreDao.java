package fr.projetcalculmental.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.projetcalculmental.entities.Score;

public class ScoreDao extends BaseDao<Score> {

    public ScoreDao(DataBaseHelper helper) {
        super(helper);
    }

    public static String tableName = "Score";
    public static String columnPseudo = "Pseudo";
    public static String columnScore = "Score";
    public static String columnDifficulty = "Difficulty";
    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    protected void putValues(ContentValues values, Score entity) {
        values.put(columnPseudo, entity.getPseudo());
        values.put(columnScore, entity.getScore());
        values.put(columnDifficulty, entity.getDifficulty());
    }

    @Override
    protected Score getEntity(Cursor cursor) {
        Score score = new Score();

        Integer indexPseudo = cursor.getColumnIndex(columnPseudo);
        Integer indexScore = cursor.getColumnIndex(columnScore);
        Integer indexDifficulty = cursor.getColumnIndex(columnDifficulty);

        score.setPseudo(cursor.getString(indexPseudo));
        score.setScore(cursor.getInt(indexScore));
        score.setDifficulty(cursor.getInt(indexDifficulty));

        return score;
    }

    public List<Score> getBestScore(int difficulty) {
        List<Score> bestScores = new ArrayList<>();

        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        String query =  "SELECT "+columnPseudo+", " + columnDifficulty + ", MAX("+columnScore+") AS " + columnScore + " " +
                        "FROM " +getTableName() + " " +
                        "WHERE " + columnDifficulty + " = " + difficulty + " " +
                        "GROUP BY " + columnPseudo + ", " + columnDifficulty + " " +
                        "ORDER BY " + columnScore + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    Score highscore = this.getEntity(cursor);
                    bestScores.add(highscore);
                }catch (IllegalStateException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bestScores;
    }
}
