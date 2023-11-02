package com.example.clientapp.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module  //factory producing object
public class SharedPrefModule {
    Context mContext;

    public SharedPrefModule(Context context){
        mContext = context;
    }

    Context provideContext(){
        return  mContext;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPrefs(){
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }
}
