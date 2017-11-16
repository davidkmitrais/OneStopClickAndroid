package com.example.david_k.oneStopClick.Helper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by David_K on 16/11/2017.
 */

public interface OnGetDataListener {
    public void onStart();
    public void onSuccess(DataSnapshot data);
    public void onFailed(DatabaseError databaseError);
}
