package com.example.android.exampleapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {
    public static final String AUTHORITY = "com.example.android.exampleapp";
    public static final String PATH_DATA = "DataObjs";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final class DataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATA).build();
        public static final String TABLE_NAME = "DataObjs";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_TIME_STAMP = "time_stamp";
        public static final String _ID = "id";

        /**
         * @param id
         * @return
         */
        public static Uri buildTodoUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }

    }
}
