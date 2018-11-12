package com.example.edu.mymedia;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mbuttonRead= (Button)findViewById(R.id.buttonFileReadFromInner);
        Button mbuttonWrite=(Button)findViewById(R.id.buttonFileWriteFromInner);

        mbuttonRead.setOnClickListener(this);
        mbuttonWrite.setOnClickListener(this);

        int permission=ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length >0 || grantResults[0] == PackageManager.PERMISSION_GRANTED){
    Log.i("","Permission has been granted by user");
            }
        }
    }

    @Override
    public void onClick(View v) {
        EditText editTextInput = findViewById(R.id.editTextInput);
        TextView mtextViewRead=findViewById(R.id.textView_read);
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream=null;
        switch (v.getId()){
            case R.id.buttonFileReadFromInner:

                try {
                    fileInputStream = openFileInput("aaa.txt");
                    byte[] buffer=new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    editTextInput.setText(new String(buffer));
                    fileInputStream.close();
                    break;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            case R.id.buttonFileWriteFromInner:
                try {
                    fileOutputStream =openFileOutput("aaa.txt", Context.MODE_APPEND);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fileOutputStream.write(editTextInput.getText().toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                editTextInput.setText("");
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
