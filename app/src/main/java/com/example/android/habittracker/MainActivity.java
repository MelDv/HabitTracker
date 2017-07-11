package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {
    private HabitDbHelper helper = new HabitDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_SUBJECT,
                HabitContract.HabitEntry.COLUMN_TOPIC,
                HabitContract.HabitEntry.COLUMN_TIME};

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection, null, null, null, null, null);

        TextView displayView = (TextView) findViewById(R.id.habit);

        try {
            displayView.setText("The habit tracker helps you keep track of how much time studying something takes.\n" +
                    "\nAt the moment, there are " + cursor.getCount() + " entries in the database.\n");

            int idColumn = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int subjectColumn = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_SUBJECT);
            int topicColumn = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_TOPIC);
            int timeColumn = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_TIME);

            int totalTime = 0;

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumn);
                String currentSubject = cursor.getString(subjectColumn);
                String currentTopic = cursor.getString(topicColumn);
                int currentTime = cursor.getInt(timeColumn);
                totalTime = totalTime + currentTime;

                displayView.append("\n" + currentId + " - " + currentSubject + " - " + currentTopic + " - " + currentTime);
            }
            displayView.append("\n\nYou have studied " + totalTime + " minutes in total. Keep it up!");

        } finally {
            cursor.close();
        }
    }

    private void insertHabit() {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(HabitContract.HabitEntry.COLUMN_SUBJECT, "Algorithms");
        values.put(HabitContract.HabitEntry.COLUMN_TOPIC, "Bubble sort");
        values.put(HabitContract.HabitEntry.COLUMN_TIME, 98);
        db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_SUBJECT, "Algorithms");
        values.put(HabitContract.HabitEntry.COLUMN_TOPIC, "Recursion tree");
        values.put(HabitContract.HabitEntry.COLUMN_TIME, 34);
        db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_SUBJECT, "Web development");
        values.put(HabitContract.HabitEntry.COLUMN_TOPIC, "Bootstrap");
        values.put(HabitContract.HabitEntry.COLUMN_TIME, 214);

        db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
    }

    @Override
    protected void onStart() {
        super.onStart();
        insertHabit();
        displayDatabaseInfo();
    }
}

