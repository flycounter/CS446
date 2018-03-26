package cs446_w2018_group3.supercardgame.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by yandong on 2018-03-25.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyUser.db";
    public static final int DATABASE_VERSION = 1;
    public static final String USER_TABLE_NAME = "deck";
    public static final String DECK_COLUMN_ID = "id";
    public static final String DECK_COLUMN_GOLD = "gold";
    public static final String DECK_COLUMN_WATER = "water";
    public static final String DECK_COLUMN_FIRE = "fire";
    public static final String DECK_COLUMN_AIR = "air";
    public static final String DECK_COLUMN_DIRT = "dirt";
    public static final String DECK_COLUMN_MAX_WATER = "max_water";
    public static final String DECK_COLUMN_MAX_FIRE = "max_fire";
    public static final String DECK_COLUMN_MAX_AIR = "max_air";
    public static final String DECK_COLUMN_MAX_DIRT = "max_dirt";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table deck " +
                "(id integer primary key, gold integer, water integer, fire integer, air integer, dirt integer, maxWater integer, maxFire integer, maxAir integer, maxEarth, integer)"
        );
        init();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void init() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 1);
        contentValues.put("gold", 50);
        contentValues.put("water", 5);
        contentValues.put("fire", 5);
        contentValues.put("air", 5);
        contentValues.put("dirt", 5);
        contentValues.put("max_water", 5);
        contentValues.put("max_fire", 5);
        contentValues.put("max_air", 5);
        contentValues.put("max_dirt", 5);
        db.insert("deck", null, contentValues);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from deck where id="+id+"", null);
        return res;
    }

    public boolean updateDeck(Integer id, int water, int fire, int air, int dirt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("water", water);
        contentValues.put("fire", fire);
        contentValues.put("air", air);
        contentValues.put("dirt", dirt);
        db.update("deck", contentValues, "id = ?", new String[]{ (Integer.toString(id)) });
        return true;
    }

    public boolean updateMaxCard(Integer id, String cardType, int gold, int num) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gold", gold);
        contentValues.put(cardType, num);
        db.update("deck", contentValues, "id = ?", new String[]{ (Integer.toString(id)) });
        return true;
    }

}
