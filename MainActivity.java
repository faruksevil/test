package web.com.test;


import android.content.pm.PackageManager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity {
    SM ttest;
    private EditText s_text;
    private ProgressBar p_search;
    private LinearLayout r_lay;
    static ArrayList<SS> arr = new ArrayList<SS>();
    
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        
        ttest = new SM(this);
        
        setContentView(R.layout.activity_main);
        s_text = (EditText) findViewById(R.id.s_text);
        r_lay = (LinearLayout) findViewById(R.id.result_LL);
        p_search = (ProgressBar) findViewById(R.id.progressSearch);
        
        
        requestPermission();
        
        
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT)
                    .show();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                    .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
            .requestPermissions(MainActivity.this, new String[]{ACCESS_FINE_LOCATION, WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }
    
    public void c_Search(View v) {
        ttest.AA(s_text.getText().toString());
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                
                
                arr = ttest.getArra();
                
                System.out.println("size of arr: " + arr.size());
                setLay();
                
            }
        }, 2000);
        
        
    }
    
    public void setLay() {
        for (int i = 0; i < arr.size(); i++) {
            final String id;
            final String title;
            
            TextView tvResult = (TextView) getLayoutInflater().inflate(R.layout.get_result, null);
            tvResult.setText((i + 1) + " - " + " ♫ " + " - " + arr.get(i).getT() + ".mp3");
            
            id = arr.get(i).getU();
            title = arr.get(i).getT();
            
            tvResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GM.get_Music(id, title, "", MainActivity.this);
                }
            });
            
            r_lay.addView(tvResult);
        }
    }
}

