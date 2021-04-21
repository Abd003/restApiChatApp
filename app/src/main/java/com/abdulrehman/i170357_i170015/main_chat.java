package com.abdulrehman.i170357_i170015;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class main_chat extends AppCompatActivity implements MyRvAdapter.OnItemListner{

    ImageView menu;
    CircleImageView image;
    AutoCompleteTextView search;
    DrawerLayout drawMenu;
    List<Contact> ls;
    RecyclerView rv;
    static String sfirstname, slastname;
    TextView drawerfirstName, drawerlastName, draweremail;
    String email;
    MyRvAdapter adapter;
    String contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        menu = findViewById(R.id.menu_icon);
        image = findViewById(R.id.search_image);
        search = (AutoCompleteTextView) findViewById(R.id.search);
        drawerfirstName =(TextView) findViewById(R.id.drawer_profile_first_name);
        drawerlastName =(TextView) findViewById(R.id.drawer_profile_last_name);
        draweremail =(TextView) findViewById(R.id.drawer_profile_email);
        drawMenu = findViewById(R.id.drawer_menu);
        sfirstname = getIntent().getStringExtra("firstName");
        slastname = getIntent().getStringExtra("lastName");
        email = getIntent().getStringExtra("email");
        sfirstname = "Abdul";
        slastname = "Rehman";
        if((sfirstname == null || sfirstname.length() == 0) || (slastname== null || slastname.length() == 0)){
            searchforspecificContact(email);
        }
        else{
            drawerfirstName.setText(sfirstname);
            drawerlastName.setText(slastname);
        }

        draweremail.setText(email);
        rv = findViewById(R.id.rv);
        /*if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
        }*/
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(main_chat.this, "Menu Pressed", Toast.LENGTH_LONG).show();
                if (drawMenu.isDrawerOpen(Gravity.LEFT)) {
                    drawMenu.closeDrawer(Gravity.LEFT);
                } else {
                    drawMenu.openDrawer(Gravity.LEFT);
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_chat.this, UserInfoDisplay.class);
                intent.putExtra("id",contactID);
                startActivity(intent);
            }
        });

        new GetData().execute();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

    }
    public void onClickLogout(View view) {
        Intent intent = new Intent(main_chat.this, login_activity.class);
        startActivity(intent);
    }

    @Override
    public void ONItemClick(int position) {
        Intent intent = new Intent(main_chat.this, chatActivity.class);
        intent.putExtra("firstName", ls.get(position).getFirstName());
        intent.putExtra("lastName", ls.get(position).getLastName());
        intent.putExtra("sfirstName",sfirstname);
        intent.putExtra("slastName",slastname);
        startActivity(intent);
    }


    class GetData extends AsyncTask<Void, Void, String> {
        String REQUEST_METHOD = "GET";
        int READ_TIMEOUT = 15000;
        int CON_TIMEOUT = 15000;
//            List<User> ls;

        ProgressDialog dialog = new ProgressDialog(main_chat.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading Contacts");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d("Main Activity", "before url");
            String url = "http://192.168.18.7/italrounder/getContact.php";
            String line;
            String result = "";
            try {
                Log.d("Main Activity", "after url");
                URL url1 = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CON_TIMEOUT);
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    streamReader.close();
                    result = stringBuilder.toString();
                } else {
                    Toast.makeText(main_chat.this, "Error Connecting to Server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(main_chat.this, "Error, Incorrect Request", Toast.LENGTH_LONG).show();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            try {
                ls = new ArrayList<>();
                JSONObject responce = new JSONObject(s);
                if (responce.getString("success").equalsIgnoreCase("1")) {
                    JSONArray users = responce.getJSONArray("contacts");
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject contact = users.getJSONObject(i);
                        System.out.println("Default First Name = " + sfirstname);
                        System.out.println("Default Last Name = " + slastname);
                        if (contact.getString("firstName").equalsIgnoreCase(sfirstname) && contact.getString("lastName").equalsIgnoreCase(slastname)){}
                        else{
                            ls.add(new Contact(
                                    //String firstName, String lastName, String date,String gender, String bio
                                    contact.getString("firstName"),
                                    contact.getString("lastName"),
                                    contact.getString("date"),
                                    contact.getString("gender"),
                                    contact.getString("bio")
                            ));
                        }
                    }
                    adapter = new MyRvAdapter(ls, main_chat.this, main_chat.this);
                    rv.setAdapter(adapter);


                } else {
                    Toast.makeText(main_chat.this, "Error Getting Response", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(main_chat.this, "Error Parsing Result", Toast.LENGTH_LONG).show();
            }
        }

    }
    private void searchforspecificContact(String email) {
        String url = "http://192.168.18.7/italrounder/search.php";
        StringRequest insertRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(main_chat.this, obj.getString("reqmsg"), Toast.LENGTH_LONG).show();
                            if(obj.getString("reqcode").equalsIgnoreCase("1")){
                                contactID = obj.getString("id");
                                searchforspecificContactDetails(contactID);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(main_chat.this,"Error Connecting to Server",Toast.LENGTH_LONG).show();
                    }
                }){
            public Map<String,String> getParams()
            {
                Map<String,String> data = new HashMap<>();
                data.put("email",email);
                return data;
            }
        };
        Volley.newRequestQueue(main_chat.this).add(insertRequest);
    }
    private void searchforspecificContactDetails(String contactID){
        System.out.println("User ID = "+ contactID);
        String url = "http://192.168.18.7/italrounder/getContDetails.php";
        StringRequest insertRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(main_chat.this, obj.getString("reqmsg"), Toast.LENGTH_LONG).show();
                            if(obj.getString("reqcode").equalsIgnoreCase("1")){
                                sfirstname = obj.getString("firstName");
                                slastname = obj.getString("lastName");
                                drawerfirstName.setText(obj.getString("firstName"));
                                drawerlastName.setText(obj.getString("lastName"));
                                System.out.println("Original First Name = " + sfirstname);
                                System.out.println("Original Last Name = " + slastname);
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
                        Toast.makeText(main_chat.this,"Error Connecting to Server",Toast.LENGTH_LONG).show();
                    }
                }){
            public Map<String,String> getParams()
            {
                Map<String,String> data = new HashMap<>();
                data.put("contactID",contactID);
                return data;
            }
        };
        Volley.newRequestQueue(main_chat.this).add(insertRequest);
    }
}