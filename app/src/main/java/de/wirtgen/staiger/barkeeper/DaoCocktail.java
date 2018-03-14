package de.wirtgen.staiger.barkeeper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by skull3r7 on 14.03.2018.
 */

@Entity
public class DaoCocktail {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String ingredients;

    @NotNull
    private String urlPicture;

    @NotNull
    private String preparation;

    @Generated(hash = 818209330)
    public DaoCocktail(Long id, @NotNull String name, @NotNull String description,
            @NotNull String ingredients, @NotNull String urlPicture,
            @NotNull String preparation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.urlPicture = urlPicture;
        this.preparation = preparation;
    }

    @Generated(hash = 2006477019)
    public DaoCocktail() {
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

    public String getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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

}
