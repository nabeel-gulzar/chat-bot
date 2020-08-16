package Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Amjad on 29-Dec-17.
 */

@Entity(tableName = "ChatHistory", foreignKeys = @ForeignKey(onDelete = CASCADE, entity = User.class,
        parentColumns = "user_id",
        childColumns = "sender_id"))
public class ChatHistory
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="message_id")
    private int message_id;

    @ColumnInfo(name="message")
    private String message;

    @ColumnInfo(name= "response")
    private String response;

    @ColumnInfo(name="message_time")
    private String message_time;

    @ColumnInfo(name = "sender_id")
    private int sender_id;

    @ColumnInfo(name= "message_valid")
    private int valid;

    @ColumnInfo(name = "anger")
    private float anger;

    @ColumnInfo(name = "disgust")
    private float disgust;

    @ColumnInfo(name = "sadness")
    private float sadness;

    @ColumnInfo(name = "surprise")
    private float surprise;

    @ColumnInfo(name = "fear")
    private float fear;

    @ColumnInfo(name = "joy")
    private float joy;


    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public float getJoy() {
        return joy;
    }

    public void setJoy(float joy) {
        this.joy = joy;
    }

    public float getFear() {
        return fear;
    }

    public void setFear(float fear) {
        this.fear = fear;
    }

    public float getSurprise() {
        return surprise;
    }

    public void setSurprise(float surprise) {
        this.surprise = surprise;
    }

    public float getSadness() {
        return sadness;
    }

    public void setSadness(float sadness) {
        this.sadness = sadness;
    }

    public float getDisgust() {
        return disgust;
    }

    public void setDisgust(float disgust) {
        this.disgust = disgust;
    }

    public float getAnger() {
        return anger;
    }

    public void setAnger(float anger) {
        this.anger = anger;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }
}
