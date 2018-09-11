package com.example.apoorva.newspart1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public NewsAdapter mNewsAdapter;
    public ListView mNewsListView;
    private TextView mStatusTextView;
    private ProgressBar mLoadingProgressBar;

    private static final String REQUEST_URL_STRING = "https://content.guardianapis.com/search?api-key=575f6778-9de7-4332-96d5-a96a07d9a9ab&show-tags=contributor";

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(NewsActivity.this,REQUEST_URL_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        mStatusTextView.setText(R.string.no_news);
        mNewsAdapter.clear();

        mLoadingProgressBar.setVisibility(View.GONE);

        if (data!=null && !data.isEmpty()) {
            mNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

        mLoadingProgressBar = (ProgressBar)findViewById(R.id.loading_progress_bar);

        mNewsAdapter = new NewsAdapter(this,new ArrayList<News>());
        mNewsListView = (ListView)findViewById(R.id.news_list);
        mNewsListView.setAdapter(mNewsAdapter);

        mStatusTextView = (TextView)findViewById(R.id.status_textview);
        mNewsListView.setEmptyView(mStatusTextView);

        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = mNewsAdapter.getItem(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(currentNews.getWebUrl()));
                startActivity(browserIntent);
            }
        });

        if (!isConnected) {
            mLoadingProgressBar.setVisibility(View.GONE);
            mStatusTextView.setText(R.string.no_internet_connection);
        } else {
            getLoaderManager().initLoader(0, null, this);
        }
    }
}
