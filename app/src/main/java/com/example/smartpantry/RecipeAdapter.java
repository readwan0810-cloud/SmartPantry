package com.example.smartpantry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Recipe> recipes;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
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
                    .inflate(R.layout.recipe_list_item, parent, false);
        }

        TextView txtRecipeName =
                convertView.findViewById(R.id.txtRecipeName);

        TextView txtRecipeStatus =
                convertView.findViewById(R.id.txtRecipeStatus);

        Recipe recipe = recipes.get(position);

        txtRecipeName.setText(recipe.getRecipeName());
        txtRecipeStatus.setText("Ready to cook with your pantry");

        return convertView;
    }
}
