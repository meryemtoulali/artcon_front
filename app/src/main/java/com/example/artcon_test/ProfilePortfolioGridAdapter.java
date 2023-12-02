package com.example.artcon_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ProfilePortfolioGridAdapter extends BaseAdapter {
    private Context context;
    private final int[] portfolioImages;
//        LayoutInflater inflter;
        public ProfilePortfolioGridAdapter(Context applicationContext, int[] portfolioImages) {
            this.context = applicationContext;
            this.portfolioImages = portfolioImages;
//            inflter = (LayoutInflater.from(applicationContext));
        }
        @Override
        public int getCount() {
            return portfolioImages.length;
        }
        @Override
        public Object getItem(int position) {
            return portfolioImages[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

    @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            View gridItemView;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                gridItemView = inflater.inflate(R.layout.profile_portfolio_grid_item, viewGroup, false);
            } else {
                gridItemView = convertView;
            }

            ImageView imageView = gridItemView.findViewById(R.id.preview); // assuming the ID is imageView
            imageView.setImageResource(portfolioImages[position]);

            return gridItemView;


        }

}
