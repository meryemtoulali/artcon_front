package com.example.artcon_test.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.PortfolioPost;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilePortfolioGridAdapter extends BaseAdapter {
    private Context context;

    private List<PortfolioPost> portfolio;
    private OnGridItemClickListener onGridItemClickListener;

    public interface OnGridItemClickListener {
        void onItemClick(int position);
    }

    public List<PortfolioPost> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<PortfolioPost> portfolio) {
        this.portfolio = portfolio;
    }

        public ProfilePortfolioGridAdapter(Context applicationContext, List<PortfolioPost> portfolio, OnGridItemClickListener listener) {
            this.context = applicationContext;
            this.portfolio = portfolio;
            this.onGridItemClickListener = listener;
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
                gridItemView = inflater.inflate(R.layout.item_profile_portfolio, viewGroup, false);
            } else {
                gridItemView = convertView;
            }

        ImageView imageView = gridItemView.findViewById(R.id.preview);
        Picasso.get()
                .load(portfolio.get(position).getMedia())
                .placeholder(R.drawable.picasso_placeholder)
                .into(imageView);
        gridItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGridItemClickListener != null) {
                    onGridItemClickListener.onItemClick(position);
                }
            }
        });

        return gridItemView;


        }

}
