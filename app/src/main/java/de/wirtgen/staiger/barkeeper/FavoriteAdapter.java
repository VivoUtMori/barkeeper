package de.wirtgen.staiger.barkeeper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolderFav> {

    private String[] mDataSet;
    private Map<Cocktail, String> map_favorites;
    private ArrayList<View> items;
    private List<Cocktail> favArrayList;
    private Map<View, Cocktail> map_View_Favs;
    private int k = 0;


    public class ViewHolderFav extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolderFav(View v) {
            super(v);

            items.add(v);
            Log.d("BarkeeperApp", "Added Item");

            Cocktail i = favArrayList.get(k);
            k++;

            map_View_Favs.put(v, i);


            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView iv = (ImageView) v.findViewById(R.id.imageview_listitem);
                    Log.d("BarkeeperApp", "Element " + getAdapterPosition() + " clicked.");
                    Cocktail selectedCocktail = map_View_Favs.get(v);
                    /*if (!selectedCocktail.getIsAvailable() && !selectedCocktail.getIsForbidden()){
                        iv.setImageResource(R.drawable.icons8checkmark96);
                        iv.setVisibility(View.VISIBLE);
                        selectedCocktail.setIsAvailable(true);
                    }*/

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
    public FavoriteAdapter(Map<Cocktail, String> cocktails) {
        this.map_favorites = cocktails;
        this.mDataSet = getStringArrFromMap();
        this.items = new ArrayList<>();
        this.favArrayList = new ArrayList<Cocktail>(cocktails.keySet());
        this.map_View_Favs = new HashMap<>();
    }

    private String[] getStringArrFromMap(){
        List<String> listStringIngredients = new ArrayList<>();
        for (String s : map_favorites.values()){
            listStringIngredients.add(s);
        }
        Collections.sort(listStringIngredients);
        return listStringIngredients.toArray(new String[listStringIngredients.size()]);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolderFav onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_ingredient, viewGroup, false);
        return new ViewHolderFav(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolderFav viewHolder, final int position) {
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
