package com.example.app_book.Model;

public class Model_doctor {
    String Name,search, STD, avatar, uid,
    chuyenkhoa, diachi,Type,typehoatdong,
    kiemduyet, OnlineStatus, TyingTo;

    public Model_doctor(String name, String search, String STD, String avatar, String uid, String chuyenkhoa, String diachi, String type, String typehoatdong, String kiemduyet, String onlineStatus, String tyingTo) {
        Name = name;
        this.search = search;
        this.STD = STD;
        this.avatar = avatar;
        this.uid = uid;
        this.chuyenkhoa = chuyenkhoa;
        this.diachi = diachi;
        Type = type;
        this.typehoatdong = typehoatdong;
        this.kiemduyet = kiemduyet;
        OnlineStatus = onlineStatus;
        TyingTo = tyingTo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public String getChuyenkhoa() {
        return chuyenkhoa;
    }

    public void setChuyenkhoa(String chuyenkhoa) {
        this.chuyenkhoa = chuyenkhoa;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTypehoatdong() {
        return typehoatdong;
    }

    public void setTypehoatdong(String typehoatdong) {
        this.typehoatdong = typehoatdong;
    }

    public String getKiemduyet() {
        return kiemduyet;
    }

    public void setKiemduyet(String kiemduyet) {
        this.kiemduyet = kiemduyet;
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

    public Model_doctor(){

  }
}
