package de.wirtgen.staiger.barkeeper;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by skull3r7 on 14.03.2018.
 */

@Entity
public class Cocktail {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String urlPicture;

    @NotNull
    private String preparation;

    private Boolean isFavourite;

    @ToMany
    @JoinEntity(
            entity = Recipes.class,
            sourceProperty = "cocktailID",
            targetProperty = "ingredientID"
    )
    private List<Recipes> recipe;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 745348896)
    private transient CocktailDao myDao;

    @Generated(hash = 2041986120)
    public Cocktail(Long id, @NotNull String name, @NotNull String description,
            @NotNull String urlPicture, @NotNull String preparation,
            Boolean isFavourite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.urlPicture = urlPicture;
        this.preparation = preparation;
        this.isFavourite = isFavourite;
    }

    @Generated(hash = 806485141)
    public Cocktail() {
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPicture() {
        return this.urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getPreparation() {
        return this.preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public Boolean getIsFavourite() {
        return this.isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 263015320)
    public List<Recipes> getRecipe() {
        if (recipe == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RecipesDao targetDao = daoSession.getRecipesDao();
            List<Recipes> recipeNew = targetDao._queryCocktail_Recipe(id);
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

    public Map<Ingredient, Integer> getAllIngredientsWithUnits(){
        Map<Ingredient, Integer> m = new HashMap<>();
        List<Recipes> recipse = this.daoSession.getRecipesDao().queryBuilder().where(RecipesDao.Properties.CocktailID.eq(this.getId())).list();
        for (Recipes r : recipse){
            Long iID = r.getIngredientID();
            List<Ingredient> l = this.daoSession.getIngredientDao().queryBuilder().where(IngredientDao.Properties.Id.eq(iID)).list();
            if (!l.isEmpty()) {
                Ingredient ing = l.get(0);
                m.put(ing, r.getUnits());
            }
        }
        return m;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 684466229)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCocktailDao() : null;
    }

}
