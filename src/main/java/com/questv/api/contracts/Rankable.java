package com.questv.api.contracts;

public interface Rankable extends Identifiable{
  String getId();
  String getUsername();
  Integer getPoints();
}
