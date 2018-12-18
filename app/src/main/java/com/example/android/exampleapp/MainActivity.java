package com.example.android.exampleapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.android.exampleapp.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CategoryAdapter adapter;
    private ArrayList<DataObjs> dataList;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dataList = new ArrayList<>();
        data = new Data();

        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView1.setHasFixedSize(true);
        binding.recyclerView2.setHasFixedSize(true);
        binding.recyclerView3.setHasFixedSize(true);

        binding.recyclerView1.setNestedScrollingEnabled(false);
        binding.recyclerView2.setNestedScrollingEnabled(false);
        binding.recyclerView3.setNestedScrollingEnabled(false);

        adapter = new CategoryAdapter();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<Data> callOne = service.getAllDataOne();

        callOne.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                data = response.body();
                dataList = data.getData();
                Toast.makeText(getApplicationContext(), dataList.get(2).getTitle(), Toast.LENGTH_SHORT).show();
                binding.noOfNotifications1.setText(dataList.size()+"");
                adapter.setData(dataList);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                t.getCause();
            }
        });

        Call<Data> callTwo = service.getAllDataTwo();

        callTwo.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                data = response.body();
                dataList = data.getData();
                Toast.makeText(getApplicationContext(), dataList.get(2).getTitle(), Toast.LENGTH_SHORT).show();
                binding.noOfNotifications2.setText(dataList.size()+"");
                adapter.setData(dataList);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                t.getCause();
            }
        });
        Call<Data> callThree = service.getAllDataThree();


        callThree.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                data = response.body();
                dataList = data.getData();
                Toast.makeText(getApplicationContext(), dataList.get(2).getTitle(), Toast.LENGTH_SHORT).show();
                binding.noOfNotifications3.setText(dataList.size()+"");
                adapter.setData(dataList);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                t.getCause();
            }
        });

    }



}
