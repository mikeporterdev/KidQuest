package com.example.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.model.Difficulty;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DIFFICULTY".
*/
public class DifficultyDao extends AbstractDao<Difficulty, Long> {

    public static final String TABLENAME = "DIFFICULTY";

    /**
     * Properties of entity Difficulty.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property DifficultyLevel = new Property(1, String.class, "difficultyLevel", false, "DIFFICULTY_LEVEL");
        public final static Property Gold = new Property(2, Integer.class, "gold", false, "GOLD");
        public final static Property Experience = new Property(3, Integer.class, "experience", false, "EXPERIENCE");
    };


    public DifficultyDao(DaoConfig config) {
        super(config);
    }
    
    public DifficultyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DIFFICULTY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DIFFICULTY_LEVEL\" TEXT," + // 1: difficultyLevel
                "\"GOLD\" INTEGER," + // 2: gold
                "\"EXPERIENCE\" INTEGER);"); // 3: experience
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DIFFICULTY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Difficulty entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String difficultyLevel = entity.getDifficultyLevel();
        if (difficultyLevel != null) {
            stmt.bindString(2, difficultyLevel);
        }
 
        Integer gold = entity.getGold();
        if (gold != null) {
            stmt.bindLong(3, gold);
        }
 
        Integer experience = entity.getExperience();
        if (experience != null) {
            stmt.bindLong(4, experience);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Difficulty readEntity(Cursor cursor, int offset) {
        Difficulty entity = new Difficulty( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // difficultyLevel
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // gold
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3) // experience
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Difficulty entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDifficultyLevel(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGold(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setExperience(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Difficulty entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Difficulty entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}