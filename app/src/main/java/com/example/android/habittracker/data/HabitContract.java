package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    public static abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "studies";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_TOPIC= "topic";
        public static final String COLUMN_TIME = "time";
    }
}
