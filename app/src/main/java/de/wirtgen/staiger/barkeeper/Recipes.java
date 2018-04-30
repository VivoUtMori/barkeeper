package de.wirtgen.staiger.barkeeper;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Staiger/Wirtgen on 19.03.2018.
 */

@Entity
public class Recipes {

    @Id
    private Long id;

    private Long cocktailID;

    private Long ingredientID;

    private Integer units; //in ml, for example 200ml Rum

    @Generated(hash = 93566827)
    public Recipes(Long id, Long cocktailID, Long ingredientID, Integer units) {
        this.id = id;
        this.cocktailID = cocktailID;
        this.ingredientID = ingredientID;
        this.units = units;
    }

    @Generated(hash = 2016500507)
    public Recipes() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCocktailID() {
        return this.cocktailID;
    }

    public void setCocktailID(Long cocktailID) {
        this.cocktailID = cocktailID;
    }

    public Long getIngredientID() {
        return this.ingredientID;
    }

    public void setIngredientID(Long ingredientID) {
        this.ingredientID = ingredientID;
    }

    public Integer getUnits() {
        return this.units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public static Map<Cocktail, String> getRecipesForCocktailCheckIngredients(DaoSession ds, long languageID){
        List<Cocktail> cocktailList = ds.getCocktailDao().loadAll();
        Map<Cocktail, String> avaibleCocktails = new HashMap<>();

        QueryBuilder<Recipes> qbr;
        QueryBuilder<Ingredient> qbi;

        for (Cocktail c : cocktailList) {
            boolean isAvaible = true;
            Log.d("BarkeeperApp", "Cocktail: " + c.getId());
            qbr = ds.getRecipesDao().queryBuilder();
            qbr.where(RecipesDao.Properties.CocktailID.eq(c.getId()));
            List<Recipes> cocktailrecipe = qbr.list();

            for (Recipes r : cocktailrecipe) {
                qbi = ds.getIngredientDao().queryBuilder();
                qbi.where(IngredientDao.Properties.Id.eq(r.ingredientID));
                Log.d("BarkeeperApp", "--> R IngID: " + r.ingredientID);
                List<Ingredient> selectedIngredient = qbi.list();
                if(selectedIngredient.size() == 1){
                    Log.d("BarkeeperApp", "---> IngID: " + selectedIngredient.get(0).getId());
                    if (!selectedIngredient.get(0).getIsAvailable()){
                        isAvaible = false;
                    }
                    if (selectedIngredient.get(0).getIsForbidden()){
                        isAvaible = false;
                    }
                }
                else{
                    Log.d("BarkeeperApp", "More than one Ingredient selected or NULL!");
                }

            }
            if (isAvaible){
                avaibleCocktails.put(c, c.getCocktailsName(languageID));
            }
        }
        return avaibleCocktails;
    }
}
