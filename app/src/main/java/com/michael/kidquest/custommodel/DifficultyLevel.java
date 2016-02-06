package com.michael.kidquest.custommodel;

/**
 * Created by Michael Porter on 05/02/16.
 */
public enum DifficultyLevel {
    VERY_EASY("Very Easy"),
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    VERY_HARD("Very Hard");

    private String value;

    DifficultyLevel(String value){
        this.value = value;
    }

    public String getDifficultyLevel(){
        return value;
    }

    public static DifficultyLevel fromString(String value){
        if (value != null) {
            for (DifficultyLevel d : DifficultyLevel.values()){
                if (value.equalsIgnoreCase(d.value)) return d;
            }
        }
        throw new IllegalArgumentException("No difficulty level found with text " + value);
    }
}
