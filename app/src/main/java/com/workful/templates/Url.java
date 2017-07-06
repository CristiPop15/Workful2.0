package com.workful.templates;

/**
 * Created by Cristian on 5/1/2017.
 */
public class Url {
    private static String http = "http://192.168.100.2:8080/";
    private static String url = http+"WebProjectWorkful/app/";
    private static String imag_url = http+"Image/getImage?path=E:/ImgApp/";
    private static String imag_url_extension = ".png";

    public static String getUrl(){
        return url;
    }

    public static String image_url(String email){
        return imag_url+email+imag_url_extension;
    }
}
