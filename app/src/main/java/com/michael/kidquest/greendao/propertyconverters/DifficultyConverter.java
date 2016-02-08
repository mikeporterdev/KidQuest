package com.michael.kidquest.greendao.propertyconverters;

import com.michael.kidquest.greendao.custommodel.DifficultyLevel;

/**
 * Created by Michael Porter on 05/02/16.
 * Used by GreenDAO to convert Enums into Database values
 */
public class DifficultyConverter implements PropertyConverter<DifficultyLevel, String> {


    @Override
    public DifficultyLevel convertToEntityProperty(String databaseValue) {
        try {
            return DifficultyLevel.valueOf(databaseValue);
        } catch (IllegalArgumentException e) {
            return DifficultyLevel.MEDIUM;
        }
    }

    @Override
    public String convertToDatabaseValue(DifficultyLevel entityProperty) {
        return entityProperty.getDifficultyLevel();
    }
}
