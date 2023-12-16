package com.example.artcon_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.artcon_test.model.PortfolioPost;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilePortfolioGridAdapter extends BaseAdapter {
    private Context context;
    private List<PortfolioPost> portfolio;

    public List<PortfolioPost> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<PortfolioPost> portfolio) {
        this.portfolio = portfolio;
    }

    //        LayoutInflater inflter;
        public ProfilePortfolioGridAdapter(Context applicationContext, List<PortfolioPost> portfolio) {
            this.context = applicationContext;
            this.portfolio = portfolio;
//            inflter = (LayoutInflater.from(applicationContext));
        }
        @Override
        public int getCount() {
            return portfolio.size();
        }
        @Override
        public Object getItem(int position) {
            return portfolio.get(position);
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

        ImageView imageView = gridItemView.findViewById(R.id.preview);
        Picasso.get()
                .load(portfolio.get(position).getMedia())
                .placeholder(R.drawable.picasso_placeholder)
                .into(imageView);
        // assuming the ID is imageView
//            imageView.setImageResource(portfolioImages[position]);

            return gridItemView;


        }

}
