package com.example.thedailywave;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator;

    // category buttons
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    // search bar
    SearchView searchView;

    // Map button text → API category
    Map<String, String> categoryMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);
        searchView = findViewById(R.id.search_view);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);

        // set listeners
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

        // setup category mapping
        categoryMap.put("GENERAL", "general");
        categoryMap.put("BUSINESS", "business");
        categoryMap.put("SPORTS", "sports");
        categoryMap.put("TECHNOLOGY", "technology");
        categoryMap.put("HEALTH", "health");
        categoryMap.put("ENTERTAINMENT", "entertainment");
        categoryMap.put("SCIENCE", "science");

        // search feature
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("general", query); // default category for search
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setupRecyclerView();
        getNews("general", null); // default call
    }

    void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show) {
        if (show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    void getNews(String category, String query) {
        changeInProgress(true);

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://10.0.2.2:5000/news").newBuilder();
        urlBuilder.addQueryParameter("category", category.toLowerCase());
        if (query != null && !query.isEmpty()) {
            urlBuilder.addQueryParameter("q", query);
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    changeInProgress(false);
                    Log.e("NEWS_API", "Request failed", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();

                JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
                Type listType = new TypeToken<List<Article>>() {}.getType();
                List<Article> articles = gson.fromJson(jsonObject.get("articles"), listType);

                runOnUiThread(() -> {
                    changeInProgress(false);
                    articleList.clear();
                    articleList.addAll(articles);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String btnText = btn.getText().toString().trim().toUpperCase();

        String category = categoryMap.getOrDefault(btnText, "general");
        getNews(category, null);
    }
}
