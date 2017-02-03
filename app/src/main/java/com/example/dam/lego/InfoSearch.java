package com.example.dam.lego;

/**
 * Created by dam on 3/2/17.
 */

public class InfoSearch {
    private String set_id;
    private String year;
    private String pieces;
    private String theme1;
    private String theme2;
    private String theme3;
    private String accessory;
    private String kit;
    private String descr;
    private String url;
    private String img_tn;
    private String img_sm;
    private String img_big;

    public InfoSearch() {
    }

    public String getSet_id() {
        return set_id;
    }

    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getTheme1() {
        return theme1;
    }

    public void setTheme1(String theme1) {
        this.theme1 = theme1;
    }

    public String getTheme2() {
        return theme2;
    }

    public void setTheme2(String theme2) {
        this.theme2 = theme2;
    }

    public String getTheme3() {
        return theme3;
    }

    public void setTheme3(String theme3) {
        this.theme3 = theme3;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg_tn() {
        return img_tn;
    }

    public void setImg_tn(String img_tn) {
        this.img_tn = img_tn;
    }

    public String getImg_sm() {
        return img_sm;
    }

    public void setImg_sm(String img_sm) {
        this.img_sm = img_sm;
    }

    public String getImg_big() {
        return img_big;
    }

    public void setImg_big(String img_big) {
        this.img_big = img_big;
    }

    @Override
    public String toString() {
        return "InfoSearch{" +
                "set_id='" + set_id + '\'' +
                ", year='" + year + '\'' +
                ", pieces='" + pieces + '\'' +
                ", theme1='" + theme1 + '\'' +
                ", theme2='" + theme2 + '\'' +
                ", theme3='" + theme3 + '\'' +
                ", accessory='" + accessory + '\'' +
                ", kit='" + kit + '\'' +
                ", descr='" + descr + '\'' +
                ", url='" + url + '\'' +
                ", img_tn='" + img_tn + '\'' +
                ", img_sm='" + img_sm + '\'' +
                ", img_big='" + img_big + '\'' +
                '}';
    }
}
