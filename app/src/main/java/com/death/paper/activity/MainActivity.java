package com.death.paper.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.death.paper.R;
import com.death.paper.adapter.ArticleAdapter;
import com.death.paper.adapter.SourceAdapter;
import com.death.paper.model.Articles;
import com.death.paper.model.News;
import com.death.paper.model.Source;
import com.death.paper.model.Sources;
import com.death.paper.networking.ApiClient;
import com.death.paper.networking.NewsApiClientInterface;
import com.death.paper.util.StItemDecoration;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Dialog dialog;
    private final static String API_KEY = "44283b117d784997a4edd8875c900f5d";
    private RecyclerView sourceList, newsArticle;
    SourceAdapter adapter;
    LottieAnimationView lottieAnimationView;
    StaggeredGridLayoutManager gridLayoutManager;
    ArticleAdapter articleAdapter;
    NewsApiClientInterface apiService;
    MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = ApiClient.getClient().create(NewsApiClientInterface.class);
        sourceList = findViewById(R.id.sourcesList);
        newsArticle = findViewById(R.id.newsArticle);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.search_view);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("News");
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_card);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        toolbar.inflateMenu(R.menu.view_switcher);
//
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                toggle(item);
//
//                return true;
//            }
//        });

        setSupportActionBar(toolbar);


        gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        newsArticle.addItemDecoration(new StItemDecoration(20, false));
        getNewsFromSource("abc-news-au");
        Call<Sources> call = apiService.getSources();
        call.enqueue(new Callback<Sources>() {
            @Override
            public void onResponse(@NonNull Call<Sources> call, @NonNull Response<Sources> response) {
                if (null != response.body().getSources() && response.body().getSources().size() > 0) {
                    List<Source> sources = response.body().getSources();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    adapter = new SourceAdapter(MainActivity.this, sources);
                    sourceList.setLayoutManager(linearLayoutManager);
                    sourceList.addItemDecoration(new StItemDecoration(10, true));
                    sourceList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Sources> call, @NonNull Throwable t) {
            }

        });

        sourceList.addOnItemTouchListener(new SourceAdapter.RecyclerTouchListener(this, sourceList, new SourceAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
               getNewsFromSource(adapter.getItem(position).getId());
                toolbar.setTitle(adapter.getItem(position).getName());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        newsArticle.addOnItemTouchListener(new ArticleAdapter.RecyclerTouchListener(this, newsArticle, new ArticleAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, NewsArticle.class);
                intent.putExtra("data", articleAdapter.getItem(position));
                Pair photoSharedElement = Pair.create(newsArticle.getChildAt(position).findViewById(R.id.poster), ViewCompat.getTransitionName(newsArticle.getChildAt(position).findViewById(R.id.poster)));
                Pair titleSharedElement = Pair.create(newsArticle.getChildAt(position).findViewById(R.id.newsHeadline), ViewCompat.getTransitionName(newsArticle.getChildAt(position).findViewById(R.id.newsHeadline)));

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, photoSharedElement, titleSharedElement);

                startActivity(intent, options.toBundle());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void getNewsFromSource(String source) {
        dialog.show();
        Call<News> newsCall = apiService.getNewsArticlesFromSource(source, API_KEY);
        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (null != response.body().getArticles() && response.body().getArticles().size() > 0) {
                    List<Articles> news = response.body().getArticles();
                    gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
                    articleAdapter = new ArticleAdapter(MainActivity.this, news);
                    newsArticle.setLayoutManager(gridLayoutManager);
                    newsArticle.setAdapter(articleAdapter);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                TextView errorText = dialog.findViewById(R.id.message);
                lottieAnimationView = dialog.findViewById(R.id.animation_view);
                lottieAnimationView.cancelAnimation();
                errorText.setText("There was an error!");
                dialog.setCancelable(true);
            }
        });
    }

    private void toggle(MenuItem item) {
        if (item.getItemId() == R.id.listView) {
            if (gridLayoutManager.getSpanCount() == 1) {
                gridLayoutManager.setSpanCount(2);
                item.setIcon(R.drawable.ic_view_list_black_24dp);
            } else {
                gridLayoutManager.setSpanCount(1);
                item.setIcon(R.drawable.ic_grid_on_black_24dp);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.listView:
                toggle(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_switcher, menu);

        MenuItem item = menu.findItem(R.id.navigation_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(searchView.isSearchOpen()){
            searchView.closeSearch();
        }else {
            super.onBackPressed();
        }
    }
}
