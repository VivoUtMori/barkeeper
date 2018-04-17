package de.wirtgen.staiger.barkeeper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Staiger/Wirtgen on 19.03.2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{

    private String[] mDataSet;
    private Map<Ingredient, String> ingredients;
    private ArrayList<View> items;
    private List<Ingredient> ingredientArrayList;
    private int k = 0;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);

            items.add(v);
            Log.d("BarkeeperApp", "Added Item");

            Ingredient i = ingredientArrayList.get(k);
            k++;

            Log.d("BarkeeperApp", "I: " + ingredients.get(i) + " IsAvailable: " + i.getIsAvailable() + " isForbidden: " + i.getIsForbidden());

            ImageView imageView = (ImageView) v.findViewById(R.id.imageview_listitem);
            if (i.getIsAvailable()){
                imageView.setImageResource(R.drawable.icons8checkmark96);
                imageView.setVisibility(View.VISIBLE);
            }
            if (i.getIsForbidden()){
                imageView.setImageResource(R.drawable.icons8delete96);
                imageView.setVisibility(View.VISIBLE);
            }

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("BarkeeperApp", "Element " + getAdapterPosition() + " clicked.");

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
        this.ingredients = ingredients;
        this.mDataSet = getStringArrFromMap();
        this.items = new ArrayList<>();
        this.ingredientArrayList = new ArrayList<Ingredient>(ingredients.keySet());
    }

    private String[] getStringArrFromMap(){
        List<String> listStringIngredients = new ArrayList<>();
        int i = 1;
        for (String s : ingredients.values()){
            listStringIngredients.add(i + " " + s);
            i++;
        }
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
