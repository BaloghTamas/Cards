package hu.bute.daai.amorg.bata.cards.adapter;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hu.bute.daai.amorg.bata.cards.R;
import hu.bute.daai.amorg.bata.cards.model.City;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>
{

    private final Context context;
    private final LayoutInflater inflater;

    private List<City> cities;

    private int lastPosition = -1;

    public CityAdapter(Context context, List<City> cities)
    {
        this.cities = cities;
        this.context = context;
        this.inflater = LayoutInflater.
                from(context);
    }

    @Override
    public int getItemCount()
    {
        return cities.size();
    }

    @Override
    public void onBindViewHolder(CityViewHolder cityViewHolder, int position)
    {
        City ci = cities.get(position);
        cityViewHolder.name.setText(ci.getName());

        Picasso.with(context).load(ci.getImageRes()).stableKey(ci.getName()).tag(cityViewHolder).into(cityViewHolder.image);
        setAnimation(cityViewHolder.card, position);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = inflater.inflate(R.layout.li_card, viewGroup, false);

        return new CityViewHolder(itemView);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void notifyItemDeleted()
    {
        lastPosition--;
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.image) ImageView image;
        @InjectView(R.id.name) TextView name;
        @InjectView(R.id.card_view) CardView card;

        public CityViewHolder(View v)
        {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

}
