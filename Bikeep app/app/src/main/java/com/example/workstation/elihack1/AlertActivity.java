package com.example.workstation.elihack1;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.R;

public class AlertActivity extends Activity implements View.OnClickListener {

    private SurfaceView surfaceView;
    private ImageButton btnPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alert);
        init();
        addListener();
    }

    private void init() {
        // TODO Auto-generated method stub
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
    }

    private void addListener() {
        // TODO Auto-generated method stub

        btnPlay.setOnClickListener(this);
    }

    MediaPlayer mediaPlayer = null;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnPlay:
                View b;
                b = findViewById(R.id.back_alert);
                b.setVisibility(View.GONE);
                b = findViewById(R.id.alert_block);
                b.setVisibility(View.GONE);
                b = findViewById(R.id.buttons);
                b.setVisibility(View.GONE);

                try{
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }else{
                        getWindow().setFormat(PixelFormat.UNKNOWN);
                        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sapir2);
                        mediaPlayer.setLooping(false);

                        SurfaceHolder surfaceHolder = surfaceView.getHolder();

                        surfaceHolder.setFixedSize(176, 144);

                        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                        mediaPlayer.setDisplay(surfaceHolder);

                        //---------------
                        int videoWidth = mediaPlayer.getVideoWidth();
                        int videoHeight = mediaPlayer.getVideoHeight();

                        //Get the width of the screen
                        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

                        //Get the SurfaceView layout parameters
                        android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();

                        //Set the width of the SurfaceView to the width of the screen
                        lp.width = (int) (((float)videoWidth / (float)videoHeight) * (float)screenHeight);

                        //Set the height of the SurfaceView to match the aspect ratio of the video
                        //be sure to cast these as floats otherwise the calculation will likely be 0
                        lp.height = screenHeight;

                        //Commit the layout parameters
                        surfaceView.setLayoutParams(lp);
                        //---------------

                        mediaPlayer.start();}
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public void gotoPark(View v) {
        Intent i = new Intent(getApplicationContext(), BikeParked.class);
        startActivity(i);
    }

    //@Override
    //public void onPause() {
    //    super.onPause();
    //    mediaPlayer.stop();
    //}

}
