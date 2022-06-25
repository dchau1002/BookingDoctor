package com.example.app_book.Model;

public class Model_HosoBN {
    String uid, TenHoSo, MaHoSo;

    public Model_HosoBN(String uid, String tenHoSo, String maHoSo) {
        this.uid = uid;
        TenHoSo = tenHoSo;
        MaHoSo = maHoSo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTenHoSo() {
        return TenHoSo;
    }

    public void setTenHoSo(String tenHoSo) {
        TenHoSo = tenHoSo;
    }

    public String getMaHoSo() {
        return MaHoSo;
    }

    public void setMaHoSo(String maHoSo) {
        MaHoSo = maHoSo;
    }

    public Model_HosoBN(){

    }
}
