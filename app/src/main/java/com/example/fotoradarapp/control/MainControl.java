package com.example.fotoradarapp.control;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fotoradarapp.R;
import com.example.fotoradarapp.client.LocalhostClient;
import com.example.fotoradarapp.dao.ImageDAO;
import com.example.fotoradarapp.dao.PageDAO;
import com.example.fotoradarapp.model.Image;
import com.example.fotoradarapp.model.Page;
import com.example.fotoradarapp.view.MainActivity;
import com.example.fotoradarapp.view.PagesActivity;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainControl {

    private EditText etUrl;
    private Gson gson = new Gson();
    private static Activity activity;
    private ListView lvListImages;
    private static ArrayAdapter adapterLvImages;
    private static List<Image> images;
    private ImageDAO imageDAO;
    private PageDAO pageDAO;


    public MainControl(MainActivity mainActivity) {
        this.activity = mainActivity;
        etUrl = activity.findViewById(R.id.etUrl);
        lvListImages = activity.findViewById(R.id.lvListImages);
        images = new ArrayList<Image>();
        adapterLvImages = new ArrayAdapter<String>(activity, android.R.layout.simple_expandable_list_item_1);
        adapterLvImages.addAll(images);
        lvListImages.setAdapter(adapterLvImages);
        imageDAO = new ImageDAO(activity);
        pageDAO =  new PageDAO(activity);

        getImages();

        addImagemOnListView(images);

        onClick();
    }

    private void onClick() {
        lvListImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Image image = (Image) adapterView.getAdapter().getItem(i);
                Toast.makeText(activity, image.toString(), Toast.LENGTH_SHORT).show();

                Intent it = new Intent(activity, PagesActivity.class);
                it.putExtra("imagem", image );
                activity.startActivity(it);

            }
        });

    }

    private void getImages() {

        LocalhostClient.get("/search/images", null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    Gson gson = new Gson();
                    images = Arrays.asList(gson.fromJson(timeline.toString(), Image[].class));

                    for (Image image : images) {
                        try {
                            imageDAO.getDao().createOrUpdate(image);

                        } catch (Exception e) {
                            Toast.makeText(activity, "IMagens carregadas do servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        });

        try {
            images = imageDAO.getDao().queryForAll();
            Toast.makeText(activity, "Imagens carregadas localmente", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static void addImagemOnListView(List<Image> images) {
        adapterLvImages.addAll(images);
    }


    public void getImageByUrl(String url) {

        LocalhostClient.get("/search/images", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                Toast.makeText(activity, timeline.toString(), Toast.LENGTH_SHORT).show();
                // Do something with the response

            }
        });
    }


    public void enviarLink() {
        String text = etUrl.getText().toString();

        RequestParams params = new RequestParams();
        params.put("url", text);


        LocalhostClient.post("/search", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                Toast.makeText(activity, timeline.toString(), Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                List<Page> pages = new ArrayList<>();
                pages = Arrays.asList(gson.fromJson(timeline.toString(), Page[].class));

                for (Page page : pages) {
                    page.setDate(new Date());
                    try {
                        pageDAO.getDao().createIfNotExists(page);
                    } catch (Exception e){
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                images.add(pages.get(0).getImage());
                Toast.makeText(activity, "IMagem inserida " + pages.get(0).getImage().getId(), Toast.LENGTH_SHORT).show();
                adapterLvImages.clear();
                try {
                    adapterLvImages.addAll(imageDAO.getDao().queryForAll());
                } catch (SQLException e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        Toast.makeText(activity, "erro", Toast.LENGTH_SHORT).show();

    }
}

