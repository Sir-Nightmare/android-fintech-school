package ru.tinkoff.school.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.tinkoff.school.model.News;
import ru.tinkoff.school.model.NewsResponse;

public final class Api {

    private static final String BASE_URL = "https://api.tinkoff.ru/v1";

    private OkHttpClient client = new OkHttpClient();
    private Gson gson;

    public Api() {
        client = new OkHttpClient.Builder().build();
        gson = new GsonBuilder().create();
    }

    public List<News> getNews() throws ApiException {
        Request request = getNewsRequest();

        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            ResponseBody body = response.body();
            String bodyString = body.string();
            Type typeOfT = new TypeToken<NewsResponse>() {}.getType();
            NewsResponse newsResponse = gson.fromJson(bodyString, typeOfT);
            return newsResponse.getPayload();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    private Request getNewsRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + "/news");
        builder.get();
        return builder.build();
    }
}
