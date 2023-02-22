package com.can.tv.testlibrary;

import android.content.Context;
import android.widget.Toast;

public class TostMessage {

    public static void s(Context c, String message){

        Toast.makeText(c,message,Toast.LENGTH_SHORT).show();

    }
}
