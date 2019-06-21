package com.example.refillpoints.data.db;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import kotlin.jvm.Synchronized;

// TODO: 17.06.19 temporary solution(need understand how correct provide DB)
public class DatabaseHolder {

    @SuppressLint("StaticFieldLeak")
    @Nullable
    private static Context context;


    @Synchronized
    public static void init(Context appContext) {
        if (databaseHelper != null && databaseHelper.isOpen()) {
            return;
        }
        context = appContext;
        databaseHelper = get();
    }

    @Nullable
    private static DatabaseHelper databaseHelper;


    public static DatabaseHelper get(){
        if (databaseHelper == null) {
            synchronized (DatabaseHolder.class) {
                if (databaseHelper == null) {
                    databaseHelper = createHelper();
                }
            }
        }
        return databaseHelper;
    }


    private static DatabaseHelper createHelper(){
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }


    public static void close(){
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
