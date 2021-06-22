package com.semihbkgr.Corbeau.util;

import lombok.NonNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Map;

public class Images {

    public static final String SUB_FOLDER="images";

    private static final String IMAGE_NOT_FOUND_IMAGE_NAME= "image-not-found";
    private static final String ICON_IMAGE_NAME= "icon";
    private static final String ICON_WHITE_IMAGE_NAME= "icon-white";
    private static final String ICON_GREEN_IMAGE_NAME= "icon-green";
    private static final String LOGO_IMAGE_NAME="logo";

    private static byte[] IMAGE_NOT_FOUND_IMAGE_CONTENT;
    private static byte[] ICON_IMAGE_CONTENT;
    private static byte[] ICON_WHITE_IMAGE_CONTENT;
    private static byte[] ICON_GREEN_IMAGE_CONTENT;
    private static byte[] LOGO_IMAGE_CONTENT;

    private static final Map<String,byte[]> PREDEFINED_IMAGE_MAP;

    static{

        try {
            IMAGE_NOT_FOUND_IMAGE_CONTENT= new ClassPathResource(
                    SUB_FOLDER+"/"+concatPngExtension(IMAGE_NOT_FOUND_IMAGE_NAME),Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ICON_IMAGE_CONTENT=new ClassPathResource(
                    SUB_FOLDER+"/"+concatPngExtension(ICON_IMAGE_NAME),Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            ICON_WHITE_IMAGE_CONTENT=new ClassPathResource(
                    SUB_FOLDER+"/"+concatPngExtension(ICON_WHITE_IMAGE_NAME),Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            ICON_GREEN_IMAGE_CONTENT=new ClassPathResource(
                    SUB_FOLDER+"/"+concatPngExtension(ICON_GREEN_IMAGE_NAME),Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            LOGO_IMAGE_CONTENT=new ClassPathResource(
                    SUB_FOLDER+"/"+concatPngExtension(LOGO_IMAGE_NAME),Images.class.getClassLoader())
                    .getInputStream().readAllBytes();
        }catch(IOException e){
            e.printStackTrace();
        }

        PREDEFINED_IMAGE_MAP =Map.of(IMAGE_NOT_FOUND_IMAGE_NAME,IMAGE_NOT_FOUND_IMAGE_CONTENT,
                ICON_IMAGE_NAME,ICON_IMAGE_CONTENT,
                ICON_WHITE_IMAGE_NAME,ICON_WHITE_IMAGE_CONTENT,
                ICON_GREEN_IMAGE_NAME,ICON_GREEN_IMAGE_CONTENT,
                LOGO_IMAGE_NAME,LOGO_IMAGE_CONTENT);

    }

    private static String concatPngExtension(String imageName){
        return imageName.concat(".png");
    }

    public static byte[] getImageNotFoundImageContent(){
        return IMAGE_NOT_FOUND_IMAGE_CONTENT;
    }

    public static byte[] getIconImageContent(){
        return ICON_IMAGE_CONTENT;
    }

    public static byte[] getIconWhiteImageContent(){
        return ICON_WHITE_IMAGE_CONTENT;
    }

    public static byte[] getIconGreenImageContent(){
        return ICON_GREEN_IMAGE_CONTENT;
    }

    public static byte[] getLogoImageContent(){
        return LOGO_IMAGE_CONTENT;
    }

    @Nullable
    public static byte[] getPredefinedImage(@NonNull String imageName){
        if(imageName.contains("/")){
            return null;
        }
        return PREDEFINED_IMAGE_MAP.get(imageName);
    }

    public static boolean contains(@NonNull String imageName){
        return PREDEFINED_IMAGE_MAP.containsKey(imageName);
    }

}
