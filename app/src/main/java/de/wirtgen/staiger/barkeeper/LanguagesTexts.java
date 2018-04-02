package de.wirtgen.staiger.barkeeper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Staiger/Wirtgen on 02.04.2018.
 */

@Entity
public class LanguagesTexts {

    @Id
    private Long id;

    private Long languageID;
    
    private Long cocktailID;

    private Long componentID;

    private Long ingredientID;

    private String text;

    @Generated(hash = 486375885)
    public LanguagesTexts(Long id, Long languageID, Long cocktailID,
            Long componentID, Long ingredientID, String text) {
        this.id = id;
        this.languageID = languageID;
        this.cocktailID = cocktailID;
        this.componentID = componentID;
        this.ingredientID = ingredientID;
        this.text = text;
    }

    @Generated(hash = 992683792)
    public LanguagesTexts() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLanguageID() {
        return this.languageID;
    }

    public void setLanguageID(Long languageID) {
        this.languageID = languageID;
    }

    public Long getCocktailID() {
        return this.cocktailID;
    }

    public void setCocktailID(Long cocktailID) {
        this.cocktailID = cocktailID;
    }

    public Long getComponentID() {
        return this.componentID;
    }

    public void setComponentID(Long componentID) {
        this.componentID = componentID;
    }

    public Long getIngredientID() {
        return this.ingredientID;
    }

    public void setIngredientID(Long ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
