package com.goabraod.weixin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;


public class HttpBasicAuth {
	@SuppressWarnings("unused")
	private String getHeader() {
        String auth = "abc:123" ;
        String authHeader = "Basic " + (new BASE64Encoder()).encode(auth.getBytes());
        return authHeader;
    }
	
	public static void main(String[] args) {

        try {
            URL url = new URL ("http://ip:port/login");
            String encoding = new BASE64Encoder().encode("test1:test1".getBytes());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}