package Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Amjad on 29-Dec-17.
 */
@Entity(tableName = "ActivityHistory",
foreignKeys = {@ForeignKey(entity = User.class,
parentColumns = "user_id",
childColumns = "user_id"),
@ForeignKey(entity = Activity.class,
parentColumns = "activity_id",
childColumns = "activity_type")})
public class ActivityHistory {
    public static final int APPROVED = 1;
    public static final int NOT_APPROVED = 0;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activity_id")
    private int activity_id;

    @ColumnInfo(name = "activity_type")
    private int activityType;

    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "starting_time")
    private long startingTime;

    @ColumnInfo(name = "ending_time")
    private long endTime;

    @ColumnInfo(name = "user_feedback")
    private int user_feedback;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name= "activity_valid")
    private int valid;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getUser_feedback() {
        return user_feedback;
    }

    public void setUser_feedback(int user_feedback) {
        this.user_feedback = user_feedback;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
