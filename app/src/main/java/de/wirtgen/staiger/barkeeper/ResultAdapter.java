package de.wirtgen.staiger.barkeeper;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.MainThread;
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


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolderFav> {

    private String[] mDataSet;
    private Map<Cocktail, String> map_Cocktails_Strings;
    private Map<View, Cocktail> map_View_Cocktail;
    private Context context;
    private long languageID;


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
                    Cocktail selectedCocktail = map_View_Cocktail.get(v);

                    if(context instanceof ResultActivity){
                        ((ResultActivity) context).showCocktailDetail(selectedCocktail);
                    }


                }
            });
            textView = (TextView) v.findViewById(R.id.item_textview_cocktail);
        }

        public TextView getTextView() {
            return textView;
        }
    }



    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public ResultAdapter(Map<Cocktail, String> cocktails, Context c, long lID) {
        this.map_Cocktails_Strings = cocktails;
        this.mDataSet = getStringArrFromMap();
        this.map_View_Cocktail = new HashMap<>();
        this.context = c;
        this.languageID = lID;
    }

    private String[] getStringArrFromMap(){
        List<String> listStringFavs = new ArrayList<>();
        for (String s : map_Cocktails_Strings.values()){
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
                .inflate(R.layout.list_item_cocktail, viewGroup, false);
        return new ViewHolderFav(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolderFav viewHolder, final int position) {
        Log.d("BarkeeperApp", "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(mDataSet[position]);

        Cocktail c = getRightCocktail(mDataSet[position]);

        this.map_View_Cocktail.put(viewHolder.itemView, c);

        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.imageview_listitem_cocktail);


        Log.d("BarkeeperApp", "Package: " + BuildConfig.APPLICATION_ID);
        Log.d("BarkeeperApp", "Picture: " + c.getUrlPicture());


        int imageID = context.getResources().getIdentifier(c.getUrlPicture(), "drawable", context.getPackageName());
        imageView.setImageDrawable(context.getResources().getDrawable(imageID));
        imageView.setVisibility(View.VISIBLE);

        TextView tv = (TextView) viewHolder.itemView.findViewById(R.id.item_textview_cocktail_ingredients);
        tv.setText(c.getIngredientsForCocktail(this.languageID));

    }

    private Cocktail getRightCocktail(String s){
        for (Map.Entry<Cocktail, String> e : map_Cocktails_Strings.entrySet()){
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

