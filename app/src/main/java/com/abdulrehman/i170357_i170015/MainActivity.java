package com.abdulrehman.i170357_i170015;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button sign_in, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in = findViewById(R.id.sign_in_button);
        register = findViewById(R.id.register_button);
//        new GetData().execute();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sign In Button Pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,login_activity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Register Button Pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,register_activity.class);
                startActivity(intent);
            }
        });
    }

//    public class GetData extends AsyncTask<Void,Void,String> {
//
//        String REQUEST_METHOD = "GET";
//        int READ_TIMEOUT = 15000;
//        int CON_TIMEOUT = 15000;
//        List<User> ls;
//
//        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            dialog.setMessage("Loading Users");
//            dialog.setCancelable(false);
//            dialog.show();
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            Log.d("Main Activity","before url");
//            String url = "http://192.168.18.7/italrounder/getUser.php";
//            String line;
//            String result = "";
//            try {
//                Log.d("Main Activity","after url");
//                URL url1 = new URL(url);
//                HttpURLConnection connection=(HttpURLConnection) url1.openConnection();
//                connection.setRequestMethod(REQUEST_METHOD);
//                connection.setReadTimeout(READ_TIMEOUT);
//                connection.setConnectTimeout(CON_TIMEOUT);
//                connection.connect();
//
//                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
//                    InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
//                    BufferedReader reader = new BufferedReader(streamReader);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    while((line=reader.readLine())!=null){
//                        stringBuilder.append(line);
//                    }
//                    reader.close();
//                    streamReader.close();
//                    result=stringBuilder.toString();
//                }
//                else{
//                    Toast.makeText(MainActivity.this,"Error Connecting to Server",Toast.LENGTH_LONG).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(MainActivity.this,"Error, Incorrect Request",Toast.LENGTH_LONG).show();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            dialog.cancel();
//            try {
//                ls = new ArrayList<>();
//                JSONObject responce = new JSONObject(s);
//                if(responce.getString("success").equalsIgnoreCase("1")){
//                    JSONArray users = responce.getJSONArray("users");
//                    for(int i = 0;i  < users.length();i++){
//                        JSONObject user = users.getJSONObject(i);
//                        ls.add(new User(
//                                user.getString("id"),
//                                user.getString("email"),
//                                user.getString("password")
//                        ));
//                    }
//
//                }
//                else{
//                    Toast.makeText(MainActivity.this,"Error Getting Response",Toast.LENGTH_LONG).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Toast.makeText(MainActivity.this,"Error Parsing Result",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}