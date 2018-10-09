package com.example.maxim.homework_view;

import java.util.List;

public class StationsContainer {
    private final List<Station> mTop;
    private final List<Station> mBottom;

    public StationsContainer(List<Station> top, List<Station> bottom) {
        mTop = top;
        mBottom = bottom;
    }

    public List<Station> getTop() {
        return mTop;
    }

    public List<Station> getBottom() {
        return mBottom;
    }
}
