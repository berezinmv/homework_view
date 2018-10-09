package com.example.maxim.homework_view;

public class Station {
    private final String mName;
    private final int mColor;

    public Station(String name, int color) {
        mName = name;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public int getColor() {
        return mColor;
    }
}
