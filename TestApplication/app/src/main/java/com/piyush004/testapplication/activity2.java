package com.piyush004.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class activity2 extends AppCompatActivity {

    private WebView webView;
    private Button button;
    private String getCurrentUrl,originalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        button = findViewById(R.id.btnWebAmazon);
}
    public void onWebViewAmazon(View view)
    {
        String url = "https://www.amazon.in/";
        Intent intent2 = new Intent(activity2.this , WebPageActivity.class);
        intent2.putExtra("Url", url);
        startActivity(intent2);
        finish();

    }

    public void onWebViewFlipcart(View view)
    {
        String url = "https://www.flipkart.com/";
        Intent intent2 = new Intent(activity2.this , WebPageActivity.class);
        intent2.putExtra("Url", url);
        startActivity(intent2);
        finish();
    }

    public void onWebViewEbay(View view)
    {
        String url = "https://in.ebay.com/";
        Intent intent2 = new Intent(activity2.this , WebPageActivity.class);
        intent2.putExtra("Url", url);
        startActivity(intent2);
        finish();
    }

    public void onWebViewSnapdeal(View view)
    {
        String url = "https://www.snapdeal.com/";
        Intent intent2 = new Intent(activity2.this , WebPageActivity.class);
        intent2.putExtra("Url", url);
        startActivity(intent2);
        finish();

    }

    public void onWebViewMantra(View view)
    {
        String url = "https://www.myntra.com/";
        Intent intent2 = new Intent(activity2.this , WebPageActivity.class);
        intent2.putExtra("Url", url);
        startActivity(intent2);
        finish();
    }
}