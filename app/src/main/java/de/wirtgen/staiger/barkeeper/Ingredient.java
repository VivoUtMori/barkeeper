package de.wirtgen.staiger.barkeeper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by skull3r7 on 19.03.2018.
 */

@Entity
public class Ingredient {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String name;

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

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 942581853)
    private transient IngredientDao myDao;


    @Generated(hash = 1876105483)
    public Ingredient(Long id, @NotNull String name, @NotNull Boolean isAvailable,
            @NotNull Boolean isForbidden) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1386056592)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getIngredientDao() : null;
    }

}
