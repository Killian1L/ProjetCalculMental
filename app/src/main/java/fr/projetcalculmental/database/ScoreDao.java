package fr.projetcalculmental.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.projetcalculmental.entities.Score;

public class ScoreDao extends BaseDao<Score> {

    public ScoreDao(DataBaseHelper helper) {
        super(helper);
    }

    public static String tableName = "Score";
    public static String columnPseudo = "Pseudo";
    public static String columnScore = "Score";
    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    protected void putValues(ContentValues values, Score entity) {
        values.put(columnPseudo, entity.getPseudo());
        values.put(columnScore, entity.getScore());
    }

    @Override
    protected Score getEntity(Cursor cursor) {
        Score score = new Score();

        Integer indexPseudo = cursor.getColumnIndex(columnPseudo);
        Integer indexScore = cursor.getColumnIndex(columnScore);

        score.setPseudo(cursor.getString(indexPseudo));
        score.setScore(cursor.getInt(indexScore));

        return score;
    }

    public Score getBestScore() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select "+columnPseudo+", "+columnScore+" from "+getTableName() + " WHERE "+columnScore+" = (SELECT MAX("+columnScore+") FROM "+getTableName()+")", null);
        cursor.moveToFirst();
        Score bestScore = this.getEntity(cursor);
        cursor.close();

        return bestScore;
    }
}
