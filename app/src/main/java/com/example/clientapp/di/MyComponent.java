package com.example.clientapp.di;

import com.example.clientapp.DiActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SharedPrefModule.class)
public interface MyComponent {

    void inject(DiActivity activity);
}
