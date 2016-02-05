package com.example.model;

import com.example.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "QUEST".
 */
public class Quest {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private long difficultyId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient QuestDao myDao;

    private Difficulty difficulty;
    private Long difficulty__resolvedKey;


    public Quest() {
    }

    public Quest(Long id) {
        this.id = id;
    }

    public Quest(Long id, String title, String description, Boolean completed, long difficultyId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.difficultyId = difficultyId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getQuestDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public long getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(long difficultyId) {
        this.difficultyId = difficultyId;
    }

    /** To-one relationship, resolved on first access. */
    public Difficulty getDifficulty() {
        long __key = this.difficultyId;
        if (difficulty__resolvedKey == null || !difficulty__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DifficultyDao targetDao = daoSession.getDifficultyDao();
            Difficulty difficultyNew = targetDao.load(__key);
            synchronized (this) {
                difficulty = difficultyNew;
            	difficulty__resolvedKey = __key;
            }
        }
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            throw new DaoException("To-one property 'difficultyId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.difficulty = difficulty;
            difficultyId = difficulty.getId();
            difficulty__resolvedKey = difficultyId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
