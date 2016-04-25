package com.michael.kidquest.greendao.model;

import java.io.Serializable;

/**
 * Created by m_por on 25/04/2016.
 */
public class Reward implements Serializable {
    private Long id;
    private String name;
    private int cost;
    private boolean completed;

    public Reward(){

    }

    public Reward(Long id, String name, int cost, boolean completed) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
