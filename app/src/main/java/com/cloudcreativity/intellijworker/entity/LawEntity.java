package com.cloudcreativity.intellijworker.entity;

public class LawEntity {
    private int id;
    private String lawTitle;
    private String lawContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLawTitle() {
        return lawTitle;
    }

    public void setLawTitle(String lawTitle) {
        this.lawTitle = lawTitle;
    }

    public String getLawContent() {
        return lawContent;
    }

    public void setLawContent(String lawContent) {
        this.lawContent = lawContent;
    }
}
