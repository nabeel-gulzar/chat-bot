package Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Amjad on 28-Dec-17.
 */

@Dao
public interface DaoAccess {

//    @Insert
//    void insertMultipleUsers(User... users);

    //User DAO functions
    @Insert                                              //Insert a single user into data base
    void insertUser(User user);
    @Query("SELECT * FROM user")                         //Get all users
    List<User> getAllUsers();
    @Query("SELECT * FROM user where user_id=:userId")                         //Get user by Id
    User getUserById(int userId);
    @Query("SELECT * FROM user where email=:userEmail")                         //Get user by Email
    User getUserByEmail(String userEmail);

    @Query("SELECT user_id from user where email=:email")//get userId by giving email
    int getUserIdFromEmail(String email);
    @Query("SELECT user_id from user where phone=:phone")//get userId by giving phone
    int getUserIdFromPhone(String phone);
    @Query("DELETE FROM user")                           //delete all users
    void deleteAllUsers();
    @Query("DELETE FROM user where user_id=:userId")    //delete a single user by giving id
    void deleteFromUser(int userId);
    @Update                                              //Update a single record
    void updateUser(User user);


    //Hobby DAO functions
    @Insert                                              //Insert a single hobby
    void insertHobby(Hobby hobby);
    @Query("SELECT * from hobby")                        //Get all hobbies
    List<Hobby> getHobbies();
    @Query("SELECT hobby_id from Hobby where title=:title") //Get hobby id by giving title
    int getHobbyIdByTitle(String title);
    @Query("SELECT * from Hobby where hobby_id=:hobbyId") //Get hobby record by giving id
    Hobby getHobbyById(int hobbyId);
    @Query("DELETE FROM Hobby")                           //delete all users
    void deleteAllHobbies();
    @Query("SELECT title from Hobby where hobby_id=:id") //Get hobby id by giving title
    String getHobbyTitleById(int id);



    //UserHobby DAO functions
    @Insert                                               //Insert single UserHobby
    void insertUserHobby(UserHobby userHobby);
    @Query("SELECT * FROM UserHobby")                     //Select all hobbies
    List<UserHobby> getUserHobbies();
    @Query("SELECT * FROM UserHobby where user_id=:id")                     //Select all hobbies
    List<UserHobby> getUserHobbies(int id);
    @Query("DELETE FROM UserHobby")    //delete single hobby
    void deleteAllUserHobbies();


    //ChatHistory DAO functions
    @Insert                                               //Insert a single message into database
    void insertChatHistory(ChatHistory chatHistory);
    @Query("SELECT * FROM ChatHistory")                   //get all chat from database
    List<ChatHistory> getChatHistory();
    @Query("SELECT * FROM ChatHistory where message_time between :from and :to")    //get chat between a specific time (from and to)
    List<ChatHistory> getChatBetween(long from, long to);


    //MoodHistory DAO functions
//    @Insert                                              //Insert mood into database
//    void insertMoodHistory(MoodHistory moodHistory);
//    @Query("SELECT * FROM MoodHistory")                  //get all records form Activity History table
//    List<MoodHistory> getMoodHistory();
//    @Query("SELECT * FROM MoodHistory where update_time between :from and :to")    //get Activities between a specific time (from and to)
//    List<MoodHistory> getMoodsBetween(long from, long to);


    //ActivityHistory DAO functions
    @Insert                                                         //Insert single activity
    void insertUserActivity(ActivityHistory activityHistory);
    @Query("SELECT * FROM ActivityHistory")                         //Get all user activities
    List<ActivityHistory> getUserActivities();
    @Query("SELECT * FROM ActivityHistory where starting_time between :from and :to")    //get user activities between a specific time (from and to)
    List<ActivityHistory> getUserActivitiesBetween(long from, long to);




    //Activity DAO functions
    @Insert                                                         //insert new activity
    void insertActivity(Activity activity);
    @Query("SELECT * FROM Activity")                                //get all activities
    List<Activity> getActivities();
    @Query("SELECT activity_id FROM Activity where title=:title")   //Get activity id by giving title
    int getActivityIdByTitle(String title);


}