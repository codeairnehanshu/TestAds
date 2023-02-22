package com.can.tv.testlibrary;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.preference.PowerPreference;

public class Constant {

//    public static final String BASE_URL = "http://192.168.29.202/can003";
    public static final String BASE_URL = "https://allapps.codeairtech.com";
    public static final String SERVER_URL = BASE_URL + "/v1/app/";

    public static final String GET_AD_DATA = SERVER_URL + "get_ads_data";

    public static final String ADS_PRIORITY = "ads_priority";
    public static final String SERVER_INTER_COUNT = "server_inter_count";
    public static final String APP_INTER_COUNT = "app_inter_count";
    public static final String SERVER_BACK_COUNT = "server_back_count";
    public static final String APP_BACK_COUNT = "app_back_count";
    public static final String IS_SPLASH_APP_OPEN = "is_splash_app_open";
    public static final String IS_BACK_ADS = "is_back_ads";
    public static final String POLICY_LINK = "policy_link";
    public static final String IS_BANNER_AD = "is_banner_ad";
    public static final String IS_MINI_NATIVE_AD = "is_mini_native_ad";
    public static final String IS_LARGE_NATIVE_AD = "is_large_native_ad";
    public static final String ADS_ON_OFF = "ads_on_off";
    public static final String ADMOB_INTER_ID = "admob_inter_id";
    public static final String INTER_LOADER = "inter_loader";
    public static final String INTER_LOADER_SECONDS = "inter_loader_seconds";
    public static final String ADMOB_BANNER_ID = "admob_banner_id";
    public static final String ADMOB_NATIVE_ID = "admob_native_id";
    public static final String ADMOB_APP_OPEN_ID = "admob_app_open_id";
    public static final String IS_REWARDED_AD = "is_rewarded_ad";
    public static final String ADMOB_REWARDED_ID = "admob_rewarded_id";
    public static final String FB_INTER_ID = "fb_inter_id";
    public static final String FB_BANNER_ID = "fb_banner_id";
    public static final String FB_NATIVE_ID = "fb_native_id";
    public static final String NATIVE_BG_COLOR = "native_background_color";
    public static final String NATIVE_HEAD_TEXT_COLOR = "native_body_headline_text_color";
    public static final String CUSTOM_ADS_EXIT = "exit_inter_on_off";
    public static final String APP_VERSION = "app_version";
    public static final String IS_FORCE = "is_force";
    public static final String QUREKA_ON_OFF = "qureka_on_off";
    public static final String QUREKA_AD_LINK = "qureka_ad_link";
    public static final String QUREKA_PRIORITY = "qureka_priority";
    public static final String WEBIFY_AD_LINK = "webify_ad_link";




    public static String adsLog = "adsLog";
    public static String errorLog = "errorLog";
    public static String NativeDefultColor="#266744FD";

    public static String ADS_NORMAL = "ADS_NORMAL";
    public static String ADS_TINY = "ADS_TINY";
    public static String FULL_SCREEN = "FullScreenOnOff";
    public static Integer[] adsWebifyInters = new Integer[]{R.drawable.inter2, R.drawable.inter4, R.drawable.inter6};
    public static Integer[] adsQurekaInters = new Integer[]{R.drawable.qureka_inter1, R.drawable.qureka_inter2, R.drawable.qureka_inter3, R.drawable.qureka_inter4, R.drawable.qureka_inter5};
    public static Integer[] adsQurekaGifInters = new Integer[]{R.drawable.qureka_round1, R.drawable.qureka_round2, R.drawable.qureka_round3, R.drawable.qureka_round4, R.drawable.qureka_round5};

    public static String mList="mList";

    public static void gotoAds(Context context) {
        try {
            String packageName = "com.android.chrome";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorMain));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(packageName);
            int prio = PowerPreference.getDefaultFile().getInt(Constant.QUREKA_PRIORITY);
            customTabsIntent.launchUrl(context, Uri.parse(prio == 0 ? PowerPreference.getDefaultFile().getString(Constant.QUREKA_AD_LINK) : PowerPreference.getDefaultFile().getString(Constant.WEBIFY_AD_LINK)));
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

//    public static void gotoTerms(Context context) {
//        try {
//            String packageName = "com.android.chrome";
//            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//            builder.setToolbarColor(ContextCompat.getColor(context, R.color.app));
//            CustomTabsIntent customTabsIntent = builder.build();
//            customTabsIntent.intent.setPackage(packageName);
//            customTabsIntent.launchUrl(context, Uri.parse(PowerPreference.getDefaultFile().getString(Constant.POLICY_LINK, "")));
//        } catch (Exception e) {
//            Log.e(errorLog, e.toString());
//        }
//    }

}
