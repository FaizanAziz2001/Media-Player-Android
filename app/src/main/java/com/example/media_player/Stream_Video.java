package com.example.media_player;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Stream_Video extends Fragment {

    Button web_stream,app_button;
    WebView webView;
    String[] links_list={"youtube.com","twitch.tv","open.spotify.com","netflix.com"};
    Spinner dropdown;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stream__video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        web_stream=view.findViewById(R.id.streamweb_button);
        app_button=view.findViewById(R.id.stream_button);
        webView=view.findViewById(R.id.Stream_Webview);
        dropdown = view.findViewById(R.id.link_Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, links_list);
        dropdown.setAdapter(adapter);

        web_stream.setOnClickListener(view12 -> {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://"+dropdown.getSelectedItem().toString()));
            getActivity().startActivity(intent);
        });

        app_button.setOnClickListener(view1 -> {
            Stream();
        });

    }

    private void Stream()
    {
        webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("https://"+dropdown.getSelectedItem().toString());
    }
}