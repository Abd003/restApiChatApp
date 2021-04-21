package com.abdulrehman.i170357_i170015;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class login_activity extends AppCompatActivity {

    EditText email,password;
    TextView register_here;
    Button sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        email =(EditText) findViewById(R.id.username_sign_in);
        password =(EditText) findViewById(R.id.password_sign_in);
        register_here =(TextView) findViewById(R.id.register_here);
        sign_in = findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.18.7/italrounder/search.php";
                StringRequest insertRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Toast.makeText(login_activity.this, obj.getString("reqmsg"), Toast.LENGTH_LONG).show();
                                    if(obj.getString("reqcode").equalsIgnoreCase("1")){
                                        Intent intent = new Intent(login_activity.this,main_chat.class);
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
                                Toast.makeText(login_activity.this,"Error Connecting to Server",Toast.LENGTH_LONG).show();
                                Toast.makeText(login_activity.this,"Checking the local Database Now",Toast.LENGTH_LONG).show();
                                getData();
                            }
                        }){
                    public Map<String,String> getParams()
                    {
                        Map<String,String> data = new HashMap<>();
                        data.put("email",email.getText().toString());
                        //data.put("password",password.getText().toString());
                        return data;
                    }
                };
                Volley.newRequestQueue(login_activity.this).add(insertRequest);
            }
        });
    }

    public void onClick(View v){
        Toast.makeText(login_activity.this, "Going to Sign In...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(login_activity.this,register_activity.class);
        startActivity(intent);
    }
    public void getData(){
        MyDBHelper myDBHelper=new MyDBHelper(login_activity.this);
        SQLiteDatabase database=myDBHelper.getReadableDatabase();
        String[] projecion=new String[]{
                MyUserContract.Users._ID,
                MyUserContract.Users._EMAIL,
                MyUserContract.Users._PASSWORD
        };

        String sort=MyUserContract.Users._EMAIL+" ASC";
        Cursor c=database.query(MyUserContract.Users.TABLENAME,projecion,null,null,null,null,sort);

        if((c.getString(c.getColumnIndex(MyUserContract.Users._EMAIL)).equalsIgnoreCase(email.getText().toString()))
        && (c.getString(c.getColumnIndex(MyUserContract.Users._PASSWORD)).equalsIgnoreCase(password.getText().toString()))){
            Toast.makeText(login_activity.this, "User Found!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(login_activity.this,main_chat.class);
            intent.putExtra("email",email.getText().toString());
            startActivity(intent);
        }
    }
}