package com.example.app_book.Model;

public class Model_UserChat {
    String Name, Type, uid, STD, OnlineStatus, TyingTo, avatar;

    public Model_UserChat(String name, String type, String uid, String STD, String onlineStatus, String tyingTo, String avatar) {
        Name = name;
        Type = type;
        this.uid = uid;
        this.STD = STD;
        this.OnlineStatus = onlineStatus;
        this.TyingTo = tyingTo;
        this.avatar = avatar;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSTD() {
        return STD;
    }

    public void setSTD(String STD) {
        this.STD = STD;
    }

    public String getOnlineStatus() {
        return OnlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        OnlineStatus = onlineStatus;
    }

    public String getTyingTo() {
        return TyingTo;
    }

    public void setTyingTo(String tyingTo) {
        TyingTo = tyingTo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Model_UserChat() {

    }
}
