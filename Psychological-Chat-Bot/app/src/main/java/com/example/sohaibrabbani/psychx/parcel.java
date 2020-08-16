
package com.example.sohaibrabbani.psychx;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class parcel implements Serializable {

    private static final long serialVersionUID = 1L;
    public ChatActivity obj=null;

    public ChatActivity getChatActivity()
    {
        return obj;
    }
    void setCharActivity(ChatActivity Obj)
    {
        obj=Obj;
    }
}