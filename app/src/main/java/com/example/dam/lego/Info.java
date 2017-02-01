package com.example.dam.lego;

import android.content.Context;
import android.media.Image;
import android.util.Log;

import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by dam on 30/1/17.
 */

public class Info {
    private String time;
    private ArrayList<Information> infos;
    String set_num;
    String name;
    int year;
    String theme_id;
    int num_parts;
    String set_img_url;
    public class Information {
        String set_num;
        String name;
        int year;
        int theme_id;
        int num_parts;
        String set_img_url;
    }

    public Info() {
        time = null;
        infos = new ArrayList<Information>();
    }

    public String getSet_num() {
        return set_num;
    }

    public void setSet_num(String set_num) {
        this.set_num = set_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(String theme_id) {
        this.theme_id = theme_id;
    }

    public int getNum_parts() {
        return num_parts;
    }

    public void setNum_parts(int num_parts) {
        this.num_parts = num_parts;
    }

    public String getSet_img_url() {
        return set_img_url;
    }

    public void setSet_img_url(String set_img_url) {
        this.set_img_url = set_img_url;
    }

    public Information getInformation(String info){
        for (Information i: infos){
            if (i.name.equals(infos))return i;
        }
        return null;
    }
    public ArrayList<Information> getInfo() { return this.infos; }
    public String getTime() { return time; }

}
