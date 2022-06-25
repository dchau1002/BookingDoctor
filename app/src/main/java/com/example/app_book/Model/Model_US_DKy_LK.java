package com.example.app_book.Model;

public class Model_US_DKy_LK {
    String iduser, Time, MaLichKhamofUS, MaLichKhamofDT, mahoso, Trangthai;

    public Model_US_DKy_LK(String iduser, String time, String maLichKhamofUS, String maLichKhamofDT, String mahoso, String trangthai) {
        this.iduser = iduser;
        Time = time;
        MaLichKhamofUS = maLichKhamofUS;
        MaLichKhamofDT = maLichKhamofDT;
        this.mahoso = mahoso;
        this.Trangthai = trangthai;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMaLichKhamofUS() {
        return MaLichKhamofUS;
    }

    public void setMaLichKhamofUS(String maLichKhamofUS) {
        MaLichKhamofUS = maLichKhamofUS;
    }

    public String getMaLichKhamofDT() {
        return MaLichKhamofDT;
    }

    public void setMaLichKhamofDT(String maLichKhamofDT) {
        MaLichKhamofDT = maLichKhamofDT;
    }

    public String getMahoso() {
        return mahoso;
    }

    public void setMahoso(String mahoso) {
        this.mahoso = mahoso;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.Trangthai = trangthai;
    }

    public Model_US_DKy_LK(){

    }
}
