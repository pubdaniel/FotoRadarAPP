package com.example.fotoradarapp.service;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FotoRadarService {

        private static String HOST = "localhost:8080/FotoRadarAPI/api";

    public  FotoRadarService() {}

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public String getRequest() throws IOException {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:8080/FotoRadarAPI/api/search/images")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String post(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();


        String body = String.format("url=%s", url);

        RequestBody bodyRequest = RequestBody.create(body.getBytes());
        Request request = new Request.Builder()
                .url(url)
                .post(bodyRequest)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
