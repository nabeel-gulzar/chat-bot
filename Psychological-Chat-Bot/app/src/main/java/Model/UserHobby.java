package Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Amjad on 29-Dec-17.
 */
@Entity(tableName ="UserHobby", primaryKeys = {"hobby_id", "user_id"},
        foreignKeys = @ForeignKey(onDelete = CASCADE, entity = User.class,
        parentColumns = "user_id",
        childColumns = "user_id"))
public class UserHobby
{

    @ColumnInfo(name="hobby_id")
    private int hobby_id;

    @ColumnInfo(name="user_id")
    private int user_id;

    @ColumnInfo(name= "user_hobby_valid")
    private int valid;

    public UserHobby() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(int hobby_id) {
        this.hobby_id = hobby_id;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
