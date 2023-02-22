package com.can.tv.testlibrary;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("error")
    public boolean error;
    @SerializedName("message")
    public String message;
    @SerializedName("ad")
    public Ad ad;
}

