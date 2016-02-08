package com.michael.kidquest.greendao.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ITEM".
 */
public class Item {

    private Long id;
    /** Not-null value. */
    private String name;
    private int itemLevel;

    public Item() {
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item(Long id, String name, int itemLevel) {
        this.id = id;
        this.name = name;
        this.itemLevel = itemLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

}
