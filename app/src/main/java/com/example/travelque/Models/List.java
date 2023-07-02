package com.example.travelque.Models;

public class List {
    private Integer id;
    private String notes;
    private Integer userId;
    private Integer travelId;

    public List(Integer id, String notes, Integer userId, Integer travelId) {
        this.id = id;
        this.notes = notes;
        this.userId = userId;
        this.travelId = travelId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTravelId() {
        return travelId;
    }

    public void setTravelId(Integer travelId) {
        this.travelId = travelId;
    }
}
