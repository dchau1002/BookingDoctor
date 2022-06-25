package com.example.app_book.Model;

public class Mode_Post {
    String iddoctor,Title,Noidung,chuyenkhoa,time,image;

    public Mode_Post(String iddoctor, String title, String noidung, String chuyenkhoa, String time, String image) {
        this.iddoctor = iddoctor;
        Title = title;
        Noidung = noidung;
        this.chuyenkhoa = chuyenkhoa;
        this.time = time;
        this.image = image;
    }

    public String getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(String iddoctor) {
        this.iddoctor = iddoctor;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNoidung() {
        return Noidung;
    }

    public void setNoidung(String noidung) {
        Noidung = noidung;
    }

    public String getChuyenkhoa() {
        return chuyenkhoa;
    }

    public void setChuyenkhoa(String chuyenkhoa) {
        this.chuyenkhoa = chuyenkhoa;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Mode_Post() {

    }
}
