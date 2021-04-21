package com.abdulrehman.i170357_i170015;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class chatActivity extends AppCompatActivity {

    String Fname, Lname, sfirstname, slastname;
    //private WebSocket webSocket;
    //private String SERVER_PATH = "ws://echo.websocket.org";
    EditText messageEdit;
    View sendButton, pickImg_button;
    RecyclerView recyclerView;
    int IMAGE_REQUEST_ID = 1;
    MessageAdapter messageAdapter;
    ImageView backArrow;
    Editable ms;
    List<Message> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Fname = getIntent().getStringExtra("firstName");
        Lname = getIntent().getStringExtra("lastName");
        sfirstname = getIntent().getStringExtra("sfirstName");
        slastname = getIntent().getStringExtra("slastName");
        System.out.println("First Name = " + Fname);
        System.out.println("Last Name = " + Lname);
        System.out.println("My First Name = " + sfirstname);
        System.out.println("My Last Name = " + slastname);
        sendButton = findViewById(R.id.send_message_button);
        messageEdit = (EditText) findViewById(R.id.messageEdit);
        recyclerView = findViewById(R.id.recycle_view_messages);


        backArrow = (ImageView) findViewById(R.id.back_arrow_icon);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ms= messageEdit.getText();
        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new GetData().execute();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //messageEdit.setText("");
                String url = "http://192.168.18.7/italrounder/insertMessage.php";
                StringRequest insertRequest = new StringRequest(Request.Method.POST, url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Toast.makeText(chatActivity.this, obj.getString("reqmsg"), Toast.LENGTH_LONG).show();
                                    if (obj.getString("reqcode").equalsIgnoreCase("1")) {
                                        messageEdit.setText("");
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("message",ms.toString());
                                        jsonObject.put("isSent",true);
                                        messageAdapter.addItem(jsonObject);
                                        messageAdapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(chatActivity.this, "Error Connecting to Server", Toast.LENGTH_LONG).show();
                                messageEdit.setText("");
                            }
                        }) {
                    public Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("sfirstName", sfirstname);
                        data.put("slastName", slastname);
                        data.put("message", ms.toString());
                        data.put("rfirstName", Fname);
                        data.put("rlastName", Lname);
                        return data;
                    }
                };
                Volley.newRequestQueue(chatActivity.this).add(insertRequest);
            }
        });
        //initiateSocketConnection();
    }
    /*
    private void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListner());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        String string = s.toString().trim();
        if(string.isEmpty()){
            resetMessageEdit();
        }
        else{
            sendButton.setVisibility(View.VISIBLE);
            //pickImg_button.setVisibility(View.INVISIBLE);
        }
    }

    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);
        messageEdit.setText("");
        sendButton.setVisibility(View.VISIBLE);
        //pickImg_button.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);
    }

    private class SocketListner extends WebSocketListener{
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            runOnUiThread(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(chatActivity.this,"Socket Connection Successful!",Toast.LENGTH_LONG).show();
                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            runOnUiThread(()-> {

                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent",false);
                    messageAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private void initializeView() {
        messageEdit = findViewById(R.id.messageEdit);
        sendButton = findViewById(R.id.send_message_button);
        //pickImg_button = findViewById(R.id.send_message_Image);
        recyclerView = findViewById(R.id.recycle_view_messages);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageEdit.addTextChangedListener(this);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("firstName",Fname);
                    jsonObject.put("lastName",Lname);
                    jsonObject.put("message",messageEdit.getText().toString());
                    webSocket.send(jsonObject.toString());
                    jsonObject.put("isSent",true);
                    messageAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                    resetMessageEdit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        /*pickImg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("Image/*");
                startActivityForResult(Intent.createChooser(intent,"Pick Image"),IMAGE_REQUEST_ID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK){
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Bitmap photo = BitmapFactory.decodeStream(is);
                sendImage(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendImage(Bitmap photo) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
        String base64String = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName",Fname);
            jsonObject.put("lastName",Lname);
            jsonObject.put("image",base64String);
            webSocket.send(jsonObject.toString());
            jsonObject.put("isSent",true);
            messageAdapter.addItem(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    class GetData extends AsyncTask<Void, Void, String> {

        String REQUEST_METHOD = "GET";
        int READ_TIMEOUT = 15000;
        int CON_TIMEOUT = 15000;
//      List<User> ls;

        ProgressDialog dialog = new ProgressDialog(chatActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setMessage("Loading Messages");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d("Main Activity", "before url");
            String url = "http://192.168.18.7/italrounder/getMessage.php";
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
                    Toast.makeText(chatActivity.this, "Error Connecting to Server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(chatActivity.this, "Error, Incorrect Request", Toast.LENGTH_LONG).show();
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
                    JSONArray messages = responce.getJSONArray("messages");
                    for (int i = 0; i < messages.length(); i++) {
                        JSONObject message = messages.getJSONObject(i);
                        JSONObject jsonObject = new JSONObject();
                        ls.add(new Message(
                                message.getString("sfirstName"),
                                message.getString("slastName"),
                                message.getString("message"),
                                message.getString("rfirstName"),
                                message.getString("rlastName")
                        ));
                        if ((message.getString("sfirstName").equalsIgnoreCase(sfirstname) || message.getString("rfirstName").equalsIgnoreCase(sfirstname))
                                &&(message.getString("sfirstName").equalsIgnoreCase(Fname) || message.getString("rfirstName").equalsIgnoreCase(Fname)))
                        {
                            jsonObject.put("message",message.getString("message"));
                            if(message.getString("sfirstName").equalsIgnoreCase(sfirstname)){
                                jsonObject.put("isSent",true);
                            }
                            else{
                                jsonObject.put("firstName", message.getString("sfirstName"));
                                jsonObject.put("lastName", message.getString("slastName"));
                                jsonObject.put("isSent",false);
                            }
                            messageAdapter.addItem(jsonObject);
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        }

                    }

                } else {
                    Toast.makeText(chatActivity.this, "Error Getting Response", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(chatActivity.this, "Error Parsing Result", Toast.LENGTH_LONG).show();
            }
        }
    }
}