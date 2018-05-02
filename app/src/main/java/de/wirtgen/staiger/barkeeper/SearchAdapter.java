package de.wirtgen.staiger.barkeeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolderFav> implements Filterable{

    private String[] mDataSet;
    private Map<Cocktail, String> map_favorites;
    private Map<View, Cocktail> map_View_Favs;
    private Context context;
    private List<String> mDataSetFiltered;
    private SearchAdapterListener listener;


    public class ViewHolderFav extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolderFav(View v) {
            super(v);

            Log.d("BarkeeperApp", "Added Item");

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageView iv = (ImageView) v.findViewById(R.id.imageview_listitem);
                    Log.d("BarkeeperApp", "Element " + getAdapterPosition() + " clicked.");
                    Cocktail selectedCocktail = map_View_Favs.get(v);

                    TextView tv = (TextView) v.findViewById(R.id.item_textview_ingredient);
                    //listener.onSelected(tv.getText().toString());

                    if(context instanceof SearchActivity){
                        Log.d("BarkeeperApp", "Search try Callback");
                        ((SearchActivity) context).showCocktailDetail(selectedCocktail);
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
    public SearchAdapter(Map<Cocktail, String> cocktails, Context c) {
        this.map_favorites = cocktails;
        this.mDataSet = getStringArrFromMap();
        this.map_View_Favs = new HashMap<>();
        this.context = c;
        this.mDataSetFiltered = new ArrayList<>();
    }

    private String[] getStringArrFromMap(){
        List<String> listStringFavs = new ArrayList<>();
        for (String s : map_favorites.values()){
            listStringFavs.add(s);
        }
        Collections.sort(listStringFavs);
        return listStringFavs.toArray(new String[listStringFavs.size()]);
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
        viewHolder.getTextView().setText(mDataSetFiltered.get(position));

        Cocktail c = getRightCocktail(mDataSetFiltered.get(position));

        this.map_View_Favs.put(viewHolder.itemView, c);

        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.imageview_listitem);
        if (c.getIsFavourite()){
            imageView.setImageResource(R.drawable.icons8starfilled96);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private Cocktail getRightCocktail(String s){
        for (Map.Entry<Cocktail, String> e : map_favorites.entrySet()){
            if (e.getValue().equals(s)){
                return e.getKey();
            }
        }
        return null;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //return mDataSet.length;
        return mDataSetFiltered.size();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataSetFiltered.clear();
                     for (String s : mDataSet){
                         mDataSetFiltered.add(s);
                     }
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (String row : mDataSet) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mDataSetFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataSetFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataSetFiltered = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface SearchAdapterListener {
        void onSelected(String s);
    }
}
