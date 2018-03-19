package de.wirtgen.staiger.barkeeper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by skull3r7 on 19.03.2018.
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

}
