package com.example.dp.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dp.Model.House;
import com.example.dp.Model.HouseLab;
import com.example.dp.R;
import com.example.dp.ViewPagerActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> implements Filterable
{
    private List<House> houses;
    private List<House> housesFilter;
    private Context context;
    private Typeface tf;
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public HouseAdapter(List<House> houses, Context context) {
        this.houses = houses;
        this.context = context;
        this.housesFilter=houses;
        HouseLab.Destroy();
        HouseLab.get(context).AddHouses(houses);
      //  tf = Typeface.createFromAsset(ge.getAssets(), "font/prodsans.ttf");
    }

    public List<House> getHouses()
    {
        return houses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.house_item, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //ниже замена houses на housesFilter
        final House house = housesFilter.get(i);
        viewHolder.title.setText(house.getTitle());
        viewHolder.district.setText(house.getDistrict_title());
        viewHolder.area.setText(house.getArea());
        viewHolder.price.setText(currencyFormat.format(Integer.parseInt(house.getPrice_total())));
        Picasso.get().load(house.getPicture_path()).into(viewHolder.image);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewPagerActivity.newIntent(context,house.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        //ниже замена houses на housesFilter
        return housesFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    housesFilter = houses;
                } else {
                    ArrayList<House> filteredList = new ArrayList<>();
                    for (House row : houses) {
                        if (row.getTitle().indexOf(charString) != -1)
                        //if (row.getTitle().contains(charString))
                        {
                            filteredList.add(row);
                        }
                    }

                    housesFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = housesFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                housesFilter = (ArrayList<House>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView district;
        private TextView area;
        private TextView price;
        private ImageView image;
        private CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            title.setTypeface(tf);
            district=(TextView)itemView.findViewById(R.id.district);
            district.setTypeface(tf);
            area=(TextView)itemView.findViewById(R.id.area);
            area.setTypeface(tf);
            price=(TextView)itemView.findViewById(R.id.price);
            price.setTypeface(tf);
            image=(ImageView)itemView.findViewById(R.id.image);
            cv=itemView.findViewById(R.id.cv);
        }

    }

    public interface HouseAdapterListener {
        void onHouseSelected(House contact);
    }
}
