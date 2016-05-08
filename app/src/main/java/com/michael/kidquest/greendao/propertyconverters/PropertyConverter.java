package com.michael.kidquest.greendao.propertyconverters;

/**
 * Created by Michael Porter on 05/02/16.
 *
 * Simple interface for the enum-to-database converter
 */
interface PropertyConverter<P, D> {

    P convertToEntityProperty(D databaseValue);

    D convertToDatabaseValue(P entityProperty);
}