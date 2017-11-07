package com.example.david_k.oneStopClick.Helper;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;

public class Helper {
    public static int resourceIdWith(Context context, String imageName) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(imageName, "drawable", context.getPackageName());
        return resourceId;
    }

    public static boolean editTextIsEmptyOrNull(EditText editText){
        String text = editText.getText().toString();

        if (stringInputIsEmptyOrNull(text)){
            return true;
        }

        return false;
    }


    public static boolean stringInputIsEmptyOrNull(String input) {
        if (input.equals(null) || input.equals("")) {
            return true;
        }

        return false;
    }
}
