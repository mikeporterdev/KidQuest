package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class KidQuestDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(5, "com.michael.kidquest.greendao.model");

        Entity userDetails = schema.addEntity("Character");
        userDetails.addIdProperty();
        userDetails.addStringProperty("name").notNull();
        userDetails.addIntProperty("level").notNull();
        userDetails.addStringProperty("parentPin").notNull();

        Entity item = schema.addEntity("Item");
        item.addIdProperty();
        item.addStringProperty("name").notNull().unique();
        item.addIntProperty("itemLevel").notNull();

        Entity inventory = schema.addEntity("Inventory");
        inventory.addIdProperty();
        Property itemId = inventory.addLongProperty("itemId").notNull().getProperty();
        inventory.addToOne(item, itemId);

        Entity quest = schema.addEntity("Quest");
        quest.addIdProperty();
        quest.addStringProperty("title").notNull();
        quest.addStringProperty("description");
        quest.addBooleanProperty("completed").notNull();
        quest.addStringProperty("difficultyLevel").customType(
                "com.michael.kidquest.greendao.custommodel.DifficultyLevel",
                "com.michael.kidquest.greendao.propertyconverters.DifficultyConverter").notNull();
        quest.addDateProperty("dateAdded").notNull();
        quest.addDateProperty("expiryDate");

        new DaoGenerator().generateAll(schema, "../app/src/main/java");

    }
}
