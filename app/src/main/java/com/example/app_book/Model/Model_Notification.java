package com.example.app_book.Model;

public class Model_Notification {
    String Ma_Noti, idsender, idTake, title, content, ippost ,type,MaHoSo, MaLichKhamofDT, MaLichKhamofUS;

    public Model_Notification(String ma_Noti, String idsender, String idTake, String title, String content, String ippost, String type, String mahoso, String maLichKhamofDT, String maLichKhamofUS) {
        Ma_Noti = ma_Noti;
        this.idsender = idsender;
        this.idTake = idTake;
        this.title = title;
        this.content = content;
        this.ippost = ippost;
        this.type = type;
        MaHoSo = mahoso;
        MaLichKhamofDT = maLichKhamofDT;
        MaLichKhamofUS = maLichKhamofUS;
    }

    public String getMa_Noti() {
        return Ma_Noti;
    }

    public void setMa_Noti(String ma_Noti) {
        Ma_Noti = ma_Noti;
    }

    public String getIdsender() {
        return idsender;
    }

    public void setIdsender(String idsender) {
        this.idsender = idsender;
    }

    public String getIdTake() {
        return idTake;
    }

    public void setIdTake(String idTake) {
        this.idTake = idTake;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIppost() {
        return ippost;
    }

    public void setIppost(String ippost) {
        this.ippost = ippost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMahoso() {
        return MaHoSo;
    }

    public void setMahoso(String mahoso) {
        MaHoSo = mahoso;
    }

    public String getMaLichKhamofDT() {
        return MaLichKhamofDT;
    }

    public void setMaLichKhamofDT(String maLichKhamofDT) {
        MaLichKhamofDT = maLichKhamofDT;
    }

    public String getMaLichKhamofUS() {
        return MaLichKhamofUS;
    }

    public void setMaLichKhamofUS(String maLichKhamofUS) {
        MaLichKhamofUS = maLichKhamofUS;
    }

    public Model_Notification(){

    }
}
