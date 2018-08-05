package com.mtw.diego.jokesonthego;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mtw.diego.jokesonthego.entity.Joke;
import com.mtw.diego.jokesonthego.explorer.EndlessPagerAdapter;
import com.mtw.diego.jokesonthego.explorer.SectionsPagerAdapter;
import com.mtw.diego.jokesonthego.helper.Host;
import com.mtw.diego.jokesonthego.helper.database.AppDatabase;
import com.mtw.diego.jokesonthego.helper.network.JokesService;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Explore extends AppCompatActivity implements SensorEventListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static SectionsPagerAdapter mSectionsPagerAdapter;
    private static EndlessPagerAdapter endlessPagerAdapter;
    private static String TAG = "JokesExplore";
    private static final JokesService jokesService = JokesService.getJokesService();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private float sensorVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        PlaceholderFragment.setJokesService(jokesService);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        endlessPagerAdapter = new EndlessPagerAdapter(mSectionsPagerAdapter, mViewPager);
        mViewPager.setAdapter(endlessPagerAdapter);
        mViewPager.setCurrentItem(1);
        ButterKnife.bind(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_explore, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    public void launchHome(MenuItem item) {
        finish();
    }

    @OnClick(R.id.fab)
    public void favorite(View view) {
        synchronized (this) {
            endlessPagerAdapter.getCurrentJoke().setFavorite(true);
            Observable.fromCallable(() -> {
                AppDatabase.getDatabase(this).jokeDao().updateJokes(endlessPagerAdapter.getCurrentJoke());
                return true;
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();

        }
        Snackbar.make(view, R.string.explore_save, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float sval = Math.abs(sensorEvent.values[1]);
            if (sval > sensorVal && sval > 2) {
                Log.i(TAG, "Sensor: " + sval);
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
            sensorVal = sval;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static JokesService jokesService;
        private static String TAG = "JokesPlaceholderFragment";
        private View rootView;
        public Joke internalJoke;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static void setJokesService(JokesService jokesService) {
            PlaceholderFragment.jokesService = jokesService;
        }

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        private synchronized void useJoke(Joke e) {
            TextView label = rootView.findViewById(R.id.section_label);
            TextView title = rootView.findViewById(R.id.title);
            TextView joke = rootView.findViewById(R.id.joke);
            joke.setText("");
            if (joke != null) {
                //Log.i(TAG, joke.toString());
            } else {
                //Log.i(TAG, "View not found");
            }
            joke.setText(e.getJoke());
            label.setText(e.getHost().getUrl());
            title.setText(e.getHost().getName());
        }

        private synchronized void getDBAndNet() {
            Disposable bd = AppDatabase.getDatabase(getContext()).jokeDao().findLessReadedJoke().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(e -> {
                //Log.i(TAG, "Database: " + e.toString());
                useJoke(e);
                internalJoke = e;
                e.setReadTimes(e.getReadTimes() + 1);
                Observable.fromCallable(() -> {
                    AppDatabase.getDatabase(getContext()).jokeDao().updateJokes(e);
                    return true;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
            });
            if (internalJoke == null) {
                Disposable net = jokesService.getJoke().subscribe(e -> {
                    //Log.i(TAG, "Remote: " + e.toString());
                    useJoke(e);
                    Observable.fromCallable(() -> {
                        AppDatabase.getDatabase(getContext()).jokeDao().insertAll(e);
                        return true;
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();

                    e.setReadTimes(e.getReadTimes() + 1);
                    Observable.fromCallable(() -> {
                        AppDatabase.getDatabase(getContext()).jokeDao().updateJokes(e);
                        //Log.i(TAG, "GUARDADO A BD");
                        return true;
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                }, err -> {
                    Log.e(TAG, err.getMessage());
                });
            } else {
                Disposable newJ = AppDatabase.getDatabase(getContext()).jokeDao().countAvaibleJokes(AppDatabase.MAX_NEW_JOKES)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(e -> {
                            if (e != null) {
                                Log.i(TAG, "Total not so old jokes: " + e.toString());
                                if (e < AppDatabase.MAX_NEW_JOKES) {
                                    Disposable net = jokesService.getJoke().subscribe(e2 -> {
                                        //Log.i(TAG, "Remote: " + e.toString());
                                        Observable.fromCallable(() -> {
                                            AppDatabase.getDatabase(getContext()).jokeDao().insertAll(e2);
                                            return true;
                                        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
                                    }, err -> {
                                        Log.e(TAG, err.getMessage());
                                    });
                                }
                            }
                        });
            }
            AppDatabase.doClean(getContext());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_explore, container, false);
            getDBAndNet();
            return rootView;
        }


        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (rootView == null) {
                return;
            }
            if (isVisibleToUser) {
                //Log.i(TAG, "Im visible, so im doing nothing :D");
            } else {
                getDBAndNet();
            }
        }
    }
}
