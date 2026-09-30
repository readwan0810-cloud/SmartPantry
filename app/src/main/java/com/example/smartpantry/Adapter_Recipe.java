package com.example.smartpantry;
import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.*;

public class Adapter_Recipe extends BaseAdapter {

    private Context context;
    private ArrayList<Recipes> recipes;

    public Adapter_Recipe(Context context, ArrayList<Recipes> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recipes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.recipe_ingredients, parent, false);
        }

        TextView txtRecipeName =
                convertView.findViewById(R.id.txtRecipeName);

        TextView txtRecipeStatus =
                convertView.findViewById(R.id.txtRecipeStatus);

        Recipes recipe = recipes.get(position);

        txtRecipeName.setText(recipe.getRecipeName());
        txtRecipeStatus.setText("Click item & view how to make it");

        return convertView;
    }
}
