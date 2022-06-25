package com.example.app_book.Model;

public class Model_KQKham {
    String uid,  CDBanDau, maKQ,NgNhan,NgGoc,DonThuoc,CDCuoiCung,idDT,MaLichKham,LoiKHuyen, ngaykham;

    public Model_KQKham(String uid, String CDBanDau, String maKQ, String ngNhan, String ngGoc, String donThuoc, String CDCuoiCung, String idDT, String maLichKham, String loiKHuyen, String ngaykham) {
        this.uid = uid;
        this.CDBanDau = CDBanDau;
        this.maKQ = maKQ;
        NgNhan = ngNhan;
        NgGoc = ngGoc;
        DonThuoc = donThuoc;
        this.CDCuoiCung = CDCuoiCung;
        this.idDT = idDT;
        MaLichKham = maLichKham;
        LoiKHuyen = loiKHuyen;
        this.ngaykham = ngaykham;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCDBanDau() {
        return CDBanDau;
    }

    public void setCDBanDau(String CDBanDau) {
        this.CDBanDau = CDBanDau;
    }

    public String getMaKQ() {
        return maKQ;
    }

    public void setMaKQ(String maKQ) {
        this.maKQ = maKQ;
    }

    public String getNgNhan() {
        return NgNhan;
    }

    public void setNgNhan(String ngNhan) {
        NgNhan = ngNhan;
    }

    public String getNgGoc() {
        return NgGoc;
    }

    public void setNgGoc(String ngGoc) {
        NgGoc = ngGoc;
    }

    public String getDonThuoc() {
        return DonThuoc;
    }

    public void setDonThuoc(String donThuoc) {
        DonThuoc = donThuoc;
    }

    public String getCDCuoiCung() {
        return CDCuoiCung;
    }

    public void setCDCuoiCung(String CDCuoiCung) {
        this.CDCuoiCung = CDCuoiCung;
    }

    public String getIdDT() {
        return idDT;
    }

    public void setIdDT(String idDT) {
        this.idDT = idDT;
    }

    public String getMaLichKham() {
        return MaLichKham;
    }

    public void setMaLichKham(String maLichKham) {
        MaLichKham = maLichKham;
    }

    public String getLoiKHuyen() {
        return LoiKHuyen;
    }

    public void setLoiKHuyen(String loiKHuyen) {
        LoiKHuyen = loiKHuyen;
    }

    public String getNgaykham() {
        return ngaykham;
    }

    public void setNgaykham(String ngaykham) {
        this.ngaykham = ngaykham;
    }

    public Model_KQKham(){

    }
}
