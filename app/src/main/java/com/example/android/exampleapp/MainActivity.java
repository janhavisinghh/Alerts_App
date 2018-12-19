package com.example.android.exampleapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.exampleapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_CATEGORY;
import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_TIME_STAMP;
import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_TITLE;
import static com.example.android.exampleapp.DataContract.DataEntry.CONTENT_URI;
import static com.example.android.exampleapp.DataContract.DataEntry._ID;
import static com.example.android.exampleapp.DataContract.DataEntry.buildTodoUriWithId;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CategoryAdapter adapter1;
    private CategoryAdapter adapter2;
    private CategoryAdapter adapter3;

    private ArrayList<DataObjs> dataList;
    private Data data;
    private Boolean onClickCard1 = false;
    private Boolean onClickCard2 = false;
    private Boolean onClickCard3 = false;

    private int cursorCount1 = 10;
    private int cursorCount2 = 10;
    private int cursorCount3 = 10;




    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dataList = new ArrayList<>();
        data = new Data();

        retrofitcall("Category 1");
        retrofitcall("Category 2");
        retrofitcall("Category 3");

        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView1.setHasFixedSize(true);
        binding.recyclerView2.setHasFixedSize(true);
        binding.recyclerView3.setHasFixedSize(true);

        binding.recyclerView1.setNestedScrollingEnabled(false);
        binding.recyclerView2.setNestedScrollingEnabled(false);
        binding.recyclerView3.setNestedScrollingEnabled(false);

        binding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCard1) {
                    onClickCard1 = false;
                    binding.recyclerView1.setVisibility(View.GONE);
                    Cursor cursor = getAllData("Category 1");
                    binding.noOfNotifications1.setText(cursor.getCount() + "");
                } else {
                    onClickCard1 = true;
                    binding.recyclerView1.setVisibility(View.VISIBLE);
                    retrofitcall("Category 1");
                }
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCard2) {
                    onClickCard2 = false;
                    binding.recyclerView2.setVisibility(View.GONE);
                    Cursor cursor = getAllData("Category 2");
                    binding.noOfNotifications2.setText(cursor.getCount() + "");
                } else {
                    onClickCard2 = true;
                    binding.recyclerView2.setVisibility(View.VISIBLE);
                    retrofitcall("Category 2");

                }
            }
        });

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCard3) {
                    onClickCard3 = false;
                    binding.recyclerView3.setVisibility(View.GONE);
                    Cursor cursor = getAllData("Category 3");
                    binding.noOfNotifications3.setText(cursor.getCount() + "");
                } else {
                    onClickCard3 = true;
                    binding.recyclerView3.setVisibility(View.VISIBLE);
                    retrofitcall("Category 3");

                }
            }
        });

        binding.cancelButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll("Category 1");
                Cursor cursor = getAllData("Category 1");
                adapter1 = new CategoryAdapter(getApplicationContext(), cursor);
                binding.noOfNotifications1.setText(cursor.getCount() + "");


            }
        });
        binding.cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll("Category 2");
                Cursor cursor = getAllData("Category 2");
                adapter2 = new CategoryAdapter(getApplicationContext(), cursor);
                binding.noOfNotifications2.setText(cursor.getCount() + "");

            }
        });
        binding.cancelButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll("Category 3");
                Cursor cursor = getAllData("Category 3");
                adapter3.swapCursor(getAllData("Category 3"));
                binding.noOfNotifications3.setText(cursor.getCount() + "");


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.refresh_button:
                Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                removeAll("Category 1");
                removeAll("Category 2");
                removeAll("Category 3");
                retrofitcall("Category 1");
                retrofitcall("Category 2");
                retrofitcall("Category 3");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewData(String title, String category, String time_stamp) {
        final ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_TIME_STAMP, time_stamp);
        getContentResolver().insert(CONTENT_URI, cv);
    }

    public Cursor getAllData(String category) {
        String selection = COLUMN_CATEGORY + " =?";
        String[] selectionArgs = {category};
        return getContentResolver().query(CONTENT_URI,
                null,
                selection,
                selectionArgs,
                _ID);
    }
    public boolean searchDataInDB(String category) {
        String[] projection = {
                COLUMN_TITLE,
                COLUMN_CATEGORY,
                COLUMN_TIME_STAMP,
        };
        String selection = COLUMN_CATEGORY + " =?";
        String[] selectionArgs = {category};
        String limit = "1";
        long id = 2;

        Cursor cursor = getContentResolver().query(buildTodoUriWithId(id),
                projection,
                selection,
                selectionArgs,
                limit);
        boolean data_present = (cursor.getCount() > 0);
        cursor.close();
        return data_present;
    }
    public void removeAll(String category) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        getContentResolver().delete(CONTENT_URI, COLUMN_CATEGORY +"=?", new String[]{category});
    }

    public void retrofitcall(final String category){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Data> call;
        if(category.equals("Category 1")){
            call = service.getAllDataOne();
        }
        else if(category.equals("Category 2")){
            call = service.getAllDataTwo();
        }
        else {
            call = service.getAllDataThree();
        }

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                mDb = dbHelper.getWritableDatabase();
                data = response.body();
                dataList = data.getData();
                if(!searchDataInDB(category)) {
                    for (int i = 0; i < dataList.size(); i++) {
                        addNewData(dataList.get(i).getTitle(), dataList.get(i).getCategory(), dataList.get(i).getTimeStamp());
                    }
                }
                Cursor cursor = getAllData(category);
                if(category.equals("Category 1")){
                    adapter1 = new CategoryAdapter(getApplicationContext(), cursor);
                    binding.recyclerView1.setAdapter(adapter1);
                    adapter1.swapCursor(getAllData(category));
                    binding.noOfNotifications1.setText(cursor.getCount() + "");
                }
                else if(category.equals("Category 2")){
                    adapter2 = new CategoryAdapter(getApplicationContext(), cursor);
                    binding.recyclerView2.setAdapter(adapter2);
                    adapter2.swapCursor(getAllData(category));
                    cursorCount2 = cursor.getCount();
                    binding.noOfNotifications2.setText(cursor.getCount() + "");
                }
                else{
                    adapter3 = new CategoryAdapter(getApplicationContext(), cursor);
                    binding.recyclerView3.setAdapter(adapter3);
                    adapter3.swapCursor(getAllData(category));
                    cursorCount3 = cursor.getCount();
                    binding.noOfNotifications3.setText(cursor.getCount() + "");
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                t.getCause();
            }
        });
    }

}
