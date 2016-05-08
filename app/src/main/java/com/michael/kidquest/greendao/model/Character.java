package com.michael.kidquest.greendao.model;

public class Character {

    private Long id;
    private String character_name;
    private int character_level;
    private String parent_pin;
    private int gold;
    private int xp;
    private String token;
    private Integer serverId;
    private Integer xp_required;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public int getCharacter_level() {
        return character_level;
    }

    public String getParent_pin() {
        return parent_pin;
    }

    public int getGold() {
        return gold;
    }

    public int getXp() {
        return xp;
    }

    public String getToken() {
        return token;
    }

    public Integer getServerId() {
        return serverId;
    }

    public int getXpRequired() {
        return xp_required;
    }
}
