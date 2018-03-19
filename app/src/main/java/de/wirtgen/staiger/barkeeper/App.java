package de.wirtgen.staiger.barkeeper;

/**
 * Created by skull3r7 on 14.03.2018.
 */

import android.app.Application;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        //Create/Load DB
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,  "cocktail-db-encrypted");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        DaoMaster dm = new DaoMaster(db);
        daoSession = dm.newSession();


        // DB leeren
        dm.dropAllTables(db, true);
        dm.createAllTables(db, true);

        //daoSession.getDaoCocktailDao().deleteAll();


        Ingredient rum = new Ingredient();
        rum.setName("Brauner Rum");
        rum.setIsAvailable(true);
        rum.setIsForbidden(false);

        Ingredient vodka = new Ingredient();
        vodka.setName("Vodka");
        vodka.setIsForbidden(false);
        vodka.setIsAvailable(true);


        daoSession.getIngredientDao().insert(rum);
        daoSession.getIngredientDao().insert(vodka);

        Log.d("DaoDB", "Inserted Ingredient with ID and Name: " + rum.getId() + " " + rum.getName());
        Log.d("DaoDB", "Inserted Ingredient with ID and Name: " + vodka.getId() + " " + vodka.getName());



        Cocktail longIsland = new Cocktail();

        longIsland.setDescription("Bester Cocktail der Welt!");
        longIsland.setName("Long Island Iced Tea");
        longIsland.setUrlPicture("/test.png");
        longIsland.setPreparation("1) Alles zusammen kippen 2) Mixen 3) Saufen");

        daoSession.getCocktailDao().insert(longIsland);

        Log.d("DaoDB", "Inserted Cocktail with ID and Name: " + longIsland.getId() + " " + longIsland.getName());

        Recipes recipeLongIsland = new Recipes();
        recipeLongIsland.setCocktailID(longIsland.getId());
        recipeLongIsland.setIngredientID(rum.getId());
        recipeLongIsland.setUnits(30);

        Recipes recipeLongIsland2 = new Recipes();
        recipeLongIsland2.setCocktailID(longIsland.getId());
        recipeLongIsland2.setIngredientID(vodka.getId());
        recipeLongIsland2.setUnits(20);

        daoSession.getRecipesDao().insert(recipeLongIsland);
        daoSession.getRecipesDao().insert(recipeLongIsland2);


        Log.d("DaoDB", "Inserted RecipeEntry with: " + longIsland.getName() + " " + rum.getName() + " " + recipeLongIsland.getUnits());
        Log.d("DaoDB", "Inserted RecipeEntry with: " + longIsland.getName() + " " + vodka.getName() + " " + recipeLongIsland2.getUnits());


    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}