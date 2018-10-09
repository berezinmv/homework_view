package com.example.maxim.homework_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Station> mTopStations;
    private List<Station> mBottomStations;
    private RowView mTopRowView;
    private RowView mBottomRowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        StationsContainer stations = getStations();
        mTopStations = stations.getTop();
        mBottomStations = stations.getBottom();

        mTopRowView = findViewById(R.id.top_row_view);
        mBottomRowView = findViewById(R.id.bottom_row_view);

        populateRowView(mTopRowView, mTopStations);
        populateRowView(mBottomRowView, mBottomStations);
    }

    private View createButton(Station station) {
        AppCompatButton stationView = new AppCompatButton(this);

        stationView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        stationView.setPadding(20, 10, 20, 10);
        stationView.setText(station.getName());
        stationView.setTextColor(getResources().getColor(android.R.color.white));
        stationView.setBackgroundColor(getResources().getColor(station.getColor()));

        return stationView;
    }

    private void populateRowView(final RowView rowView, List<Station> stations) {
        for (final Station station : stations) {
            View stationView = createButton(station);
            stationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup oldParentView;
                    ViewGroup newParentView;
                    List<Station> removeFrom;
                    List<Station> addTo;
                    if (v.getParent() == mTopRowView) {
                        oldParentView = mTopRowView;
                        newParentView = mBottomRowView;
                        removeFrom = mTopStations;
                        addTo = mBottomStations;
                    } else {
                        oldParentView = mBottomRowView;
                        newParentView = mTopRowView;
                        removeFrom = mBottomStations;
                        addTo = mTopStations;
                    }
                    oldParentView.removeView(v);
                    newParentView.addView(v);
                    removeFrom.remove(station);
                    addTo.add(station);

                }
            });

            rowView.addView(stationView);
        }
    }

    private StationsContainer getStations() {
        Object container = getLastCustomNonConfigurationInstance();
        if (container != null) {
            return (StationsContainer) container;
        }

        List<Station> top = new ArrayList<>();
        top.add(new Station("Сокол", R.color.green));
        top.add(new Station("Войковская", R.color.green));
        top.add(new Station("Аэропорт", R.color.green));
        top.add(new Station("Динамо", R.color.green));
        top.add(new Station("Белорусская", R.color.green));
        top.add(new Station("Охотный ряд", R.color.red));
        top.add(new Station("Университет", R.color.red));
        top.add(new Station("Спортивная", R.color.red));
        top.add(new Station("Саларьево", R.color.red));
        top.add(new Station("Кропоткинская", R.color.red));
        top.add(new Station("Кунцевская", R.color.blue));
        top.add(new Station("Строгино", R.color.blue));
        top.add(new Station("Крылатское", R.color.blue));
        top.add(new Station("Молодежная", R.color.blue));
        top.add(new Station("Парк победы", R.color.blue));
        top.add(new Station("Боровицкая", R.color.grey));
        top.add(new Station("Полянка", R.color.grey));
        top.add(new Station("Тульская", R.color.grey));
        top.add(new Station("Нагатинская", R.color.grey));
        top.add(new Station("Нагорная", R.color.grey));
        List<Station> bottom = new ArrayList<>();
        return new StationsContainer(top, bottom);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return new StationsContainer(mTopStations, mBottomStations);
    }
}
