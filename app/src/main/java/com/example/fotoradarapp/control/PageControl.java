package com.example.fotoradarapp.control;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fotoradarapp.R;
import com.example.fotoradarapp.client.LocalhostClient;
import com.example.fotoradarapp.dao.PageDAO;
import com.example.fotoradarapp.model.Image;
import com.example.fotoradarapp.model.Page;
import com.example.fotoradarapp.view.PagesActivity;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PageControl {

    private PageDAO pageDAO;
    private ListView lvPages;
    private static ArrayAdapter<Page> adapterPages;
    private List<Page> pages;
    private Activity activity;
    private Image image;
    private WebView webView ;

    public PageControl(PagesActivity pagesActivity) {
        this.activity = pagesActivity;

        lvPages = activity.findViewById(R.id.lvPages);
        webView = activity.findViewById(R.id.webView);

        pages = new ArrayList<>();
        adapterPages = new ArrayAdapter<Page>(activity, android.R.layout.simple_expandable_list_item_1, pages);
        lvPages.setAdapter(adapterPages);

        image = (Image) activity.getIntent().getSerializableExtra("imagem");

        pageDAO = new PageDAO(activity);

        getPages();
        onClick();

    }

    private void onClick() {
        lvPages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse( adapterPages.getItem(i).getUrl())));
                webView.loadUrl(adapterPages.getItem(i).getUrl());
            }
        });
    }

    private void getPages() {

        LocalhostClient.get("search/matchs/image/" + image.getId(), null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Toast.makeText(activity, "um elemento", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                // Do something with the response
                Gson gson = new Gson();
                Page[] pages = gson.fromJson(timeline.toString(), Page[].class);

                for (Page page : pages) {
                    try {
                        pageDAO.getDao().createIfNotExists(page);
                        Toast.makeText(activity, "Página " + page.getPageTitle() + " salva", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(activity, "Erro ao salvar página local", Toast.LENGTH_SHORT).show();
                    }
                }

                adapterPages.addAll(Arrays.asList(pages));
            }
        });
        try {
            pages = pageDAO.getDao().queryForAll();
            adapterPages.addAll(pages);
            Toast.makeText(activity, "Páginas carregadas localmente", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }
}
