package ru.tinkoff.school.network;

import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import ru.tinkoff.school.model.News;

// TODO переписать загрузку данных с использованием Thread и Handler вместо AsyncTask
public class LoadNewsAsyncTask extends AsyncTask<Void, Void, List<News>> {

    public interface Callback {
        void onOk(List<News> data);

        void onError(Throwable error);
    }

    private Api api;
    private Callback callback;
    private Exception exception;

    public LoadNewsAsyncTask(Api api, Callback callback) {
        this.api = api;
        this.callback = callback;
    }

    // TODO добавить кеширование данных в БД.
    // При открытии экрана сначала должна показываться копия данных из БД.
    // Параллельно с этим выполняется запрос на сервер за обновленными данными, при получении ответа
    // копия из БД удаляется и подменяется обновленной версией с сервера
    @Override
    protected List<News> doInBackground(Void... voids) {
        try {
            return api.getNews();
        } catch (ApiException e) {
            exception = e;
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<News> data) {
        if (exception == null) {
            callback.onOk(data);
        } else {
            callback.onError(exception);
        }
    }
}
