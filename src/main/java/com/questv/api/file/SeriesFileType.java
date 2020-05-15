package com.questv.api.file;

public enum SeriesFileType {
  COVER, PROMO;

  @Override
  public String toString() {
    final StringBuilder toString = new StringBuilder();
    switch (this) {
      case COVER:
        toString.append("cover");
        break;
      case PROMO:
        toString.append("promoImage");
        break;

    }
    return toString.toString();
  }
}
