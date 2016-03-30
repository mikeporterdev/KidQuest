package com.michael.kidquest.greendao.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table "CHARACTER".
 */
public class Character {

    private Long id;
    /** Not-null value. */
    private String name;
    private int character_level;
    /** Not-null value. */
    private String parentPin;
    private int gold;
    private int xp;
    private String token;
    private Integer serverId;

    public Character() {
    }

    public Character(Long id) {
        this.id = id;
    }

    public Character(Long id, String name, int level, String parentPin, int gold, int xp, String token, Integer serverId) {
        this.id = id;
        this.name = name;
        this.character_level = level;
        this.parentPin = parentPin;
        this.gold = gold;
        this.xp = xp;
        this.token = token;
        this.serverId = serverId;
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

    public int getCharacter_level() {
        return character_level;
    }

    public void setCharacter_level(int character_level) {
        this.character_level = character_level;
    }

    /** Not-null value. */
    public String getParentPin() {
        return parentPin;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setParentPin(String parentPin) {
        this.parentPin = parentPin;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

}
