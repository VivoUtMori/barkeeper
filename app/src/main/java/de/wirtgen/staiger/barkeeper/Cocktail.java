package de.wirtgen.staiger.barkeeper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Staiger/Wirtgen on 14.03.2018.
 */

@Entity
public class Cocktail {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String urlPicture;

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

    @Generated(hash = 1982294495)
    public Cocktail(Long id, @NotNull String urlPicture, Boolean isFavourite) {
        this.id = id;
        this.urlPicture = urlPicture;
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

    public String getUrlPicture() {
        return this.urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
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

    public String getCocktailsName(Long languageID){
        QueryBuilder<LanguagesTexts> qb = this.daoSession.getLanguagesTextsDao().queryBuilder();
        qb.where(LanguagesTextsDao.Properties.CocktailID.eq(this.getId())).where(LanguagesTextsDao.Properties.ComponentID.eq((long)1));
        List<LanguagesTexts> lt = qb.list();
        LanguagesTexts t = lt.get(0);
        return t.getText();
    }

    public String getCocktailsDescription(Long languageID){
        QueryBuilder<LanguagesTexts> qb = this.daoSession.getLanguagesTextsDao().queryBuilder();
        qb.where(LanguagesTextsDao.Properties.CocktailID.eq(this.getId())).where(LanguagesTextsDao.Properties.ComponentID.eq((long)2));
        List<LanguagesTexts> lt = qb.list();
        LanguagesTexts t = lt.get(0);
        return t.getText();
    }

    public String getCocktailsPreperation(Long languageID){
        QueryBuilder<LanguagesTexts> qb = this.daoSession.getLanguagesTextsDao().queryBuilder();
        qb.where(LanguagesTextsDao.Properties.CocktailID.eq(this.getId())).where(LanguagesTextsDao.Properties.ComponentID.eq((long)3));
        List<LanguagesTexts> lt = qb.list();
        LanguagesTexts t = lt.get(0);
        return t.getText();
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 684466229)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCocktailDao() : null;
    }


}
