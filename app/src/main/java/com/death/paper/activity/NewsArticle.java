package com.death.paper.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.death.paper.R;
import com.death.paper.model.Articles;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

public class NewsArticle extends AppCompatActivity {

    ImageView posterImage;
    TextView newsHeadline;
    WebView newsContent;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);
        posterImage = findViewById(R.id.poster);
        newsHeadline = findViewById(R.id.headline);
        newsContent = findViewById(R.id.news);
        progressBar = findViewById(R.id.progress);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        Articles articles = (Articles) getIntent().getSerializableExtra("data");

        Picasso.get().load(articles.getUrlToImage()).into(posterImage, new Callback() {
            @Override
            public void onSuccess() {
                BitmapDrawable drawable = (BitmapDrawable) posterImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int color = palette.getDominantColor(ContextCompat.getColor(NewsArticle.this, android.R.color.black));
                        newsHeadline.setBackgroundColor(color);
                        newsHeadline.setTextColor(getComplimentColor(color));

                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }

        });
        newsHeadline.setText(articles.getTitle());

        newsContent.loadUrl(articles.getUrl());

        newsContent.setWebViewClient(new WebViewController());
        newsContent.getSettings().setJavaScriptEnabled(true);

    }

    public int getComplimentColor(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.argb(alpha, red, green, blue);
    }

    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
