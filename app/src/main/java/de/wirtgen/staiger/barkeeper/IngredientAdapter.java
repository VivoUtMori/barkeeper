package de.wirtgen.staiger.barkeeper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Staiger/Wirtgen on 19.03.2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{

    private String[] mDataSet;
    private Map<Ingredient, String> map_ingredients;
    private Map<View, Ingredient> map_View_Ingredients;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);

            Log.d("BarkeeperApp", "Added Item");

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView iv = (ImageView) v.findViewById(R.id.imageview_listitem);
                    Log.d("BarkeeperApp", "Element " + getAdapterPosition() + " clicked.");
                    Ingredient selectedIngredient = map_View_Ingredients.get(v);
                    if (!selectedIngredient.getIsAvailable() && !selectedIngredient.getIsForbidden()){
                        iv.setImageResource(R.drawable.icons8checkmark96);
                        iv.setVisibility(View.VISIBLE);
                        selectedIngredient.setIsAvailable(true);
                    }
                    else if (selectedIngredient.getIsAvailable() && !selectedIngredient.getIsForbidden()){
                        iv.setImageResource(R.drawable.icons8delete96);
                        iv.setVisibility(View.VISIBLE);
                        selectedIngredient.setIsAvailable(false);
                        selectedIngredient.setIsForbidden(true);
                    }
                    else {
                        iv.setVisibility(View.INVISIBLE);
                        selectedIngredient.setIsAvailable(false);
                        selectedIngredient.setIsForbidden(false);
                    }

                }
            });
            textView = (TextView) v.findViewById(R.id.item_textview_ingredient);
        }

        public TextView getTextView() {
            return textView;
        }
    }



    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public IngredientAdapter(Map<Ingredient, String> ingredients) {
        this.map_ingredients = ingredients;
        this.mDataSet = getStringArrFromMap();
        this.map_View_Ingredients = new HashMap<>();
    }

    private String[] getStringArrFromMap(){
        List<String> listStringIngredients = new ArrayList<>();
        for (String s : map_ingredients.values()){
            listStringIngredients.add(s);
        }
        Collections.sort(listStringIngredients);
        return listStringIngredients.toArray(new String[listStringIngredients.size()]);


    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_ingredient, viewGroup, false);
        return new ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d("BarkeeperApp", "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(mDataSet[position]);

        Ingredient i = getRightIngredient(mDataSet[position]);
        this.map_View_Ingredients.put(viewHolder.itemView, i);

        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.imageview_listitem);
        if (i.getIsAvailable()){
            imageView.setImageResource(R.drawable.icons8checkmark96);
            imageView.setVisibility(View.VISIBLE);
        }
        if (i.getIsForbidden()){
            imageView.setImageResource(R.drawable.icons8delete96);
            imageView.setVisibility(View.VISIBLE);
        }
    }


    private Ingredient getRightIngredient(String s){
        for (Map.Entry<Ingredient, String> e : map_ingredients.entrySet()){
            if (e.getValue().equals(s)){
                return e.getKey();
            }
        }
        return null;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
