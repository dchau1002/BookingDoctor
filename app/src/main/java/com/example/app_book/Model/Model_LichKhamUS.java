package com.example.app_book.Model;

public class Model_LichKhamUS {
    String uid, iddoctor, ThoiGian, Ngaykham, MoTa,TenHoSo, MaLichKhamofUS, Buoi, MaLichKhamofDT,trangthai, MaHoSo, hinhanh;

    public Model_LichKhamUS(String uid, String iddoctor, String thoiGian, String ngaykham, String moTa, String tenHoSo, String maLichKhamofUS, String buoi, String maLichKhamofDT, String trangthai, String maHoSo, String hinhanh) {
        this.uid = uid;
        this.iddoctor = iddoctor;
        ThoiGian = thoiGian;
        Ngaykham = ngaykham;
        MoTa = moTa;
        TenHoSo = tenHoSo;
        MaLichKhamofUS = maLichKhamofUS;
        Buoi = buoi;
        MaLichKhamofDT = maLichKhamofDT;
        this.trangthai = trangthai;
        MaHoSo = maHoSo;
        this.hinhanh = hinhanh;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(String iddoctor) {
        this.iddoctor = iddoctor;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public String getNgaykham() {
        return Ngaykham;
    }

    public void setNgaykham(String ngaykham) {
        Ngaykham = ngaykham;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getTenHoSo() {
        return TenHoSo;
    }

    public void setTenHoSo(String tenHoSo) {
        TenHoSo = tenHoSo;
    }

    public String getMaLichKhamofUS() {
        return MaLichKhamofUS;
    }

    public void setMaLichKhamofUS(String maLichKhamofUS) {
        MaLichKhamofUS = maLichKhamofUS;
    }

    public String getBuoi() {
        return Buoi;
    }

    public void setBuoi(String buoi) {
        Buoi = buoi;
    }

    public String getMaLichKhamofDT() {
        return MaLichKhamofDT;
    }

    public void setMaLichKhamofDT(String maLichKhamofDT) {
        MaLichKhamofDT = maLichKhamofDT;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getMaHoSo() {
        return MaHoSo;
    }

    public void setMaHoSo(String maHoSo) {
        MaHoSo = maHoSo;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Model_LichKhamUS(){

    }

}
