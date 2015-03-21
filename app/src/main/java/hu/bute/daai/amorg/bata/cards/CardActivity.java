package hu.bute.daai.amorg.bata.cards;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.bute.daai.amorg.bata.cards.adapter.CityAdapter;
import hu.bute.daai.amorg.bata.cards.model.City;
import hu.bute.daai.amorg.bata.cards.swipe.SwipeDismissRecyclerViewTouchListener;

public class CardActivity extends ActionBarActivity
{
    private List<City> citiesPredefined;
    private CityAdapter adapter;
    private ArrayList<City> citiesInList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        // Layout manager for vertical orientation
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        citiesPredefined = new ArrayList<>();
        citiesPredefined.add(new City("Budapest", R.drawable.res_budapest1));
        citiesPredefined.add(new City("London", R.drawable.res_london));
        citiesPredefined.add(new City("Paris", R.drawable.res_paris));
        citiesPredefined.add(new City("Copenhagen", R.drawable.res_copenhagen));

        citiesInList = new ArrayList<>();
        citiesInList.add(citiesPredefined.get(0));
        citiesInList.add(citiesPredefined.get(1));

        adapter = new CityAdapter(this, citiesInList);

        recyclerView.setAdapter(adapter);

        // Swipe to dismiss
        // Copyright: github/krossovochkin
        // https://github.com/krossovochkin/Android-SwipeToDismiss-RecyclerView

        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(
                        recyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks()
                        {
                            @Override
                            public boolean canDismiss(int position)
                            {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions)
                            {
                                for (int position : reverseSortedPositions)
                                {
                                    citiesInList.remove(position);
                                }
                                // do not call notifyItemRemoved for every item, it will cause gaps on deleting items
                                adapter.notifyDataSetChanged();
                                adapter.notifyItemDeleted();
                            }
                        });
        recyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener(touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Toast.makeText(getApplicationContext(), "Clicked " + citiesPredefined.get(position), Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_add)
        {
            //Add random city
            int randCityPos = ((int) (Math.random() * 100.0)) % 4;
            citiesInList.add(citiesPredefined.get(randCityPos));
            adapter.notifyItemInserted(citiesInList.size());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnItemClickListener
    {
        public void onItemClick(View view, int position);
    }

    public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener
    {
        GestureDetector mGestureDetector;
        private OnItemClickListener mListener;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener)
        {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onSingleTapUp(MotionEvent e)
                {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e)
        {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e))
            {
                mListener.onItemClick(childView, view.getChildPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent)
        {
        }
    }
}
