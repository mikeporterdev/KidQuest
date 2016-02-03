package com.example;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class DaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.example.model");

        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("parentPin");

        Entity character = schema.addEntity("Character");
        character.addIdProperty();
        character.addStringProperty("name");
        character.addIntProperty("level");

        //One to one relationship between character and user
        Property userId = character.addLongProperty("userId").getProperty();
        character.addToOne(user, userId);
        Property characterId = user.addLongProperty("characterId").getProperty();
        user.addToOne(character, characterId);

        Entity item = schema.addEntity("Item");
        item.addIdProperty();
        item.addStringProperty("name");
        item.addIntProperty("itemLevel");

        //Character can have many items. Item is owned by one character.
        Property itemsCharacterId = item.addLongProperty("characterId").getProperty();
        item.addToOne(character, itemsCharacterId);

        ToMany characterToItems = character.addToMany(item, itemsCharacterId);
        characterToItems.setName("items");

        Entity quest = schema.addEntity("Quest");
        quest.addIdProperty();
        quest.addStringProperty("title");
        quest.addStringProperty("description");
        quest.addBooleanProperty("completed");

        Entity questReward = schema.addEntity("QuestReward");
        questReward.addIdProperty();
        questReward.addIntProperty("gold");
        questReward.addIntProperty("experience");

    }
}
