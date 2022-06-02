package com.example.dsa_minim2exemple;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsa_minim2exemple.API.API;
import com.example.dsa_minim2exemple.API.Repos;
import com.example.dsa_minim2exemple.API.User;
import com.example.dsa_minim2exemple.ListAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getUser();
        getRepos();

        Button returnBtn = (Button) findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getUser() {
        SharedPreferences sharedPref = getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPref.getString("User", null);

        TextView followers = (TextView) findViewById(R.id.followers);
        TextView following = (TextView) findViewById(R.id.following);
        TextView repos = (TextView) findViewById(R.id.repositories);
        TextView usernom = (TextView) findViewById(R.id.userName);

        ImageView avatar = (ImageView) findViewById(R.id.avatar);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        API gerritAPI = retrofit.create(API.class);
        Call<User> call = gerritAPI.getInfo(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);

                    User user = response.body();
                    String seguidors = "Followers: " + user.getFollowers();
                    String repositoris = "Repositories: " + user.getRepos();
                    String f2 = "Following: " + user.getFollowing();

                    usernom.setText(username);
                    followers.setText(seguidors);
                    repos.setText(repositoris);
                    following.setText(f2);
                    Picasso.get().load(user.getAvatar()).into(avatar);
                    progressBar.setVisibility(View.INVISIBLE);

                }else{
                    Log.i("User info fetch failure", "User does not exist");
                    Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("User info failure", t.toString());
                Intent intent = new Intent (getApplicationContext(), ErrorActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getRepos() {
        SharedPreferences sharedPref = getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPref.getString("User",null);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        API gerritAPI = retrofit.create(API.class);
        Call<List<Repos>> call = gerritAPI.getRepos(username);

        call.enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                if (response.isSuccessful()) {
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);

                    List<Repos> reposList = response.body();
                    ListAdapter listAdapter = new ListAdapter(reposList, SecondActivity.this);

                    RecyclerView recyclerView = findViewById(R.id.ListRecyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this));
                    recyclerView.setAdapter(listAdapter);

                    progressBar.setVisibility(View.INVISIBLE);

                }else{
                    Intent intent = new Intent (getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                Intent intent = new Intent (getApplicationContext(), ErrorActivity.class);
                startActivity(intent);
            }
        });
    }
}
