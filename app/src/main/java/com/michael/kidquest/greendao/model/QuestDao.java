package com.michael.kidquest.greendao.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.michael.kidquest.greendao.custommodel.DifficultyLevel;
import com.michael.kidquest.greendao.propertyconverters.DifficultyConverter;

import com.michael.kidquest.greendao.model.Quest;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QUEST".
*/
public class QuestDao extends AbstractDao<Quest, Long> {

    public static final String TABLENAME = "QUEST";

    /**
     * Properties of entity Quest.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property Completed = new Property(3, boolean.class, "completed", false, "COMPLETED");
        public final static Property DifficultyLevel = new Property(4, String.class, "difficultyLevel", false, "DIFFICULTY_LEVEL");
        public final static Property DateAdded = new Property(5, java.util.Date.class, "dateAdded", false, "DATE_ADDED");
        public final static Property ExpiryDate = new Property(6, java.util.Date.class, "expiryDate", false, "EXPIRY_DATE");
    };

    private final DifficultyConverter difficultyLevelConverter = new DifficultyConverter();

    public QuestDao(DaoConfig config) {
        super(config);
    }
    
    public QuestDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QUEST\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TITLE\" TEXT NOT NULL ," + // 1: title
                "\"DESCRIPTION\" TEXT," + // 2: description
                "\"COMPLETED\" INTEGER NOT NULL ," + // 3: completed
                "\"DIFFICULTY_LEVEL\" TEXT NOT NULL ," + // 4: difficultyLevel
                "\"DATE_ADDED\" INTEGER NOT NULL ," + // 5: dateAdded
                "\"EXPIRY_DATE\" INTEGER);"); // 6: expiryDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QUEST\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Quest entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }
        stmt.bindLong(4, entity.getCompleted() ? 1L: 0L);
        stmt.bindString(5, difficultyLevelConverter.convertToDatabaseValue(entity.getDifficultyLevel()));
        stmt.bindLong(6, entity.getDateAdded().getTime());
 
        java.util.Date expiryDate = entity.getExpiryDate();
        if (expiryDate != null) {
            stmt.bindLong(7, expiryDate.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Quest readEntity(Cursor cursor, int offset) {
        Quest entity = new Quest( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
            cursor.getShort(offset + 3) != 0, // completed
            difficultyLevelConverter.convertToEntityProperty(cursor.getString(offset + 4)), // difficultyLevel
            new java.util.Date(cursor.getLong(offset + 5)), // dateAdded
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)) // expiryDate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Quest entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCompleted(cursor.getShort(offset + 3) != 0);
        entity.setDifficultyLevel(difficultyLevelConverter.convertToEntityProperty(cursor.getString(offset + 4)));
        entity.setDateAdded(new java.util.Date(cursor.getLong(offset + 5)));
        entity.setExpiryDate(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Quest entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Quest entity) {
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
