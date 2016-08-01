package ru.tinkoff.school.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.tinkoff.school.R;
import ru.tinkoff.school.model.News;

public class NewsAdapter extends RecyclerView.Adapter {

    private List<News> news;
    private LayoutInflater layoutInflater;

    public NewsAdapter(Context context, List<News> news) {
        this.news = news;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_main, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder h = (NewsViewHolder) holder;
        News data = news.get(position);
        Spanned text = Html.fromHtml(data.getText());
        h.label.setText(text);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView label;

        NewsViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.main_news_label);
        }

    }
}
