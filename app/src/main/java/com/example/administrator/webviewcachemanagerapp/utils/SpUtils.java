package com.example.administrator.webviewcachemanagerapp.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SpUtils {

    public static final String SP_TABLE = "webview_cache";
    public static final String SP_CACHE_TOTAL = "cache_total";//所有缓存的id均组合成字符串（按"_id"的方式拼凑）存进sp中的value中
    public static final String SP_CACHE_COUNT = "cache_count";//记录当前缓存的id的个数


    public static void setSharePreferenceString(Context context, String key, String value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SP_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static Integer getSharePreferenceInteger(Context context, String key, Integer defaultValue) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SP_TABLE, Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public static void setSharePreferenceInteger(Context context, String key, Integer value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SP_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static String getSharePreferenceString(Context context, String key, String defaultValue) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SP_TABLE, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * 给缓存的数目+1
     *
     * @param context
     */
    public static void addSpCacheCount(Context context) {
        Log.e("addSpCacheCount", "调用了");
        int countTemp = getSharePreferenceInteger(context, SP_CACHE_COUNT, 0);
        setSharePreferenceInteger(context, SP_CACHE_COUNT, ++countTemp);
    }

    /**
     * 给缓存的数目-1
     *
     * @param context
     */
    public static void minusSpCacheCount(Context context) {
        Log.e("minusSpCacheCount", "调用了");
        int countTemp = getSharePreferenceInteger(context, SP_CACHE_COUNT, 0);
        if (countTemp > 0) {
            setSharePreferenceInteger(context, SP_CACHE_COUNT, --countTemp);
        }
    }


    /**
     * 获取已经缓存的数目
     *
     * @param context
     * @return
     */
    public static int returnCacheTotal(Context context) {
        Log.e("当前已经缓存的数目为 ", getSharePreferenceInteger(context, SP_CACHE_COUNT, 0) + "");
        return getSharePreferenceInteger(context, SP_CACHE_COUNT, 0);
    }

    /**
     * 获取最老的时间的key值
     *
     * @param context
     * @return
     */
    public static String returnTagTimeKey(Context context) {
        String[] timeArrays = getSharePreferenceString(context, SP_CACHE_TOTAL, "").split("_");
        Log.e("returnTagTimeKey", "进入了");
        for (int i = 0; i < timeArrays.length; i++) {
            Log.e("key和value的对应值",timeArrays[i]+"  "+getSharePreferenceString(context,timeArrays[i],""));
        }

        if (timeArrays.length == 0) {
            Log.e("sp_cache_total的值为空", "返回了");
            return "";
        }
        int oldestTimeIndex = 0;
        for (int i = 0; i < timeArrays.length; i++) {
            if (!"".equals(getSharePreferenceString(context,timeArrays[i],""))) {
                oldestTimeIndex = i;
                break;
            }
        }
        for (int i = oldestTimeIndex + 1; i < timeArrays.length; i++) {
            if (!"".equals(timeArrays[i]) && timeArrays[i].compareTo(timeArrays[oldestTimeIndex]) < 0) {
                oldestTimeIndex = i;
            }
        }
        Log.e("返回的时间最老对应的key值", timeArrays[oldestTimeIndex]);
        return timeArrays[oldestTimeIndex];
    }

    /**
     * 获取最老的时间的value值
     *
     * @param context
     * @return
     */
    public static String returnTagTimeValue(Context context, String timeTagKey) {
        return getSharePreferenceString(context, timeTagKey, "");
    }


    /**
     * 给缓存的id库增加最新的id
     *
     * @param context
     * @param newIdStr
     */
    public static void addNewIdToSpCacheTotal(Context context, String newIdStr) {
        Log.e("addNewIdToSpCacheTotal", "执行了");
        String cacheTotalValueNow = getSharePreferenceString(context, SP_CACHE_TOTAL, "");
        if (cacheTotalValueNow.contains(newIdStr)) {
            return;
        } else {
            setSharePreferenceString(context, SP_CACHE_TOTAL, cacheTotalValueNow + newIdStr);
        }
    }

    public static String returnSpCacheTotal(Context context) {
        return getSharePreferenceString(context, SP_CACHE_TOTAL, "");
    }
}
