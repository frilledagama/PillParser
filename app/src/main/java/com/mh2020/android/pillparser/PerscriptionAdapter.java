package com.mh2020.android.pillparser;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class PerscriptionAdapter extends ArrayAdapter<Perscription> {
    /**
     * A custom constructor. Context inflates the layout file and the input list
     * is the list of words to display.
     * @param context The current context. Used to inflate the layout file.
     * @param perscriptionList A List of Perscription objects to display in a list
     */
    PerscriptionAdapter(Context context, List<Perscription> wordList){
        //Normally the second argument of the super constructor is
        //used when populating a single TextView.
        //WordAdapter uses two TextViews so this can be any number
        //Here we use 0.
        super(context,0,wordList);
    }

}
