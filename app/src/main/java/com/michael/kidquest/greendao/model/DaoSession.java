package com.michael.kidquest.greendao.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.greendao.model.Item;
import com.michael.kidquest.greendao.model.Inventory;
import com.michael.kidquest.greendao.model.Quest;

import com.michael.kidquest.greendao.model.CharacterDao;
import com.michael.kidquest.greendao.model.ItemDao;
import com.michael.kidquest.greendao.model.InventoryDao;
import com.michael.kidquest.greendao.model.QuestDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig characterDaoConfig;
    private final DaoConfig itemDaoConfig;
    private final DaoConfig inventoryDaoConfig;
    private final DaoConfig questDaoConfig;

    private final CharacterDao characterDao;
    private final ItemDao itemDao;
    private final InventoryDao inventoryDao;
    private final QuestDao questDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        characterDaoConfig = daoConfigMap.get(CharacterDao.class).clone();
        characterDaoConfig.initIdentityScope(type);

        itemDaoConfig = daoConfigMap.get(ItemDao.class).clone();
        itemDaoConfig.initIdentityScope(type);

        inventoryDaoConfig = daoConfigMap.get(InventoryDao.class).clone();
        inventoryDaoConfig.initIdentityScope(type);

        questDaoConfig = daoConfigMap.get(QuestDao.class).clone();
        questDaoConfig.initIdentityScope(type);

        characterDao = new CharacterDao(characterDaoConfig, this);
        itemDao = new ItemDao(itemDaoConfig, this);
        inventoryDao = new InventoryDao(inventoryDaoConfig, this);
        questDao = new QuestDao(questDaoConfig, this);

        registerDao(Character.class, characterDao);
        registerDao(Item.class, itemDao);
        registerDao(Inventory.class, inventoryDao);
        registerDao(Quest.class, questDao);
    }
    
    public void clear() {
        characterDaoConfig.getIdentityScope().clear();
        itemDaoConfig.getIdentityScope().clear();
        inventoryDaoConfig.getIdentityScope().clear();
        questDaoConfig.getIdentityScope().clear();
    }

    public CharacterDao getCharacterDao() {
        return characterDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public InventoryDao getInventoryDao() {
        return inventoryDao;
    }

    public QuestDao getQuestDao() {
        return questDao;
    }

}
