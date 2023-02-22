package com.can.tv.testlibrary;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ad implements Serializable {

    @SerializedName("ads_priority")
    public int ads_priority;
    @SerializedName("server_inter_count")
    public int server_inter_count;
    @SerializedName("server_back_count")
    public int server_back_count;
    @SerializedName("is_splash_app_open")
    public boolean is_splash_app_open;
    @SerializedName("is_back_ads")
    public boolean is_back_ads;
    @SerializedName("policy_link")
    public String policy_link;
    @SerializedName("is_mini_native_ad")
    public boolean is_mini_native_ad;
    @SerializedName("is_banner_ads")
    public boolean is_banner_ads;
    @SerializedName("is_large_native_ad")
    public boolean is_large_native_ad;
    @SerializedName("ads_on_off")
    public boolean ads_on_off;
    @SerializedName("admob_inter_id")
    public String admob_inter_id;
    @SerializedName("inter_loder")
    public boolean inter_loder;
    @SerializedName("inter_loder_second")
    public int inter_loder_second;
    @SerializedName("admob_banner_id")
    public String admob_banner_id;
    @SerializedName("admob_native_id")
    public String admob_native_id;
    @SerializedName("admob_app_open_id")
    public String admob_app_open_id;
    @SerializedName("is_rewarded_on")
    public boolean is_rewared_on;
    @SerializedName("admob_rewarded_id")
    public String admob_rewarded_id;
    @SerializedName("fb_inter_id")
    public String fb_inter_id;
    @SerializedName("fb_banner_id")
    public String fb_banner_id;
    @SerializedName("fb_native_id")
    public String fb_native_id;
    @SerializedName("native_background_color")
    public String native_background_color;
    @SerializedName("native_body_headline_text_color")
    public String native_body_headline_text_color;
    @SerializedName("full_screen_on_off")
    public boolean full_screen_on_off;
    @SerializedName("exit_inter_on_off")
    public boolean exit_inter_on_off;
    @SerializedName("app_version")
    public String app_version;
    @SerializedName("is_force")
    public boolean is_force;
    @SerializedName("qureka_on_off")
    public boolean qureka_on_off;
    @SerializedName("qureka_ad_link")
    public String qureka_ad_link;
    @SerializedName("qureka_priority")
    public int qureka_priority;
    @SerializedName("webify_link")
    public String webify_link;

}
