package de.wirtgen.staiger.barkeeper;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Generated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Staiger/Wirtgen on 19.03.2018.
 */

@Entity
public class Ingredient {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    private Boolean isForbidden;

    @ToMany
    @JoinEntity(
            entity = Recipes.class,
            sourceProperty = "ingredientID",
            targetProperty = "cocktailID"
    )
    private List<Recipes> recipe;

    @ToMany
    @JoinEntity(
            entity = LanguagesTexts.class,
            sourceProperty = "ingredientID",
            targetProperty = "languageID"
    )
    private List<LanguagesTexts> text;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 942581853)
    private transient IngredientDao myDao;

    @Generated(hash = 1776925014)
    public Ingredient(Long id, @NotNull Boolean isAvailable,
            @NotNull Boolean isForbidden) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.isForbidden = isForbidden;
    }

    @Generated(hash = 1584798654)
    public Ingredient() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsForbidden() {
        return this.isForbidden;
    }

    public void setIsForbidden(Boolean isForbidden) {
        this.isForbidden = isForbidden;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1552641681)
    public List<Recipes> getRecipe() {
        if (recipe == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RecipesDao targetDao = daoSession.getRecipesDao();
            List<Recipes> recipeNew = targetDao._queryIngredient_Recipe(id);
            synchronized (this) {
                if (recipe == null) {
                    recipe = recipeNew;
                }
            }
        }
        return recipe;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 444356711)
    public synchronized void resetRecipe() {
        recipe = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 114820642)
    public List<LanguagesTexts> getText() {
        if (text == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LanguagesTextsDao targetDao = daoSession.getLanguagesTextsDao();
            List<LanguagesTexts> textNew = targetDao._queryIngredient_Text(id);
            synchronized (this) {
                if (text == null) {
                    text = textNew;
                }
            }
        }
        return text;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1948229807)
    public synchronized void resetText() {
        text = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public List<Ingredient> getAllIngredients(){
        return this.daoSession.getIngredientDao().loadAll();
    }

    public Map<Ingredient, String> getAllIngredientsWithLangauge(Long languageID){
        List<Ingredient> l = this.daoSession.getIngredientDao().loadAll();
        Map<Ingredient, String> output = new HashMap<>();
        for (Ingredient i : l){
            output.put(i, this.getIngredientsName(languageID));
        }
        return output;
    }

    public String getIngredientsName(Long languageID){
        QueryBuilder<LanguagesTexts> qb = this.daoSession.getLanguagesTextsDao().queryBuilder();
        qb.where(LanguagesTextsDao.Properties.IngredientID.eq(this.getId()));
        List<LanguagesTexts> lt = qb.list();
        LanguagesTexts t = lt.get(0);
        //Log.d("DoaDB","Loading Ingredient: " + this.getId() +  " Size of Text: " + lt.size()+ " text: " + t.getText());
        return t.getText();
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1386056592)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getIngredientDao() : null;
    }

}
