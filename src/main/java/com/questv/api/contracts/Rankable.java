package com.questv.api.contracts;

public interface Rankable extends Identifiable, Comparable<Rankable>{
  String getId();
  String getUsername();
  Integer getPoints();


  @Override
  default int compareTo(Rankable o) {
    return o.getPoints() - getPoints();
  }
}
