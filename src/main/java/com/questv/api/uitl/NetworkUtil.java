package com.questv.api.uitl;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class NetworkUtil {
  private static String publicIp;
  private static String localIp;


  public static String getPublicIp() {
    if (publicIp == null) {
      try {
        URL url_name = new URL("http://bot.whatismyipaddress.com");

        BufferedReader sc =
            new BufferedReader(new InputStreamReader(url_name.openStream()));
        publicIp = sc.readLine().trim();
      } catch (Exception e) {
        publicIp = "localhost";
      }
    }
    return publicIp;
  }


  private static  boolean dev = false;
  public static String getLocalIp() {
    if (dev) return "192.168.25.21";

      if (localIp == null) {
        try {
          InetAddress host = InetAddress.getLocalHost();
          localIp = host.getHostAddress();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    return localIp;
  }
}
