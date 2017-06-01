package com.example.narcis.zvonnecontrol.adaptori;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.narcis.zvonnecontrol.EvenimentActivity;
import com.example.narcis.zvonnecontrol.R;
import com.example.narcis.zvonnecontrol.obiecte.eveniment;
import com.example.narcis.zvonnecontrol.obiecte.pizza;

import java.util.List;

/**
 * Created by Narcis on 6/1/2017.
 */

public class adaptoreveniment extends ArrayAdapter<eveniment> {

    private int layoutResource;
    private eveniment loc;
    private View view;

    public adaptoreveniment(Context context, int layoutResource, List<eveniment> evenimentList) {
        super(context, layoutResource, evenimentList);
        this.layoutResource = layoutResource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }
        loc = getItem(position);
        if (loc != null) {
            TextView tip=(TextView)view.findViewById(R.id.pizzanume);
            TextView ingrediente=(TextView)view.findViewById(R.id.pizzaingrediente);
            tip.setText(loc.getTip());
            ingrediente.setText(loc.get());
            TextView b1=(TextView) view.findViewById(R.id.butonpizza);
            TextView gramaj=(TextView) view.findViewById(R.id.pizzzagramaj);
            gramaj.setText(loc.getGramaj());
            b1.setText(loc.getPret()+" lei");

        }

        return view;
    }

