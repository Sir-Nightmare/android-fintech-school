package ru.tinkoff.school.model;

import java.util.List;

public class NewsResponse {

    private String resultCode;

    private List<News> payload;

    public List<News> getPayload() {
        return payload;
    }

    public void setPayload(List<News> payload) {
        this.payload = payload;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
