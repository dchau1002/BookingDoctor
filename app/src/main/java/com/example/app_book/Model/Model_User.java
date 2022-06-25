package com.example.app_book.Model;

public class Model_User {
    String Name, STD, avatar, uid, diachi;

    public Model_User(String name, String STD, String avatar, String uid, String diachi) {
        Name = name;
        this.STD = STD;
        this.avatar = avatar;
        this.uid = uid;
        this.diachi = diachi;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSTD() {
        return STD;
    }

    public void setSTD(String STD) {
        this.STD = STD;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Model_User(){

    }
}
