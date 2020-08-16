//package Model;
//
//import android.arch.persistence.room.ColumnInfo;
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.ForeignKey;
//import android.arch.persistence.room.PrimaryKey;
//
///**
// * Created by Amjad on 29-Dec-17.
// */
//@Entity(tableName = "MoodHistory",
//        foreignKeys = @ForeignKey(entity = User.class,
//        parentColumns = "user_id",
//        childColumns = "user_id"))
//public class MoodHistory {
//
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name="mood_id")
//    private int mood_id;
//
//    @ColumnInfo(name="aggression_level")
//    private long aggressionLevel;
//
//    @ColumnInfo(name="depression_level")
//    private long depression_level;
//
//    @ColumnInfo(name="mood_inclination_level")
//    private long moodInclination;
//
//    @ColumnInfo(name="update_time")
//    private long update_time;
//
//    @ColumnInfo(name="user_id")
//    private int user_id;
//
//
//    public int getMood_id() {
//        return mood_id;
//    }
//
//    public void setMood_id(int mood_id) {
//        this.mood_id = mood_id;
//    }
//
//    public long getAggressionLevel() {
//        return aggressionLevel;
//    }
//
//    public void setAggressionLevel(long aggressionLevel) {
//        this.aggressionLevel = aggressionLevel;
//    }
//
//    public long getDepression_level() {
//        return depression_level;
//    }
//
//    public void setDepression_level(long depression_level) {
//        this.depression_level = depression_level;
//    }
//
//    public long getMoodInclination() {
//        return moodInclination;
//    }
//
//    public void setMoodInclination(long moodInclination) {
//        this.moodInclination = moodInclination;
//    }
//
//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
//
//    public long getUpdate_time() {
//        return update_time;
//    }
//
//    public void setUpdate_time(long update_time) {
//        this.update_time = update_time;
//    }
//}
