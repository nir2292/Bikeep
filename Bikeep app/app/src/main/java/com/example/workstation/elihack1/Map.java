package com.example.workstation.elihack1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.R;

public class Map extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);

    }

    public void GoToQRScan(View v) {
/*
        Intent i = new Intent(getApplicationContext(), BikeParked.class);
        startActivity(i);
*/
        IntentIntegrator integrator = new IntentIntegrator(Map.this);
        integrator.initiateScan();
/*
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);

        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {

            // handle scan result
            String contantsString = scanResult.getContents() == null ? "0" : scanResult.getContents();
            Log.i("huji", "qr scan resault: " + contantsString);

            if (contantsString.equalsIgnoreCase("0")) {
                Toast.makeText(this, "Problem to get the  contant Number", Toast.LENGTH_LONG).show();

            }
            //sucsses
            else {
                Intent intent = new Intent(getApplicationContext(), BikeParked.class);
                startActivity(intent);
            }

        }
        else{
            Toast.makeText(this, "Problem to secan the barcode.", Toast.LENGTH_LONG).show();
        }
/*        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.i("huji", "qr scan resault: " + contents);
                Intent intent = new Intent(getApplicationContext(), BikeParked.class);
                startActivity(intent);
                finish();
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("huji", Context.MODE_PRIVATE);
        sharedPref.edit().putBoolean("huji",false).apply();
    }
}
