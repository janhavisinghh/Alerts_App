package com.example.android.exampleapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import static android.provider.BaseColumns._ID;
import static com.example.android.exampleapp.DataContract.DataEntry.TABLE_NAME;
import static com.example.android.exampleapp.DataContract.DataEntry.buildTodoUriWithId;

public class dataContentProvider extends ContentProvider {
    public static final int DATA = 100;
    public static final int DATA_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBHelper dbHelper;

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.AUTHORITY;


        uriMatcher.addURI(authority, TABLE_NAME, DATA);
        uriMatcher.addURI(authority, TABLE_NAME + "/#", DATA_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DBHelper(context);
        return true;
    }

    /**
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {
            case DATA:
                long id = db.insert(TABLE_NAME, null, values);
                if (id != -1) {
                    try {
                        getContext().getContentResolver().notifyChange(uri, null);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                return buildTodoUriWithId(id);

            default:
                return null;
        }
    }

    /**
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case DATA_WITH_ID:
                String _ID = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{_ID};

                retCursor = dbHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;


            case DATA:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        try {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return retCursor;
    }

    /**
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted;
        switch (match) {
            case DATA:
                tasksDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case DATA_WITH_ID:
                String movie_id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(TABLE_NAME, _ID + "="
                        + movie_id
                        + (!TextUtils.isEmpty(selection) ? " AND ("
                        + selection + ')' : ""), new String[]{movie_id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            try {
                getContext().getContentResolver().notifyChange(uri, null);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        return tasksDeleted;
    }

    /**
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @param uri
     * @return
     */
    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
