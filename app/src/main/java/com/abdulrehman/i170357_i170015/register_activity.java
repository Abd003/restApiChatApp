package com.abdulrehman.i170357_i170015;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register_activity extends AppCompatActivity {

    EditText email, password, con_password;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        email =(EditText) findViewById(R.id.email_register);
        password =(EditText) findViewById(R.id.password_register);
        con_password =(EditText) findViewById(R.id.conf_password_register);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insertion in MySQL Database via PHP now
                Toast.makeText(register_activity.this,"Register Button Clicked!",Toast.LENGTH_SHORT).show();
                String url = "http://192.168.18.7/italrounder/insert.php";
                StringRequest insertRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Toast.makeText(register_activity.this,obj.getString("reqmsg"),Toast.LENGTH_LONG).show();
                                    if(obj.getString("reqcode").equalsIgnoreCase("1")){
                                        insertInLocalDatabase();
                                        Intent intent = new Intent(register_activity.this,create_profile.class);
                                        intent.putExtra("email",email.getText().toString());
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(register_activity.this,"Error Connecting to Server",Toast.LENGTH_LONG).show();
                            }
                        }){
                    public Map<String,String> getParams()
                    {
                        Map<String,String> data = new HashMap<>();
                        data.put("email",email.getText().toString());
                        data.put("password",password.getText().toString());
                        return data;
                    }
                };
                Volley.newRequestQueue(register_activity.this).add(insertRequest);
            }
        });
    }

    public void insertInLocalDatabase() {
        Toast.makeText(register_activity.this,"Register Button Clicked!",Toast.LENGTH_SHORT).show();
        //Insertion in Local Database via now
        MyDBHelper myDBHelper=new MyDBHelper(register_activity.this);
        SQLiteDatabase database=myDBHelper.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(MyUserContract.Users._EMAIL,email.getText().toString());
        cv.put(MyUserContract.Users._PASSWORD,password.getText().toString());
        double res=database.insert(MyUserContract.Users.TABLENAME,null,cv);
        if(res!=-1){
            Toast.makeText(register_activity.this,"User Inserted in Local Database!",Toast.LENGTH_SHORT).show();
        }
        database.close();
        myDBHelper.close();
        return;
    }

    public void onClick(View v){
        Toast.makeText(register_activity.this, "Going to Sign In...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(register_activity.this,login_activity.class);
        startActivity(intent);
    }
}