package com.abdulrehman.i170357_i170015;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    TextView PersonBio, PersonAge, PersonName;
    Session session;

//    List<Contact> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        PersonBio = findViewById(R.id.PersonBio);
        PersonAge = findViewById(R.id.PersonAge);
        PersonName = findViewById(R.id.PersonName);

        session = new Session(this);


        PersonBio.setText(session.getBio());
        PersonAge.setText(session.getGender() + "," + session.getDate());
        PersonName.setText(session.getFirstName() + " " + session.getLastName());

//        else { // incase of login
//
//            PersonBio.setText(ls.get(0).getBio());
//            PersonAge.setText(ls.get(0).getGender() + "," + ls.get(0).getDate());
//            PersonName.setText(ls.get(0).getFirstName() + " " + ls.get(0).getLastName());
//
//        }

    }

//    class GetData extends AsyncTask<Void, Void, String> {
//
//        String REQUEST_METHOD = "GET";
//        int READ_TIMEOUT = 15000;
//        int CON_TIMEOUT = 15000;
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            Log.d("User Profile", "before url");
//            String url = "http://192.168.112.1/italrounder/searchContactUsingId.php";
//            String line;
//            String result = "";
//            try {
//                Log.d("User Profile", "after url");
//                URL url1 = new URL(url);
//                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
//                connection.setRequestMethod(REQUEST_METHOD);
//                connection.setReadTimeout(READ_TIMEOUT);
//                connection.setConnectTimeout(CON_TIMEOUT);
//                connection.connect();
//
//                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
//                    BufferedReader reader = new BufferedReader(streamReader);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    while ((line = reader.readLine()) != null) {
//                        stringBuilder.append(line);
//                    }
//                    reader.close();
//                    streamReader.close();
//                    result = stringBuilder.toString();
//                } else {
//                    Toast.makeText(UserProfile.this, "Error Connecting to Server", Toast.LENGTH_LONG).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(UserProfile.this, "Error, Incorrect Request", Toast.LENGTH_LONG).show();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                ls = new ArrayList<>();
//                JSONObject responce = new JSONObject(s);
//                if (responce.getString("reqcode").equalsIgnoreCase("1")) {
//                    JSONArray users = responce.getJSONArray("contacts");
//                    for (int i = 0; i < users.length(); i++) {
//                        JSONObject contact = users.getJSONObject(i);
//                        ls.add(new Contact(
//                                //String firstName, String lastName, String date,String gender, String bio
//                                contact.getString("firstName"),
//                                contact.getString("lastName"),
//                                contact.getString("date"),
//                                contact.getString("gender"),
//                                contact.getString("bio")
//                        ));
//                    }
//                } else {
//                    Toast.makeText(UserProfile.this, "Error Getting Response", Toast.LENGTH_LONG).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Toast.makeText(UserProfile.this, "Error Parsing Result", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}