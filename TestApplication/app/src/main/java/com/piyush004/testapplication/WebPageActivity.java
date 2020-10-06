package com.piyush004.testapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WebPageActivity extends AppCompatActivity {

    private WebView webView;
    private String URL;
    private String getCurrentUrl, originalUrl;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.webView);
        button = findViewById(R.id.compareBtn);
        Intent intent = getIntent();
        URL = intent.getStringExtra("Url");
        System.out.println(URL);
        switch (URL) {
            case "https://www.amazon.in/": {
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        originalUrl = url;
                        Log.d("Webview", "your current url is " + url);
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }
                });
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(URL);
                break;
            }

            case "https://www.flipkart.com/": {
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        originalUrl = url;
                        Log.d("Webview", "your current url is " + url);
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }
                });
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(URL);
                break;
            }
            case "https://www.myntra.com/": {
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        originalUrl = url;
                        Log.d("Webview", "your current url is " + url);
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }
                });
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(URL);
                break;
            }
            case "https://in.ebay.com/": {
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        originalUrl = url;
                        Log.d("Webview", "your current url is " + url);
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }
                });
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(URL);
                break;
            }
            case "https://www.snapdeal.com/": {
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        originalUrl = url;
                        Log.d("Webview", "your current url is " + url);
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }
                });
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(URL);
                break;
            }
            default:
                break;
        }
    }
    public void OnCompare(View view)
    {
        Intent intent3 = new Intent(WebPageActivity.this , activity2.class);
        startActivity(intent3);
        finish();
    }
}