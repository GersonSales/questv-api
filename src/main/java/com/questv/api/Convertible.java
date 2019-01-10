package com.questv.api;

public interface Convertible <T>{
    T convert();

    void update(T model);
}
