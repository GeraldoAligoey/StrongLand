package me.eljae.strongland;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

public class News extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#landslide")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                .setTimeline(searchTimeline).build();
        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
