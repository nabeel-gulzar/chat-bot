package Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Amjad on 29-Dec-17.
 */
//this is read only table and user can not inset into in so no need of valid bit
@Entity(tableName ="Hobby")
public class Hobby
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="hobby_id")
    private int id;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
