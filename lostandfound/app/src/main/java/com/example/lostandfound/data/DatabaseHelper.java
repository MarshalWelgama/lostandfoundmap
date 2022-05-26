package com.example.lostandfound.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lostandfound.NewAdvert;
import com.example.lostandfound.model.Item;
import com.example.lostandfound.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Util.TYPE + " TEXT, " + Util.ITEM_NAME + " TEXT, " + Util.PHONE + " TEXT, " + Util.DESCRIPTION + " TEXT, " + Util.DATE + " TEXT, " + Util.LOCATION + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_NOTE_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);
    }

    public long insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TYPE, item.getType());
        contentValues.put(Util.ITEM_NAME, item.getItem_name());
        contentValues.put(Util.PHONE, item.getPhone());
        contentValues.put(Util.DESCRIPTION, item.getDescription());
        contentValues.put(Util.DATE, item.getDate());
        contentValues.put(Util.LOCATION, item.getLocation());


        long newRowID = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();

        return newRowID;
    }

    public List<Item> fetchAll()
    {
        ArrayList<Item> list = new ArrayList<Item>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Util.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Item obj = new Item();
                        //only one column
                        obj.setItem_id(cursor.getInt(0));
                        obj.setType(cursor.getString(1));
                        obj.setItem_name(cursor.getString(2));
                        obj.setPhone(cursor.getString(3));
                        obj.setDescription(cursor.getString(4));
                        obj.setDate(cursor.getString(5));
                        obj.setLocation(cursor.getString(6));
                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

    public void removeItem(int itemId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "DELETE FROM " + Util.TABLE_NAME + " WHERE " + Util.ITEM_ID + " = " + itemId;
        db.execSQL(QUERY);
    }


}
