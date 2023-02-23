package com.can.tv.testlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.gson.Gson;
import com.preference.PowerPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LargeNativeAds {


    String gNativeId;
    String fNativeId;
    int adsPriority, qurekaPriority;
    boolean adsOnOff, largeOnOff, qurekaOnOff;
    String bgColor, textColor;


    public LargeNativeAds(String gNativeId, String fNativeId, int adsPriority, int qurekaPriority, boolean adsOnOff, boolean largeOnOff, boolean qurekaOnOff, String bgColor, String textColor) {
        this.gNativeId = gNativeId;
        this.fNativeId = fNativeId;
        this.adsPriority = adsPriority;
        this.qurekaPriority = qurekaPriority;
        this.adsOnOff = adsOnOff;
        this.largeOnOff = largeOnOff;
        this.qurekaOnOff = qurekaOnOff;
        this.bgColor = bgColor;
        this.textColor = textColor;
    }


    private static ArrayList<NativeAd> gNativeAd = new ArrayList<>();
    private static ArrayList<com.facebook.ads.NativeAd> fbNativeAd = new ArrayList<>();
    private static String ad_type;

    public void loadNativeAds(Activity activity) {
        switch (adsPriority) {
            case 0:
                loadGoogleAds(activity);
                break;
            case 1:
                loadFbAds(activity);
                break;
        }
    }

    public void loadGoogleAds(Activity activity) {
//        final String nativeAdstr = PowerPreference.getDefaultFile().getString(Constant.ADMOB_NATIVE_ID);

        AdLoader.Builder builder = new AdLoader.Builder(activity, gNativeId);
        builder.forNativeAd(natives -> {

            gNativeAd.clear();
            gNativeAd.add(natives);
        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError errorCode) {
                Log.e(Constant.adsLog, "loadNativeAds failed" + errorCode.toString());
                gNativeAd.clear();

                switch (adsPriority) {
                    case 0:
                        loadFbAds(activity);
                        break;
                    case 1:
                        break;
                }

            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void loadFbAds(Activity activity) {
//        final String nativeAd = PowerPreference.getDefaultFile().getString(Constant.FB_NATIVE_ID);
        final com.facebook.ads.NativeAd fbnativeAd = new com.facebook.ads.NativeAd(activity, fNativeId);

        fbnativeAd.loadAd(fbnativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                switch (adsPriority) {
                    case 0:
                        break;
                    case 1:
                        loadGoogleAds(activity);
                        break;
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                fbNativeAd.clear();
                fbNativeAd.add((com.facebook.ads.NativeAd) ad);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }

    public FrameLayout getFrameLayout(Activity activity, Dialog dialog) {
        if (dialog != null) {
            return dialog.findViewById(R.id.nativeAd);
        } else {
            return activity.findViewById(R.id.nativeAd);
        }
    }


    public TextView getTextLayout(Activity activity, Dialog dialog) {
        if (dialog != null) {
            return dialog.findViewById(R.id.ad_space);
        } else {
            return activity.findViewById(R.id.ad_space);
        }
    }


/*    public void showNativeAds(Activity activity, Dialog dialog) {
        FrameLayout nativeAdLayout = getFrameLayout(activity, dialog);
        TextView adSpace = getTextLayout(activity, dialog);

        switch (adsPriority) {
            case 0:
                showGoogleAds(activity, dialog, nativeAdLayout, adSpace);
                break;
            case 1:
                showFbAds(activity, dialog, nativeAdLayout, adSpace);
                break;
        }
    }*/

    public void showNativeAds(Activity activity, Dialog dialog, FrameLayout nativeAdLayout, TextView adSpace) {
//        FrameLayout nativeAdLayout = getFrameLayout(activity, dialog);
//        TextView adSpace = getTextLayout(activity, dialog);

        switch (PowerPreference.getDefaultFile().getInt(Constant.ADS_PRIORITY, 0)) {
            case 0:
                showGoogleAds(activity, dialog, nativeAdLayout, adSpace);
                break;
            case 1:
                showFbAds(activity, dialog, nativeAdLayout, adSpace);
                break;
        }
    }


    public void showGoogleAds(Activity activity, Dialog dialog, FrameLayout nativeAdLayout, TextView adSpace) {

        LinearLayout adView = null;

        if (adsOnOff && largeOnOff) {

            if (gNativeAd.size() > 0) {

                if (dialog != null) {
                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.ads_native_dialog, null);
                } else {
                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.ads_native_normal, null);

                    CardView cardView = adView.findViewById(R.id.cvMain);
                    TextView ad_headline = adView.findViewById(R.id.ad_headline);
                    TextView ad_body = adView.findViewById(R.id.ad_body);

                    cardView.setCardBackgroundColor(Color.parseColor(bgColor));
                    ad_body.setTextColor(Color.parseColor(textColor));
                    ad_headline.setTextColor(Color.parseColor(textColor));

//                    if (PowerPreference.getDefaultFile().getBoolean(Constant.HomeNativeBackgroundColorOnOff, true)) {
//                        cardView.setCardBackgroundColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativeBackgroundColor, Constant.NativeDefultColor)));
//                        ad_body.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativebodyheadlineColor)));
//                        ad_headline.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativebodyheadlineColor)));
//                    } else {
//                        cardView.setCardBackgroundColor(Color.parseColor(Constant.NativeDefultColor));
//                    }
                }


                NativeAd lovalNative = gNativeAd.get(0);

                populateUnifiedNativeAdView(lovalNative, adView.findViewById(R.id.uadview));

                nativeAdLayout.removeAllViews();
                nativeAdLayout.addView(adView);

                adSpace.setVisibility(View.GONE);
                nativeAdLayout.setVisibility(View.VISIBLE);

                loadNativeAds(activity);

            } else {
                switch (adsPriority) {
                    case 0:
                        showFbAds(activity, dialog, nativeAdLayout, adSpace);
                        break;
                    case 1:
                        loadNativeAds(activity);
                        showQurekaAds(activity, dialog, adView, nativeAdLayout, adSpace);
                        break;
                }
            }
        } else {
            nativeAdLayout.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }

    public void showFbAds(Activity activity, Dialog dialog, FrameLayout nativeAdLayout, TextView adSpace) {

        LinearLayout adView = null;

        if (adsOnOff && largeOnOff) {

            if (fbNativeAd.size() > 0) {

                if (dialog != null) {
                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.ads_fb_dialog, null);
                } else {
                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.ads_fb_normal, null);


                    CardView cardView = adView.findViewById(R.id.cvMain);
                    TextView ad_headline = adView.findViewById(R.id.ad_headline);
                    TextView ad_body = adView.findViewById(R.id.ad_body);

                    cardView.setCardBackgroundColor(Color.parseColor(bgColor));
                    ad_body.setTextColor(Color.parseColor(textColor));
                    ad_headline.setTextColor(Color.parseColor(textColor));


//                    if (PowerPreference.getDefaultFile().getBoolean(Constant.HomeNativeBackgroundColorOnOff, true)) {
//                        cardView.setCardBackgroundColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativeBackgroundColor, Constant.NativeDefultColor)));
//                        ad_body.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativebodyheadlineColor)));
//                        ad_headline.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativebodyheadlineColor)));
//                    } else {
//                        cardView.setCardBackgroundColor(Color.parseColor(Constant.NativeDefultColor));
//                    }
                }

                com.facebook.ads.NativeAd lovalNative = fbNativeAd.get(0);

                inflateAd(lovalNative, adView.findViewById(R.id.uadview));

                nativeAdLayout.removeAllViews();
                nativeAdLayout.addView(adView);

                adSpace.setVisibility(View.GONE);
                nativeAdLayout.setVisibility(View.VISIBLE);

                loadNativeAds(activity);

            } else {
                switch (adsPriority) {
                    case 0:
                        loadNativeAds(activity);
                        showQurekaAds(activity, dialog, adView, nativeAdLayout, adSpace);
                        break;
                    case 1:
                        showGoogleAds(activity, dialog, nativeAdLayout, adSpace);
                        break;
                }
            }
        } else {
            nativeAdLayout.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }

    public void showQurekaAds(Activity activity, Dialog dialog, LinearLayout adView, FrameLayout nativeAdLayout, TextView adSpace) {
        if (qurekaOnOff) {

            if (dialog != null) {
                adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.qureka_native_dialog, null);
            } else {
                adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.qureka_native, null);

                CardView cardView = adView.findViewById(R.id.cvMain);
                TextView ad_headline = adView.findViewById(R.id.ad_headline);
                TextView ad_body = adView.findViewById(R.id.ad_body);

                cardView.setCardBackgroundColor(Color.parseColor(bgColor));
                ad_body.setTextColor(Color.parseColor(textColor));
                ad_headline.setTextColor(Color.parseColor(textColor));


//                if (PowerPreference.getDefaultFile().getBoolean(Constant.HomeNativeBackgroundColorOnOff, true)) {
//                    cardView.setCardBackgroundColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativeBackgroundColor, Constant.NativeDefultColor)));
//                    ad_body.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativebodyheadlineColor)));
//                    ad_headline.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.NativebodyheadlineColor)));
//                } else {
//                    cardView.setCardBackgroundColor(Color.parseColor(Constant.NativeDefultColor));
//                }
            }

            ImageView imageViewMain = adView.findViewById(R.id.qurekaAds1);
            ImageView imageViewBG = adView.findViewById(R.id.qurekaAds);
            ImageView imageViewGif = adView.findViewById(R.id.gif_inter_round);

//            int prio = PowerPreference.getDefaultFile().getInt(Constant.QUREKA_PRIORITY);
            if(qurekaPriority == 0){
                setQureka(activity, imageViewMain, imageViewBG, imageViewGif, Constant.ADS_NORMAL);
            }else{
                setWebify(activity, imageViewMain, imageViewBG, imageViewGif, Constant.ADS_NORMAL);
            }


            adView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constant.gotoAds(activity);
                }
            });

            nativeAdLayout.removeAllViews();
            nativeAdLayout.addView(adView);

            adSpace.setVisibility(View.GONE);
            nativeAdLayout.setVisibility(View.VISIBLE);

        }else{
            nativeAdLayout.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }


    public void setQureka(Activity activity, ImageView imageViewMain, ImageView imageViewBG, ImageView imageViewGif, String isSmall) {

        if (PowerPreference.getDefaultFile().getInt(isSmall, 0) >= 5) {
            PowerPreference.getDefaultFile().putInt(isSmall, 0);
            setQureka(activity, imageViewMain, imageViewBG, imageViewGif, isSmall);
        } else {
            if (imageViewBG != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsQurekaInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewBG);

            if (imageViewMain != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsQurekaInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewMain);

            if (imageViewGif != null && !activity.isFinishing())
                Glide.with(activity).asGif().load(Constant.adsQurekaGifInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewGif);


            int top = PowerPreference.getDefaultFile().getInt(isSmall, 0) + 1;
            PowerPreference.getDefaultFile().putInt(isSmall, top);
        }
    }

    public void setWebify(Activity activity, ImageView imageViewMain, ImageView imageViewBG, ImageView imageViewGif, String isSmall) {

        if (PowerPreference.getDefaultFile().getInt(isSmall, 0) >= 3) {
            PowerPreference.getDefaultFile().putInt(isSmall, 0);
            setWebify(activity, imageViewMain, imageViewBG, imageViewGif, isSmall);
        } else {
            if (imageViewBG != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsWebifyInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewBG);

            if (imageViewMain != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsWebifyInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewMain);

            if (imageViewGif != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsWebifyInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)]).centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewGif);


            int top = PowerPreference.getDefaultFile().getInt(isSmall, 0) + 1;
            PowerPreference.getDefaultFile().putInt(isSmall, top);
        }
    }


    public void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        if (adView.findViewById(R.id.ad_media) != null) {
            MediaView mediaView = adView.findViewById(R.id.ad_media);
            mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            adView.setMediaView(mediaView);

        }
        if (adView.findViewById(R.id.ad_headline) != null)
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));

        if (adView.findViewById(R.id.ad_body) != null)
            adView.setBodyView(adView.findViewById(R.id.ad_body));

        if (adView.findViewById(R.id.ad_call_to_action) != null)
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));

        if (adView.findViewById(R.id.ad_app_icon) != null)
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));

        if (adView.findViewById(R.id.ad_stars) != null)
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));


        if (nativeAd.getStarRating() == null) {
            if (adView.getStarRatingView() != null)
                Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.GONE);
        } else {
            if (adView.getStarRatingView() != null) {
                Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.VISIBLE);
                ((RatingBar) adView.getStarRatingView()).setRating(Float.parseFloat(String.valueOf(nativeAd.getStarRating())));
            }
        }

        if (nativeAd.getHeadline() == null) {
            if (adView.getHeadlineView() != null)
                Objects.requireNonNull(adView.getHeadlineView()).setVisibility(View.GONE);
        } else {
            if (adView.getHeadlineView() != null) {
                Objects.requireNonNull(adView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            }
        }

        if (nativeAd.getBody() == null) {
            if (adView.getBodyView() != null)
                Objects.requireNonNull(adView.getBodyView()).setVisibility(View.GONE);
        } else {
            if (adView.getBodyView() != null) {
                Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }
        }

        if (nativeAd.getCallToAction() == null) {
            if (adView.getCallToActionView() != null)
                Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            if (adView.getCallToActionView() != null) {
                Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
                ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            }
        }

        if (nativeAd.getIcon() == null) {
            if (adView.getIconView() != null)
                Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            if (adView.getIconView() != null) {
                ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                        nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
        }

        adView.setNativeAd(nativeAd);
    }

    public void inflateAd(com.facebook.ads.NativeAd nativeAd, NativeAdLayout adView) {

        nativeAd.unregisterView();

        com.facebook.ads.MediaView nativeAdIcon = adView.findViewById(R.id.ad_app_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.ad_headline);
        com.facebook.ads.MediaView nativeAdMedia = adView.findViewById(R.id.ad_media);
        TextView nativeAdBody = adView.findViewById(R.id.ad_body);
        TextView nativeAdCallToAction = adView.findViewById(R.id.ad_call_to_action);

        if (nativeAdTitle != null) {
            if (nativeAd.getAdHeadline() == null) {
                nativeAdTitle.setVisibility(View.GONE);
            } else {
                nativeAdTitle.setVisibility(View.VISIBLE);
                nativeAdTitle.setText(nativeAd.getAdHeadline());
            }
        }

        if (nativeAdBody != null) {
            if (nativeAd.getAdBodyText() == null) {
                nativeAdBody.setVisibility(View.GONE);
            } else {
                nativeAdBody.setVisibility(View.VISIBLE);
                nativeAdBody.setText(nativeAd.getAdBodyText());
            }
        }

        if (nativeAdCallToAction != null) {
            if (nativeAd.getAdCallToAction() == null) {
                nativeAdCallToAction.setVisibility(View.INVISIBLE);
            } else {
                nativeAdCallToAction.setVisibility(View.VISIBLE);
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            }
        }
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);

    }


    public void showNativeListAds(Activity activity, FrameLayout nativeAd, TextView adSpace) {
        switch (adsPriority) {
            case 0:
                showGoogleAds(activity, null, nativeAd, adSpace);
                break;
            case 1:
                showFbAds(activity, null, nativeAd, adSpace);
                break;
        }
    }

}