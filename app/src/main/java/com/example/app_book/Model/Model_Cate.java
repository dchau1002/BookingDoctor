package com.example.app_book.Model;

public class Model_Cate {
    String id , name, image;


    public Model_Cate(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public Model_Cate(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
