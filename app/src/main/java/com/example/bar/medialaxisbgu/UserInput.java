package com.example.bar.medialaxisbgu;

import android.util.Pair;

/**
 * Created by Bar on 13/01/2017.
 */
public class UserInput {
    public Pair coordinates;
    public boolean out_of_mask;
    public int id_of_image;

    UserInput(float x, float y, boolean out_of_mask, int id) {
        this.coordinates = Pair.create(x, y);
        this.out_of_mask = out_of_mask;
        this.id_of_image = id;
    }
}
