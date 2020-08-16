package com.example.sohaibrabbani.psychx;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import Model.ChatHistory;
import Model.DatabaseFilter;
import Model.Hobby;
import Model.User;
import Model.UserHobby;

/**
 * Created by Amjad on 07-Jan-18.
 */

public class PsychxWebService
{
    public static final String BASE_URL = "http://10.20.25.166:8000",
    INSERT_USER_HOBBY = "insert_user_hobby",
    INSERT_USER = "insert",
    UPDATE_USER = "update_user",
            UPDATE_PHOTO = "update_photo",
    VERIFY_USER = "verify",
    CHAT_REPLY = "reply",
    GET_USER_HOBBIES = "get_user_hobbies",
    GET_HOBBIES = "get_hobbies",
    FORGOT_PASSWORD = "forgot_password",
    RESET_PASSWORD = "reset_password",
    EMOTIONS = "emotions",
    EMOTIONS_DATE_WISE = "emotions_daily",
    PROMPT_CHAT= "prompt",
    SLASH = "/";
    public static String SESSION_ID = "new";

    public void createUser(final User user)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String response = "";
                try {
                    URL url =new URL(BASE_URL + SLASH + INSERT_USER);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();
                    String email = user.getEmail();
                    String phone = user.getPhone();
                    String photo = user.getImage();
                    String password = user.getPassword();
                    String dob = user.getDob();

                    String gender = String.valueOf(user.getGender());

                    Log.d("Gender Service", gender);



                    writer.write("first_name="+firstName+
                            "&last_name="+lastName+
                            "&email="+email+
                            "&phone="+phone+
                            "&dob="+dob+"" +
                            "&photo="+photo+
                            "&password="+password+
                            "&gender="+gender);
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK)
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    }
                    else if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP)//302
                    {
                        Log.d("Found", "YES");
                    }
                    else
                    {
                        response = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    public void getVerifiedUser(final LoginActivity context, final String email, final String password)
    {
        new AsyncTask<Void, Void, User>(){



            ProgressDialog dialog = new ProgressDialog(context);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("Verifying Account, please wait.");
                dialog.show();
            }


            @Override
            protected User doInBackground(Void... voids) {
                String response = "";
                try {
                    URL url =new URL(BASE_URL + SLASH + VERIFY_USER);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();

                    Log.d("Login Email:", email);
                    Log.d("Login Pass:", password);
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("email="+email+"&password="+password);
                    writer.flush();
                    writer.close();

                    os.close();

                    User user = null;
                    int responseCode = conn.getResponseCode();

                    Log.d("Login Res Code:", String.valueOf(responseCode));
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                        user = new User();

                        JSONObject json= new JSONObject(response);
                        JSONObject userJSON= new JSONObject(json.getString("user"));
                        user.setFirstName(userJSON.getString("first_name"));
                        user.setLastName(userJSON.getString("last_name"));
                        user.setPhone(userJSON.getString("phone"));
                        user.setDob(userJSON.getString("dob"));
                        user.setEmail(json.getString("email"));
                        user.setGender(userJSON.getString("gender").charAt(0));
                        user.setImage(userJSON.getString("photo"));
                        user.setUid(userJSON.getInt("id"));

                        return user;
                    } else {

                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(User user) {
//                super.onPostExecute(user);


                if(user != null)
                {
                    Log.d("Login Async", "In");

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    context.ProceedToDashboard(user);
                    //Proceed to dash-board
                }
                if (dialog.isShowing())
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("There was a problem signing into your account.");
                    alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }
                    );

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    dialog.dismiss();
                }
            }
        }.execute();
    }



    public void resetPassword(final RecoverPasswordActivity context, final String email, final String password) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(BASE_URL + SLASH + RESET_PASSWORD);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();

                    Log.d("Login Email:", email);
                    Log.d("Login Pass:", password);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("email=" + email + "&password=" + password);
                    writer.flush();
                    writer.close();

                    os.close();

                    int responseCode = conn.getResponseCode();

                    Log.d("Login Res Code:", String.valueOf(responseCode));
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        context.proceedToSignInActivity(true);
                    } else {
                        context.proceedToSignInActivity(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    public void updateUser(final User user, final Bitmap bitmap)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String response = "";
                try {
                    URL url =new URL(BASE_URL + SLASH + UPDATE_USER);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String firstName = user.getFirstName(),
                            lastName = user.getLastName(),
                            email = user.getEmail(),
                            phone = user.getPhone();

                    String dob = user.getDob();

                    char gender = user.getGender();

                    StringBuilder json_data = new StringBuilder("first_name="+firstName+
                            "&last_name="+lastName+
                            "&email="+email+
                            "&phone="+phone+
                            "&dob="+dob+
                            "&gender="+String.valueOf(gender));
                    if(bitmap != null) {
                        new PsychxWebService().updateUserPhoto(email, bitmap);
                    }
//                    else
//                        json_data.append("&image_data=no_image");
                    //Serialize user pbject and send it in request body
                    Log.d("Image", json_data.toString());
                    writer.write(json_data.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    } else {
                        response = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    public void updateUserPhoto(final String email, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStreamObject;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("email", email);
                HashMapParams.put("server", BASE_URL + SLASH);
                HashMapParams.put("photo", ConvertImage);
                imageProcessClass.ImageHttpRequest(BASE_URL + SLASH + UPDATE_PHOTO, HashMapParams);
                return null;
            }
        }.execute();
    }


    public void updateUser(final User user)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String response = "";
                try {
                    URL url =new URL(BASE_URL + SLASH + UPDATE_USER);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String firstName = user.getFirstName(),
                            lastName = user.getLastName(),
                            email = user.getEmail(),
                            phone = user.getPhone();

                    String dob = user.getDob();

                    char gender = user.getGender();

                    StringBuilder json_data = new StringBuilder("first_name="+firstName+
                            "&last_name="+lastName+
                            "&email="+email+
                            "&phone="+phone+
                            "&dob="+dob+
                            "&gender="+String.valueOf(gender)+
                            "&image_data=no_image");

                    //Serialize user pbject and send it in request body
                    writer.write(json_data.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    } else {
                        response = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    public void insertUserHobby(final UserHobby userHobby)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String response = "";
                try {
                    URL url =new URL(BASE_URL + SLASH + INSERT_USER_HOBBY);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String user_id = String.valueOf(userHobby.getUser_id());
                    String hobby_id = String.valueOf(userHobby.getHobby_id());

                    writer.write("user_id="+user_id+"&hobby_id="+hobby_id);
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    } else {
                        response = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();

    }

    public void insertUserHobbyList(final List<UserHobby> userHobby)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String response = "";
                try {
                    URL url =new URL(BASE_URL + SLASH + INSERT_USER_HOBBY);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    JSONArray hobby_list = new JSONArray();
                    for(UserHobby hobby: userHobby)
                    {
                        JSONObject temp_hobby = new JSONObject();
                        temp_hobby.put("user_id", hobby.getUser_id());
                        temp_hobby.put("hobby_id", hobby.getHobby_id());
//                        if(hobby_list.length()<3)
                        hobby_list.put(temp_hobby);
                    }

//                    JSONObject json_array_object = new JSONObject();
//                    json_array_object.put("hobby_list", hobby_list);
//                    String json_string = json_array_object.toString();
                    writer.write("hobby_list=" + hobby_list.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    } else {
                        response = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();

    }



    public void getPromptChat(final Context context, final int userId)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                String response = "";
                try {
                    URL url = new URL(BASE_URL + SLASH + PROMPT_CHAT);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    String postString = "user_id=" + userId;

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(postString);
                    writer.flush();
                    writer.close();

                    os.close();

                    int responseCode = conn.getResponseCode();

                    Log.d("Login Res Code:", String.valueOf(responseCode));
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }

                        ChatHistory message=new ChatHistory();
                        JSONObject json = new JSONObject(response);

                        message.setMessage(json.getString("message"));
                        message.setMessage_time(json.get("message_time").toString());
                        message.setResponse(json.get("response").toString());
                        message.setAnger(Float.valueOf(json.getString("anger")));
                        message.setAnger(Float.valueOf(json.getString("fear")));
                        message.setAnger(Float.valueOf(json.getString("disgust")));
                        message.setAnger(Float.valueOf(json.getString("sadness")));
                        message.setAnger(Float.valueOf(json.getString("joy")));
                        message.setAnger(Float.valueOf(json.getString("surprise")));
                        DatabaseFilter.insertChatMessage(context, message);
                        PsychxWebService.SESSION_ID = json.getString("session_id");

                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;


            }
        }.execute();

    }


    public void getChatResponse(final ChatActivity chatContext, final ChatHistory message)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                String response = "";
                try {
                    URL url = new URL(BASE_URL + SLASH + CHAT_REPLY);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();

//                    Log.d("Login Email:", email);
//                    Log.d("Login Pass:", password);

                    String postString = "user_id=" + message.getSender_id() + "&message=" + message.getMessage() +
                                "&session_id=" + PsychxWebService.SESSION_ID;

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(postString);
                    writer.flush();
                    writer.close();

                    os.close();

                    int responseCode = conn.getResponseCode();

                    Log.d("Login Res Code:", String.valueOf(responseCode));
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }

                        JSONObject json = new JSONObject(response);

                        message.setMessage(json.getString("message"));
                        message.setMessage_time(json.get("message_time").toString());
                        message.setResponse(json.get("response").toString());
                        message.setAnger(Float.valueOf(json.getString("anger")));
                        message.setAnger(Float.valueOf(json.getString("fear")));
                        message.setAnger(Float.valueOf(json.getString("disgust")));
                        message.setAnger(Float.valueOf(json.getString("sadness")));
                        message.setAnger(Float.valueOf(json.getString("joy")));
                        message.setAnger(Float.valueOf(json.getString("surprise")));
                        DatabaseFilter.insertChatMessage(chatContext, message);
                        PsychxWebService.SESSION_ID = json.getString("session_id");
                        chatContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatContext.generateResponse(message.getResponse());
                            }
                        });

                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;


            }
        }.execute();

    }


    public void getEmotions(final ReportingAllEmotionFragment context, final int user_id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                String response = "";
                try {
                    URL url = new URL(BASE_URL + SLASH + EMOTIONS);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("user_id=" + user_id);
                    writer.flush();
                    writer.close();

                    os.close();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }

//                        JSONObject temp = new JSONObject(response);
                        ArrayList<Double> joy_list = new ArrayList<>();
                        ArrayList<Double> fear_list = new ArrayList<>();
                        ArrayList<Double> surprise_list = new ArrayList<>();
                        ArrayList<Double> disgust_list = new ArrayList<>();
                        ArrayList<Double> anger_list = new ArrayList<>();
                        ArrayList<Double> sadness_list = new ArrayList<>();

                        JSONArray cast = new JSONArray(response);
                        for (int i = 0; i < cast.length(); i++) {
                            JSONObject actor = cast.getJSONObject(i);
                            joy_list.add(actor.getDouble("joy"));
                            disgust_list.add(actor.getDouble("disgust"));
                            anger_list.add(actor.getDouble("anger"));
                            surprise_list.add(actor.getDouble("surprise"));
                            fear_list.add(actor.getDouble("fear"));
                            sadness_list.add(actor.getDouble("sadness"));
                        }
                        context.populateGraph(anger_list, disgust_list, fear_list, joy_list, sadness_list, sadness_list);
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;


            }
        }.execute();

    }

    public void getDateWiseEmotions(final ReportingDailyChartFragment context, final int user_id, final String date) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                String response = "";
                try {
                    URL url = new URL(BASE_URL + SLASH + EMOTIONS_DATE_WISE);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("user_id=" + user_id + "&type=d&date=" + date);
                    writer.flush();
                    writer.close();

                    os.close();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }

//                        JSONObject temp = new JSONObject(response);
//                        ArrayList<Double> joy_list = new ArrayList<>();
//                        ArrayList<Double> fear_list = new ArrayList<>();
//                        ArrayList<Double> surprise_list = new ArrayList<>();
//                        ArrayList<Double> disgust_list = new ArrayList<>();
//                        ArrayList<Double> anger_list = new ArrayList<>();
//                        ArrayList<Double> sadness_list = new ArrayList<>();

                        double anger, disgust, fear, joy, sadness, surprise;
                        anger = disgust = fear = joy = sadness = surprise = 0.0;
                        JSONArray cast = new JSONArray(response);
                        int i;
                        for (i = 0; i < cast.length(); i++) {
                            JSONObject actor = cast.getJSONObject(i);
                            joy += actor.getDouble("joy");
                            disgust += actor.getDouble("disgust");
                            anger += actor.getDouble("anger");
                            surprise += actor.getDouble("surprise");
                            fear += actor.getDouble("fear");
                            sadness += actor.getDouble("sadness");
                        }
                        double total = anger + disgust + joy + fear + sadness + surprise;
                        if (total == 0.0) {
                            context.populatePieChart(anger, disgust, fear, joy, sadness, surprise);
                            return null;
                        }


                        anger = (anger * 360) / total;
                        disgust = (disgust * 360) / total;
                        fear = (fear * 360) / total;
                        joy = (joy * 360) / total;
                        sadness = (sadness * 360) / total;
                        surprise = (surprise * 360) / total;

                        context.populatePieChart(anger, disgust, fear, joy, sadness, surprise);
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;


            }
        }.execute();

    }


    public void getMonthWiseEmotions(final ReportingMonthlyChartFragment context, final int user_id, final String date) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                String response = "";
                try {
                    URL url = new URL(BASE_URL + SLASH + EMOTIONS_DATE_WISE);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("user_id=" + user_id + "&type=m&date=" + date);
                    writer.flush();
                    writer.close();

                    os.close();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }

//                        JSONObject temp = new JSONObject(response);
//                        ArrayList<Double> joy_list = new ArrayList<>();
//                        ArrayList<Double> fear_list = new ArrayList<>();
//                        ArrayList<Double> surprise_list = new ArrayList<>();
//                        ArrayList<Double> disgust_list = new ArrayList<>();
//                        ArrayList<Double> anger_list = new ArrayList<>();
//                        ArrayList<Double> sadness_list = new ArrayList<>();

                        double anger, disgust, fear, joy, sadness, surprise;
                        anger = disgust = fear = joy = sadness = surprise = 0.0;
                        JSONArray cast = new JSONArray(response);
                        int i;
                        for (i = 0; i < cast.length(); i++) {
                            JSONObject actor = cast.getJSONObject(i);
                            joy += actor.getDouble("joy");
                            disgust += actor.getDouble("disgust");
                            anger += actor.getDouble("anger");
                            surprise += actor.getDouble("surprise");
                            fear += actor.getDouble("fear");
                            sadness += actor.getDouble("sadness");
                        }
                        double total = anger + disgust + joy + fear + sadness + surprise;
                        if (total == 0.0) {
                            context.populatePieChart(anger, disgust, fear, joy, sadness, surprise);
                            return null;
                        }


                        anger = (anger * 360) / total;
                        disgust = (disgust * 360) / total;
                        fear = (fear * 360) / total;
                        joy = (joy * 360) / total;
                        sadness = (sadness * 360) / total;
                        surprise = (surprise * 360) / total;

                        context.populatePieChart(anger, disgust, fear, joy, sadness, surprise);
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;


            }
        }.execute();

    }

    public void getWeekWiseEmotions(final ReportingWeeklyChartFragment context, final int user_id, final String start_date) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                String response = "";
                try {
                    URL url = new URL(BASE_URL + SLASH + EMOTIONS_DATE_WISE);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("user_id=" + user_id + "&type=w&start_date=" + start_date);
                    writer.flush();
                    writer.close();

                    os.close();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }

                        double anger, disgust, fear, joy, sadness, surprise;
                        anger = disgust = fear = joy = sadness = surprise = 0.0;
                        JSONArray cast = new JSONArray(response);
                        int i;
                        for (i = 0; i < cast.length(); i++) {
                            JSONObject actor = cast.getJSONObject(i);
                            joy += actor.getDouble("joy");
                            disgust += actor.getDouble("disgust");
                            anger += actor.getDouble("anger");
                            surprise += actor.getDouble("surprise");
                            fear += actor.getDouble("fear");
                            sadness += actor.getDouble("sadness");
                        }
                        double total = anger + disgust + joy + fear + sadness + surprise;
                        if (total == 0.0) {
                            context.populatePieChart(anger, disgust, fear, joy, sadness, surprise);
                            return null;
                        }


                        anger = (anger * 360) / total;
                        disgust = (disgust * 360) / total;
                        fear = (fear * 360) / total;
                        joy = (joy * 360) / total;
                        sadness = (sadness * 360) / total;
                        surprise = (surprise * 360) / total;

                        context.populatePieChart(anger, disgust, fear, joy, sadness, surprise);
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;


            }
        }.execute();

    }


    public void getAllUserHobbies(final Context context, final int userId)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                RequestQueue queue = Volley.newRequestQueue(context);
                // "http://10.0.2.2:8000/get_user_hobbies/27/"
                String url =BASE_URL + SLASH + GET_USER_HOBBIES + SLASH + userId + SLASH;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                String reply=response;
                                Log.d("Response", reply);
//                                DatabaseFilter.deleteAllUserHobbies(context);
                                ArrayList<Integer> hobby_list = new ArrayList<>();
                                try {
                                    JSONArray json = new JSONArray(response);
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject temp_object = json.getJSONObject(i);
                                        UserHobby hobby = new UserHobby();
                                        hobby.setHobby_id(temp_object.getInt("hobby_id"));
                                        hobby.setUser_id(userId);
                                        hobby.setValid(0);

                                        hobby_list.add(hobby.getHobby_id());
                                        DatabaseFilter.insertUserHobby(context, hobby);
                                    }
//                                    DatabaseFilter.setSharedPreference_Loaded(context);
                                    DatabaseFilter.setSharedPreference_UserHobbies(context, hobby_list);
                                }catch (Exception ex)
                                {
                                    ex.getMessage();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(stringRequest);
                return null;
            }
        }.execute();
    }


    public void getAllHobbies(final Context context)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                RequestQueue queue = Volley.newRequestQueue(context);
                // "http://10.0.2.2:8000/get_user_hobbies/27/"
                String url =BASE_URL + SLASH + GET_HOBBIES + SLASH;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                String reply=response;
                                Log.d("Response", reply);

                                ArrayList<String> hobbies = new ArrayList<String>();
                                DatabaseFilter.deleteAllHobbies(context);
                                try {
                                    JSONArray json = new JSONArray(response);
                                    for(int i=0; i<json.length();i++)
                                    {
                                        JSONObject temp_object = json.getJSONObject(i);
                                        Hobby hobby = new Hobby();
                                        hobby.setId(temp_object.getInt("id"));
                                        hobby.setTitle(temp_object.getString("title"));
                                        hobby.setDescription(temp_object.getString("description"));
                                        hobbies.add(hobby.getTitle());
                                        DatabaseFilter.insertHobby(context, hobby);
                                    }
                                    if(hobbies.size()>0)
                                        DatabaseFilter.setSharedPreference_Hobbies(context, hobbies);
                                }catch (Exception ex)
                                {
                                    ex.getMessage();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(stringRequest);
                return null;
            }
        }.execute();
    }


    public void forgotPassword(final String email)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String response = "";
                try {
                    URL url =new URL(BASE_URL + SLASH + FORGOT_PASSWORD);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));


                    writer.write("email=" + email);
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();
                    Log.d("RESPONSE_CODE", String.valueOf(responseCode));
                    if (responseCode == HttpsURLConnection.HTTP_OK)
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                        DatabaseFilter.Recovery_Token = response.split(" ")[0].replace("\"", "");
                        DatabaseFilter.Recovery_Phone = response.split(" ")[1].replace("\"", "");
                        //recovery code has been sent to the given email
                    }
                    else if (responseCode ==111)
                    {
                        //No user with given emalk
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();

    }

}
