package Model;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sohaibrabbani.psychx.ChatActivity;
import com.example.sohaibrabbani.psychx.PsychxWebService;
import com.example.sohaibrabbani.psychx.Message;
import com.example.sohaibrabbani.psychx.R;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Amjad on 29-Dec-17.
 */

public class DatabaseFilter {

    public static final int VALID = 1;
    public static final int INVALID = 0;
    public static String Recovery_Token = "";
    public static String Recovery_Phone = "";


    public static String encryptPassword(String password)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);

        }catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static User signUpUser(User user, String password)
    {
        //send user and password to sever and verify the user
        return user;
    }

    public static boolean verifyUser(String email, String password) {
        ///if user is activated and email and password are true
        return true;
    }

    public static void deleteAllUsers(final Context context)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().deleteAllUsers();
                return null;
            }
        }.execute();
    }

    public static void insertUser(final Context context, final User user)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().deleteAllUsers();
                db.daoAccess().insertUser(user);
//                DatabaseFilter.setSharedPreference_Loaded(context);
                return null;
            }
        }.execute();
    }

    public static void loadAllMessages(final ChatActivity context, final List<Message> messageData)
    {
        new AsyncTask<Void, Void, Void>(){

            ProgressDialog dialog = new ProgressDialog(context);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("Loading chat, please wait.");
                dialog.setCancelable(false);
                dialog.show();

            }


            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                List<ChatHistory> temp_chats = db.daoAccess().getChatHistory();
                for (ChatHistory chat : temp_chats)
                {
                    if(!chat.getMessage().isEmpty())
                        messageData.add(new Message(chat.getMessage(), chat.getMessage(), false));
                    if(!chat.getResponse().isEmpty())
                        messageData.add(new Message(chat.getMessage(), chat.getMessage(), true));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }.execute();

    }


    public static void insertHobby(final Context context, final Hobby hobby)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().insertHobby(hobby);
                return null;
            }
        }.execute();
    }

    public static void insertChatMessage(final Context context, final ChatHistory chatMessage) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().insertChatHistory(chatMessage);
                return null;
            }
        }.execute();
    }

    public static void deleteAllHobbies(final Context context)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().deleteAllHobbies();
                return null;
            }
        }.execute();
    }


    public static void deleteAllUserHobbies(final Context context)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().deleteAllUserHobbies();
//                List<UserHobby> userHobbyList = new ArrayList<>();
//                new PsychxWebService().insertUserHobbyList(userHobbyList);
                return null;
            }
        }.execute();
    }


    public static void getAllHobbies(final Context context, final ArrayList<String> content)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                List<Hobby> temp_list = db.daoAccess().getHobbies();
                for (Hobby h:temp_list)
                {
                    content.add(h.getTitle());
                }
                return null;
            }
        }.execute();
    }


    public static void getAllUserHobbies(final Context context, final List<String> user_hobbies_list, final int user_id)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                List<UserHobby> user_hobbies = db.daoAccess().getUserHobbies(user_id);
                for(UserHobby userHobby:user_hobbies)
                {
                    String hobbyTitle = db.daoAccess().getHobbyTitleById(userHobby.getHobby_id());
                    user_hobbies_list.add(hobbyTitle);
                }
                return null;
            }
        }.execute();
    }

    public static void insertUserHobby(final Context context, final String title, final int user_id)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                int hobby_id = db.daoAccess().getHobbyIdByTitle(title);
                UserHobby hobby = new UserHobby();
                hobby.setHobby_id(hobby_id);
                hobby.setUser_id(user_id);
                hobby.setValid(0);
                db.daoAccess().insertUserHobby(hobby);
                return null;
            }
        }.execute();
    }

    public static void insertUserHobby(final Context context, final UserHobby hobby)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().insertUserHobby(hobby);
                return null;
            }
        }.execute();
    }

    public static void insertUserHobbiesList(final Context context, ArrayList<String> hobby_list, final int user_id)
    {
        final List<String> hobbyList = new ArrayList<>(new HashSet<>(hobby_list));
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().deleteAllUserHobbies();
                List<UserHobby> userHobbyList = new ArrayList<>();
                for(String title: hobbyList) {
                    int hobby_id = db.daoAccess().getHobbyIdByTitle(title);
                    User user = db.daoAccess().getUserById(user_id);
                    Log.d("Database Filter", user.getEmail());
                    UserHobby hobby = new UserHobby();
                    hobby.setHobby_id(hobby_id);
                    hobby.setUser_id(user_id);
                    hobby.setValid(0);
                    db.daoAccess().insertUserHobby(hobby);
                    userHobbyList.add(hobby);
//                    new PsychxWebService().insertUserHobby(hobby);
                }
                new PsychxWebService().insertUserHobbyList(userHobbyList);
                return null;
            }
        }.execute();
    }


    public static void updateUser(final Context context, final User user)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                db.daoAccess().updateUser(user);
                return null;
            }
        }.execute();
    }

    public static void setSharedPreference(Context context, User user)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getResources().getString(R.string.sp_first_name), user.getFirstName());
        editor.putString(context.getResources().getString(R.string.sp_last_name), user.getLastName());
        editor.putString(context.getResources().getString(R.string.sp_email), user.getEmail());
        editor.putString(context.getResources().getString(R.string.sp_phone), user.getPhone());
        editor.putString(context.getResources().getString(R.string.sp_photo), user.getImage());
        editor.putString(context.getResources().getString(R.string.sp_gender), String.valueOf(user.getGender()));
        editor.putInt(context.getResources().getString(R.string.sp_id), user.getUid());
        editor.putString(context.getResources().getString(R.string.sp_dob), user.getDob());
        editor.putString(context.getResources().getString(R.string.sp_image), user.getImage());
        editor.putInt(context.getResources().getString(R.string.sp_valid), user.getValid());
        editor.putBoolean(context.getResources().getString(R.string.sp_logged_in), true);


        editor.commit();

    }

    public static void setSharedPreference_UserHobbies(final Context context, final ArrayList<Integer> hobbies)
    {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "PsychX").build();
                SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                StringBuilder current_user_hobby = new StringBuilder("");

                int i = 0;
                for(int userHobbyId: hobbies)
                {
                    String hobbyTitle = db.daoAccess().getHobbyTitleById(userHobbyId);
                    current_user_hobby.append( ((i++==0)?"":" ") + hobbyTitle);
                }
                editor.putString(context.getResources().getString(R.string.sp_user_hobby), current_user_hobby.toString().trim());
                editor.commit();

                return null;
            }
        }.execute();
    }


    public static void setSharedPreference_UserHobbiesWithTitle(final Context context, final ArrayList<String> hobbies)
    {
                SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                StringBuilder current_user_hobby = new StringBuilder("");

                int i = 0;
                for(String userHobbyId: hobbies)
                {
                    String hobbyTitle = userHobbyId;
                    current_user_hobby.append( ((i++==0)?"":" ") + hobbyTitle);
                }

                editor.putString(context.getResources().getString(R.string.sp_user_hobby), current_user_hobby.toString().trim());
                editor.apply();
    }


    public static ArrayList<String> getSharedPreference_UserHobbies(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        String temp_hobby = sharedPref.getString(context.getResources().getString(R.string.sp_user_hobby), "");
        if (temp_hobby.isEmpty())
            return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(temp_hobby.trim().split(" ")));
    }

    public static void setSharedPreference_Hobbies(Context context, ArrayList<String> hobbies) {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        StringBuilder current_hobby = new StringBuilder("");
        for(int i = 0; i<hobbies.size(); i++) {
            current_hobby.append(((i==0)?"":" ") + hobbies.get(i));
        }
        editor.putString(context.getResources().getString(R.string.sp_hobby), current_hobby.toString());
        editor.commit();
    }


    public static void setSharedPreferenceSuggestionEmails(Context context, String email) {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String prev_emails = sharedPref.getString(context.getResources().getString(R.string.sp_suggestion_emails), "");
        if (prev_emails.contains(email))
            return;
        if (prev_emails.isEmpty())
            editor.putString(context.getResources().getString(R.string.sp_suggestion_emails), email);
        else
            editor.putString(context.getResources().getString(R.string.sp_suggestion_emails), prev_emails + " " + email);

        editor.apply();
    }

    public static String[] getSharedPreferenceSuggestionEmails(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        String prev_emails = sharedPref.getString(context.getResources().getString(R.string.sp_suggestion_emails), "");
        return prev_emails.split(" ");
    }

    public static ArrayList<String> getSharedPreference_Hobbies(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        String temp_hobby = sharedPref.getString(context.getResources().getString(R.string.sp_hobby), "");
        return new ArrayList<>(Arrays.asList(temp_hobby.trim().split(" ")));
    }


    public static void destroySession(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        String emails = sharedPref.getString(context.getResources().getString(R.string.sp_suggestion_emails), "");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        if (!emails.isEmpty())
            editor.putString(context.getResources().getString(R.string.sp_suggestion_emails), emails);
        editor.commit();

    }

    public static User getSharedPreference(Context context)
    {
        User user = new User();

        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        int id = sharedPref.getInt(context.getResources().getString(R.string.sp_id), 0);
        String firstName = sharedPref.getString(context.getResources().getString(R.string.sp_first_name), "");
        String lastName = sharedPref.getString(context.getResources().getString(R.string.sp_last_name), "");
        String email = sharedPref.getString(context.getResources().getString(R.string.sp_email), "");
        String phone = sharedPref.getString(context.getResources().getString(R.string.sp_phone), "");
        String photo = sharedPref.getString(context.getResources().getString(R.string.sp_photo), "");
        char gender = sharedPref.getString(context.getResources().getString(R.string.sp_gender), "o").charAt(0);
        String dob = sharedPref.getString(context.getResources().getString(R.string.sp_dob), "1990-01-03");

        user.setUid(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setGender(gender);
        user.setDob(dob);
        user.setImage(photo);

        return user;
    }

    public static boolean isLoggedin(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("PsychX", Context.MODE_PRIVATE);
        boolean logged_in = sharedPref.getBoolean(context.getResources().getString(R.string.sp_logged_in), false);
        return logged_in;
    }
}


