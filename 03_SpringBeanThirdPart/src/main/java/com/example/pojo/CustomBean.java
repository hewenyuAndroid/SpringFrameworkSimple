package com.example.pojo;

public class CustomBean {

    private String id;
    private String name;
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "CustomBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

}
