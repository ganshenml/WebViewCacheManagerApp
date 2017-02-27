package com.example.administrator.webviewcachemanagerapp.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/25.
 */

public class FileUtils {
    public static final String TAG = "FileUtils";

    //helper method for clearCache() , recursive
    //returns number of deleted files
    public static int clearCacheFolder(final File dir, final long timeTag) {
        Log.e("clearCacheFolder","调用了");

        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {

                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, timeTag);
                    }

                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first
                    if (child.lastModified() < timeTag) {
//                        Log.e(TAG, "file: " + child.getName() + "  size: " + child.length());
//                        Date date = new Date(child.lastModified());
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//                        Log.e(TAG, "lastModified: " + formatter.format(date));

                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }


    /**
     * 获取WebView缓存的大小
     * @param dir
     * @return
     */
    public static long returnCacheFolderSize(final File dir) {
        long fileSize = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        fileSize += returnCacheFolderSize(child);
                    } else {
                        fileSize += child.length();
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, String.format("Failed to count the cache, error %s", e.getMessage()));
            }
        }
        return fileSize;
    }

}
