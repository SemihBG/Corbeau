package com.smh.PostBlogWebApp.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class Images {

    public static final String SUB_FOLDER="images";

    public static final String IMAGE_NOT_FOUND_IMAGE_NAME= "image-not-found.png";
    public static final String ICON_IMAGE_NAME= "icon.png";
    public static final String LOGO_IMAGE_NAME="logo.png";

    private static byte[] IMAGE_NOT_FOUND_IMAGE_CONTENT;
    private static byte[] ICON_IMAGE_CONTENT;
    private static byte[] LOGO_IMAGE_CONTENT;

    static{

        try {
            IMAGE_NOT_FOUND_IMAGE_CONTENT= new ClassPathResource(
                    SUB_FOLDER+"/"+IMAGE_NOT_FOUND_IMAGE_NAME,Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ICON_IMAGE_CONTENT=new ClassPathResource(
                    SUB_FOLDER+"/"+ICON_IMAGE_NAME,Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            LOGO_IMAGE_CONTENT=new ClassPathResource(
                    SUB_FOLDER+"/"+LOGO_IMAGE_NAME,Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static byte[] getImageNotFoundImageContent(){
        return IMAGE_NOT_FOUND_IMAGE_CONTENT;
    }

    public static byte[] getIconImageContent(){
        return ICON_IMAGE_CONTENT;
    }

    public static byte[] getLogoImageContent(){
        return LOGO_IMAGE_CONTENT;
    }

}
