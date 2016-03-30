package com.michael.kidquest.greendao.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table "CHARACTER".
 */
public class Character {

    private Long id;
    /** Not-null value. */
    private String character_name;
    private int character_level;
    /** Not-null value. */
    private String parent_pin;
    private int gold;
    private int xp;
    private String token;
    private Integer serverId;

    public Character() {
    }

    public Character(Long id) {
        this.id = id;
    }

    public Character(Long id, String name, int level, String parent_pin, int gold, int xp, String token, Integer serverId) {
        this.id = id;
        this.character_name = name;
        this.character_level = level;
        this.parent_pin = parent_pin;
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
    public String getCharacter_name() {
        return character_name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public int getCharacter_level() {
        return character_level;
    }

    public void setCharacter_level(int character_level) {
        this.character_level = character_level;
    }

    /** Not-null value. */
    public String getParent_pin() {
        return parent_pin;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setParent_pin(String parent_pin) {
        this.parent_pin = parent_pin;
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
