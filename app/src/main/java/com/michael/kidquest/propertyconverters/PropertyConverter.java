package com.michael.kidquest.propertyconverters;

/**
 * Created by Michael Porter on 05/02/16.
 */
public interface PropertyConverter<P, D> {

    P convertToEntityProperty(D databaseValue);

    D convertToDatabaseValue(P entityProperty);
}