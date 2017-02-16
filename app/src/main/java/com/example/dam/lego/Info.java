package com.example.dam.lego;

/**
 * Created by dam on 2/2/17.
 */

public class Info {
    private String part_id;
    private String qty;
    private String ldraw_color_id;
    private String type;
    private String part_name;
    private String color_name;
    private String part_img_url;
    private String element_id;
    private String element_img_url;
    private String rb_color_id;
    private String part_type_id;

    public Info() {
    }

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getLdraw_color_id() {
        return ldraw_color_id;
    }

    public void setLdraw_color_id(String ldraw_color_id) {
        this.ldraw_color_id = ldraw_color_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public String getPart_img_url() {
        return part_img_url;
    }

    public void setPart_img_url(String part_img_url) {
        this.part_img_url = part_img_url;
    }

    public String getElement_id() {
        return element_id;
    }

    public void setElement_id(String element_id) {
        this.element_id = element_id;
    }

    public String getElement_img_url() {
        return element_img_url;
    }

    public void setElement_img_url(String element_img_url) {
        this.element_img_url = element_img_url;
    }

    public String getRb_color_id() {
        return rb_color_id;
    }

    public void setRb_color_id(String rb_color_id) {
        this.rb_color_id = rb_color_id;
    }

    public String getPart_type_id() {
        return part_type_id;
    }

    public void setPart_type_id(String part_type_id) {
        this.part_type_id = part_type_id;
    }

    @Override
    public String toString() {
        return "Info{" +
                "part_id='" + part_id + '\'' +
                ", qty='" + qty + '\'' +
                ", ldraw_color_id='" + ldraw_color_id + '\'' +
                ", type='" + type + '\'' +
                ", part_name='" + part_name + '\'' +
                ", color_name='" + color_name + '\'' +
                ", part_img_url='" + part_img_url + '\'' +
                ", element_id='" + element_id + '\'' +
                ", element_img_url='" + element_img_url + '\'' +
                ", rb_color_id='" + rb_color_id + '\'' +
                ", part_type_id='" + part_type_id + '\'' +
                '}';
    }
}
