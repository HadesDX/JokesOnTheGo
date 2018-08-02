package com.mtw.diego.jokesonthego.explorer;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mtw.diego.jokesonthego.Explore;
import com.mtw.diego.jokesonthego.entity.Joke;

public class EndlessPagerAdapter extends PagerAdapter {

    private final static String TAG = "JokesEndlessPagerAdapte";
    private SectionsPagerAdapter adapter;
    private static Joke currentJoke;

    public EndlessPagerAdapter(SectionsPagerAdapter adapter, ViewPager viewPager) {
        this.adapter = adapter;
        viewPager.addOnPageChangeListener(new SwapPageListener(viewPager));
    }

    @Override
    public int getCount() {
        return adapter.getCount() + 2;
    }

    public Joke getCurrentJoke() {
        return currentJoke;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (adapter.getCount() < 2) {
            //Log.i(TAG, "Request Instance position: " + position);
            adapter.instantiateItem(container, position);
        }

        int newPosition;
        if (position == 0) {
            newPosition = adapter.getCount() - 1;
        } else if (position >= getCount() - 1) {
            newPosition = 0;
        } else {
            newPosition = position - 1;
        }
        //Log.i(TAG, "Request Instance position: " + position + " new: " + newPosition);
        return adapter.instantiateItem(container, newPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Log.i(TAG, "Request Destroy position: " + position);
        if (position < 0 || position > adapter.getCount() - 1) {
            return;
        }
        //Log.i(TAG, "DO Destroy position: " + position);
        //adapter.destroyItem(container, position, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return adapter.getPageTitle(position);
    }

    @Override
    public float getPageWidth(int position) {
        return adapter.getPageWidth(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentJoke = ((Explore.PlaceholderFragment) object).internalJoke;
        adapter.setPrimaryItem(container, position, object);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return adapter.getItemPosition(object);
    }

    private class SwapPageListener implements ViewPager.OnPageChangeListener {

        private ViewPager viewPager;

        SwapPageListener(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                PagerAdapter pagerAdapter = viewPager.getAdapter();
                if (pagerAdapter != null) {
                    int itemCount = pagerAdapter.getCount();
                    if (itemCount < 2) {
                        return;
                    }
                    int index = viewPager.getCurrentItem();
                    if (index == 0) {
                        viewPager.setCurrentItem(itemCount - 2, false);
                        //Log.i(TAG, "Request Swap current position: " + index + " to: " + (itemCount - 2));
                    } else if (index == itemCount - 1) {
                        viewPager.setCurrentItem(1, false);
                        //Log.i(TAG, "Request Swap current position: " + index + " to: " + 1);
                    }
                }
            }
        }
    }
}