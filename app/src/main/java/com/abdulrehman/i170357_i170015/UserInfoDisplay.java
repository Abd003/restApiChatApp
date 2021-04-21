package com.abdulrehman.i170357_i170015;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoDisplay extends AppCompatActivity {

    TextView userBio,userAge,userName;
    Session session;
    RelativeLayout relativeLayout;
    String contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_display);
        contactID = getIntent().getStringExtra("id");
        relativeLayout = findViewById(R.id.background_display);

        userBio = findViewById(R.id.userBio);
        userAge = findViewById(R.id.userAge);
        userName = findViewById(R.id.userName);

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return false;
            }
        });
        String url = "http://192.168.18.7/italrounder/searchContactUsingId.php";
        StringRequest insertRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(UserInfoDisplay.this, obj.getString("reqmsg"), Toast.LENGTH_LONG).show();
                            if(obj.getString("reqcode").equalsIgnoreCase("1")){
                                userName.setText(obj.getString("firstName")+" "+obj.getString("lastName"));
                                userAge.setText(obj.getString("date"));
                                userBio.setText(obj.getString("bio"));
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserInfoDisplay.this,"Error Connecting to Server",Toast.LENGTH_LONG).show();
                    }
                }){
            public Map<String,String> getParams()
            {
                Map<String,String> data = new HashMap<>();
                data.put("contactID",contactID);
                return data;
            }
        };
        Volley.newRequestQueue(UserInfoDisplay.this).add(insertRequest);
    }
}