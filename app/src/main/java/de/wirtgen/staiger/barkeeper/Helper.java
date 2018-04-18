package de.wirtgen.staiger.barkeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Helper {

    public static int getRandomNumber(int inclusive, int exclusive){
        if (inclusive <= 1 && exclusive <= 1)
            return 1;
        Random r = new Random();
        return r.nextInt(exclusive - inclusive) + inclusive;
    }

    public static String[] getStringArrFromMap(Map<Object, String> map){
        List<String> listStringIngredients = new ArrayList<>();
        for (String s : map.values()){
            listStringIngredients.add(s);
        }
        Collections.sort(listStringIngredients);
        return listStringIngredients.toArray(new String[listStringIngredients.size()]);
    }

    public enum Component {
        COCKTAILNAME(1),
        COCKTAILDESCRIPTION(2),
        COCKTAILPREPERATION(3);

        private final long id;

        Component(long id){
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }

}
