package Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Amjad on 28-Dec-17.
 */

@Entity(tableName = "user", indices = {@Index(value="email", unique = true)})
public class User {

    public static final int MALE = 1;
    public static final int FEMALE = 0;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="user_id")
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    //1 for male and 0 for female
    @ColumnInfo(name = "gender")
    private char gender;

    @ColumnInfo(name = "dob")
    private String dob;

    @ColumnInfo(name= "email")
    private String email;

    @ColumnInfo(name= "password")
    private String password;

    @ColumnInfo(name= "phone")
    private String phone;

    @ColumnInfo(name= "user_valid")
    private int valid;

    @ColumnInfo(name="profile_picture")
    private String image;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
