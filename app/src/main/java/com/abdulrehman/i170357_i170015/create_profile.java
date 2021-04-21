package com.abdulrehman.i170357_i170015;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class create_profile extends AppCompatActivity {

    Button save;
    EditText firstName, lastName, bio;
    CircleImageView profilePic;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private final String TAG = "CreateProfile";
    private EditText mDisplayDate;
    String date;
    Bitmap photo;
    String gender;
    Editable f, l, b;
    //int birthDate,birthMonth,birthYear;
    String finFName, finLName, finBio;
    List<Contact> ls;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String email;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        save = findViewById(R.id.save_button);
        firstName = findViewById(R.id.profile_first_name);
        lastName = findViewById(R.id.profile_last_name);
        bio = findViewById(R.id.profile_bio);
        profilePic = findViewById(R.id.profile_pic);
        mDisplayDate = (EditText) findViewById(R.id.profile_date_of_birth);
        radioGroup = (RadioGroup) findViewById(R.id.profile_gender);

        email = getIntent().getStringExtra("email");
        ls = new ArrayList<>();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });
        String encoded = null;
        if (photo != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        String finalEncoded = encoded;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);
                gender = radioButton.getText().toString();
                f = firstName.getText();
                l = lastName.getText();
                b = bio.getText();

//                System.out.println("First Name = " + f.toString());
//                System.out.println("Last Name : " + l.toString());
//                System.out.println("Date of Birth : " + date);
//                System.out.println("Gender : " + gender);
//                System.out.println("Bio : " + b.toString());

                //now add into the database

                //store in the database

                String url = "http://192.168.18.7/italrounder/insertContact.php";
                StringRequest insertRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Toast.makeText(create_profile.this, obj.getString("reqmsg"), Toast.LENGTH_LONG).show();
                                    if (obj.getString("reqcode").equalsIgnoreCase("1")) {
                                        Intent intent = new Intent(create_profile.this, main_chat.class);
                                        Toast.makeText(create_profile.this, "Profile Completed", Toast.LENGTH_LONG).show();
                                        intent.putExtra("firstName",f.toString());
                                        intent.putExtra("lastName",l.toString());
                                        intent.putExtra("email",email);
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
                                Toast.makeText(create_profile.this, "Error Connecting to Server", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    public Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("firstName", f.toString()); // f = firstname
                        data.put("lastName", l.toString()); // l = lastname
                        data.put("date", date);
                        data.put("gender", gender);
                        data.put("bio", b.toString()); // b = bio
                        return data;
                    }
                };
                Volley.newRequestQueue(create_profile.this).add(insertRequest);

                //

                /*ls.add(new Contact(firstName.getText().toString(),lastName.getText().toString(),date,gender,bio.getText().toString(),finalEncoded));
                ArrayList<String> arr = new ArrayList<>();
                arr.add(f.toString());
                arr.add(l.toString());
                arr.add(date);
                arr.add(gender);
                arr.add(b.toString());
                arr.add(finalEncoded);
                intent.putStringArrayListExtra("contact",arr);*/

//                Intent intent = new Intent(create_profile.this, main_chat.class);
//                Toast.makeText(create_profile.this, "Profile Completed", Toast.LENGTH_LONG).show();
//                startActivity(intent);
            }
        });
    }

    public void onClickDate(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(create_profile.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date = dayOfMonth + "-" + month + "-" + year;
                //birthDate = dayOfMonth;
                //birthMonth = month;
                //birthYear = year;
                mDisplayDate.setText(date);
            }
        };
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            photo = (Bitmap) data.getExtras().get("data");
            profilePic.setImageBitmap(photo);

        }
    }

}