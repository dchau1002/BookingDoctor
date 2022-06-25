package com.example.app_book.Model;

public class Model_LichKhamDT {
    String uid, Ngaythang, Ghichu, Trangthai, NameDT, MaLichKham;

    public Model_LichKhamDT(String uid, String ngaythang, String ghichu, String trangthai, String nameDT, String maLichKham) {
        this.uid = uid;
        Ngaythang = ngaythang;
        Ghichu = ghichu;
        Trangthai = trangthai;
        NameDT = nameDT;
        MaLichKham = maLichKham;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNgaythang() {
        return Ngaythang;
    }

    public void setNgaythang(String ngaythang) {
        Ngaythang = ngaythang;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        Trangthai = trangthai;
    }

    public String getNameDT() {
        return NameDT;
    }

    public void setNameDT(String nameDT) {
        NameDT = nameDT;
    }

    public String getMaLichKham() {
        return MaLichKham;
    }

    public void setMaLichKham(String maLichKham) {
        MaLichKham = maLichKham;
    }

    public Model_LichKhamDT(){

    }



}
