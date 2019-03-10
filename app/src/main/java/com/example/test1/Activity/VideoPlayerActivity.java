package com.example.test1.Activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.Adapter.HomepageAdapter;
import com.example.test1.Database.ChatDBHelper;
import com.example.test1.Database.ChatDBUtility;
import com.example.test1.Model.DataResponse;
import com.example.test1.R;
import com.example.test1.Utility.Utility;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {

    Button play;
    TextView title_tv,description;


    MediaSource mediaSource;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    ChatDBHelper chatDBHelper;
    ChatDBUtility chatDBUtility;

    RecyclerView recyclerView;
    HomepageAdapter defectsAdapter;

    ArrayList<DataResponse> dataResponse;
    private RecyclerView.LayoutManager layoutManager;

    ProgressDialog progressDialog;



    String url,id;
    int currentWindow;
    long playbackPosition;
    int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        url=getIntent().getStringExtra("url");
        id=getIntent().getStringExtra("id");

        chatDBUtility = new ChatDBUtility();
        chatDBHelper = chatDBUtility.CreateChatDB(VideoPlayerActivity.this);

        Utility.ChangeStatusBarColor(VideoPlayerActivity.this);
        Initializer();

        GetValuesFromSharedPreferences();
        setData();
        InitializeListener();
    }

    private void setData() {
        try {

           /* if(exoPlayer!=null)
            {
                exoPlayer.release();
            }
*/
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoURI = Uri.parse(url);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);



        }catch (Exception e){
            e.printStackTrace();
            Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
        }


        setAdapter();



    }



    private void GetValuesFromSharedPreferences() {


    }

    private void InitializeListener() {

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // play.setText("Pause");

                ArrayList<DataResponse> checkPosition=new ArrayList<>();
                checkPosition=chatDBUtility.GetDataList(chatDBHelper);
                long play_pos=0;
                int current_window=0;
                for (int i=0;i<checkPosition.size();i++)
                {
                    if(checkPosition.get(i).getId().equals(id))
                    {
                        play_pos=checkPosition.get(i).getPosition();
                        current_window=checkPosition.get(i).getCurrent_window();
                    }
                }

                if(play_pos!=0)
                {
                    exoPlayer.setPlayWhenReady(true);
                    exoPlayerView.setPlayer(exoPlayer);
                    exoPlayer.prepare(mediaSource);
                    exoPlayer.seekTo(current_window, play_pos);
                }  else
                {
                    try {
                        exoPlayer.setPlayWhenReady(true);
                        exoPlayerView.setPlayer(exoPlayer);
                        exoPlayer.prepare(mediaSource);



                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        });

        exoPlayer.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    // media actually playing
                    play.setVisibility(View.GONE);
                } else if (playWhenReady) {


                   if(playbackState== Player.STATE_BUFFERING)

                   {
                       play.setVisibility(View.GONE);
                     //  Toast.makeText(VideoPlayerActivity.this, "Buffering", Toast.LENGTH_SHORT).show();
                   }
                   else if(playbackState==Player.STATE_ENDED)
                   {
                       if(pos==dataResponse.size())
                       {
                           Toast.makeText(VideoPlayerActivity.this, "Last video", Toast.LENGTH_SHORT).show();
                           play.setVisibility(View.VISIBLE);
                       }else
                       {
                           chatDBUtility.UpdateTime(chatDBHelper,0,currentWindow,id);
                           id=dataResponse.get(pos).getId();
                           url=dataResponse.get(pos).getUrl();
                           exoPlayer.stop();
                           exoPlayer.seekTo(0L);
                           play.setVisibility(View.VISIBLE);
                           Uri videoURI = Uri.parse(url);

                           DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                           ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                           mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
                           //setData();

                           setAdapter();

                       }

                   }
                    // might be idle (plays after prepare()),
                    // buffering (plays when data available)
                    // or ended (plays when seek away from end)
                }

                else {
                    // player paused in any state
                }
            }
        });




    }






    private void Initializer() {

        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.videoView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        play = (Button) findViewById(R.id.play);
        description=(TextView)findViewById(R.id.description);
        title_tv=(TextView)findViewById(R.id.title_tv);

    }

    private void setAdapter() {

        dataResponse=chatDBUtility.GetDataList(chatDBHelper);

        for (int i=0;i<dataResponse.size();i++)
        {
            if(dataResponse.get(i).getId().equals(id))
            {
                pos=i;
                description.setText(dataResponse.get(i).getDescription());
                title_tv.setText(dataResponse.get(i).getTitle());
            }
        }

        dataResponse.remove(pos);


        layoutManager = new LinearLayoutManager(VideoPlayerActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        defectsAdapter = new HomepageAdapter(VideoPlayerActivity.this, dataResponse,2);
        recyclerView.setAdapter(defectsAdapter);
        recyclerView.setItemViewCacheSize(dataResponse.size());
    }





    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        releasePlayer();
    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();

            chatDBUtility.UpdateTime(chatDBHelper,playbackPosition,currentWindow,id);
            exoPlayer.release();
        }
    }


}

