package com.death.paper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.death.paper.R;
import com.death.paper.model.Articles;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rajora_sd on 21-09-2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Articles> articles;
    private Context mContext;

    public ArticleAdapter(Context context, List<Articles> articles) {
        mContext = context;
        this.articles = articles;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        final Articles article = articles.get(position);
        holder.adText.setText(article.getTitle());
        Picasso.with(mContext)
                .load(article.getUrlToImage())
                .into(holder.posterImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        BitmapDrawable drawable = (BitmapDrawable) holder.posterImage.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                int color = palette.getDominantColor(ContextCompat.getColor(mContext, android.R.color.black));
                                holder.adText.setBackgroundColor(color);
                                holder.adText.setTextColor(getComplimentColor(color));
                            }
                        });
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public Articles getItem(int position) {
        return articles.get(position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView adText;
        ImageView posterImage;

        MyViewHolder(View itemView) {
            super(itemView);
            adText = itemView.findViewById(R.id.newsHeadline);
            posterImage = itemView.findViewById(R.id.poster);
        }
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

}
