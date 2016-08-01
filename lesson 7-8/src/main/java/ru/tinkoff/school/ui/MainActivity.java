package ru.tinkoff.school.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.tinkoff.school.R;
import ru.tinkoff.school.model.News;
import ru.tinkoff.school.network.Api;
import ru.tinkoff.school.network.LoadNewsAsyncTask;

// TODO добавить экран просмотра новости, при клике по элементу списка
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = new Api();
        initViews();
        initErrorView();
        showProgressView();
        getNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_refresh_item) {
            showProgressView();
            getNews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initErrorView() {
        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressView();
                getNews();
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.addItemDecoration(new ItemDividerDecoration(this));
        progressBar = (ProgressBar) findViewById(R.id.main_progress_bar);
        errorTextView = (TextView) findViewById(R.id.main_error_text_view);
    }

    private void showProgressView() {
        recyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void showError() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private void onNewsLoaded(List<News> news) {
        NewsAdapter newsAdapter = new NewsAdapter(this, news);
        recyclerView.setAdapter(newsAdapter);
        showContent();
    }

    private void onErrorWhileLoadingNews(Throwable cause) {
        showError();
    }

    private void getNews() {
        LoadNewsAsyncTask task = new LoadNewsAsyncTask(api, new MainActivityCallback(this));
        task.execute();
    }

    private static class MainActivityCallback implements LoadNewsAsyncTask.Callback {

        WeakReference<MainActivity> activityWeakReference;

        public MainActivityCallback(MainActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onOk(List<News> data) {
            MainActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }

            activity.onNewsLoaded(data);
        }

        @Override
        public void onError(Throwable error) {
            MainActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }

            activity.onErrorWhileLoadingNews(error);
        }
    }

}
