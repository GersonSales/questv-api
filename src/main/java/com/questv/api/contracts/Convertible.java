package com.questv.api.contracts;

public interface Convertible <T extends Convertible> {
    T convert();
}
