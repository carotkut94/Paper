package com.death.paper.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;

import com.death.paper.R;
import com.death.paper.model.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajora_sd on 21-09-2017.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.MyViewHolder> implements Filterable {

    private List<Source> sources;
    private List<Source> sourcesFiltered;
    private Context mContext;

    public SourceAdapter(Context context, List<Source> models) {
        mContext = context;
        this.sources = models;
        this.sourcesFiltered = models;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_news_paper, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        final Source source = sources.get(position);
        holder.adText.setText(source.getName());
    }
    public Source getItem(int position) {
        return sources.get(position);
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    @Override
    public Filter getFilter() {
        return  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                Log.e("QUERY ADAPTER", charString);
                if (charString.isEmpty()) {
                    sources = sourcesFiltered ;
                } else {
                    List<Source> filteredList = new ArrayList<>();
                    for (Source roster : sourcesFiltered) {
                        Log.e("NAME", roster.getName());
                        if (roster.getName().toLowerCase().contains(charString.toLowerCase()) || roster.getUrl().contains(charString)) {
                            filteredList.add(roster);
                            Log.e("MATCH FOUND", ""+true);
                        }

                        Log.e("MATCH FOUND", ""+false);
                    }
                    sources = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sources;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sources = (ArrayList<Source>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        Button adText;
        MyViewHolder(View itemView) {
            super(itemView);
            adText = (Button) itemView.findViewById(R.id.newspapername);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SourceAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SourceAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
