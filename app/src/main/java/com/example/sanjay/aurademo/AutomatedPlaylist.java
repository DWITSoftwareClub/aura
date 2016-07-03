package com.example.sanjay.aurademo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AutomatedPlaylist extends AppCompatActivity {
    Intent intent;
    ListView lv;
    String[] items;
    String moodValue;
    MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
    byte[] art;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //Bundle extras=getIntent().getExtras();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__songs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.playlistview);
       moodValue=getIntent().getExtras().getString("userMood").toString();
        System.out.println("the value is " +moodValue);
        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
            if()
        for (int i = 0; i < mySongs.size(); i++) {
          //  toast(mySongs.get(i).getName().toString());
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "");
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.song_layout, R.id.textView, items);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), Player.class).putExtra("pos", position).putExtra("songlist", mySongs));
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
        //PlaylistGeneration playlist = new PlaylistGeneration();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {

                al.addAll(findSongs(singleFile));

            } else {
                if (singleFile.getName().endsWith(".mp3")) {
                   String genre = playlistBygenre(singleFile.getAbsolutePath());
                  String returnedContext = playlistByMood(singleFile.getAbsolutePath(), moodValue);
                    System.out.println("The genre is " +genre);
                    System.out.println("The returned context is  " +returnedContext);
                    if(genre==returnedContext) {
                        al.add(singleFile);
                    }
                }
            }
        }
        return al;

    }

    public void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aura_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AutomatedPlaylist Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sanjay.aurademo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AutomatedPlaylist Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sanjay.aurademo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    public String playlistBygenre(String path) {
        String genre="";
        metaRetriver.setDataSource(path);
        System.out.println("thee path is " + path);
        try {

            genre = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
            System.out.println("the genre under Playlist by genre is " +genre);
            if(genre==null){
                genre="Adele";
            }
        } catch (Exception e) {
            genre="Other";
        }
        return genre;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public String playlistByMood(String path,  String mood){
//        MediaExtractor mex = new MediaExtractor();
//        try {
//            mex.setDataSource(path);// the adresss location of the sound on sdcard.
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
           String songvalue= evaluateMusicAccordingToMood(mood);
//
//        MediaFormat mf = mex.getTrackFormat(0);

//        int bitRate = mf.getInteger(MediaFormat.KEY_BIT_RATE);
//        int sampleRate = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE);
//        int channelCount = mf.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
//        try {
//            art = metaRetriver.getEmbeddedPicture();
//            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
//            String album = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
//            String artist = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//        }


//        catch (Exception e)
//        {
//
//        }
        return songvalue;
    }

    public String evaluateMusicAccordingToMood(String mood){
        String a="Other";
        switch (mood){
            case "Happy":
                a="Hard Rock";
                break;
            case "Sad":
                a="Blues";
                break;
            case "Indifferent":
                a="Pop";
                break;
            case "Angry":
                a="Metal";
                break;
            case "Anxious":
                a="Other";
                break;
            case "Party":
                a="Bollywood";
                break;
            case "Loved":
                a="Romantic";
                break;
        }
        return a;
    }

}


