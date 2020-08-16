package Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "Activity")
public class Activity
{
    public static final int ACTIVITY_SLEEP = 0;
    public static final int ACTIVITY_IDLE = 1;
    public static final int ACTIVITY_AWAKE = 2;
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="activity_id")
    private int activityType;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="description")
    private String description;


    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
