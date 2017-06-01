package com.example.narcis.zvonnecontrol.adaptori;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.narcis.zvonnecontrol.R;
import com.example.narcis.zvonnecontrol.obiecte.pizza;

;
import java.util.List;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptorpizza extends ArrayAdapter<pizza>  {

    private int layoutResource;
    private pizza loc;
    private View view;

    public adaptorpizza(Context context, int layoutResource, List<pizza> pizzalist) {
        super(context, layoutResource, pizzalist);
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
            ingrediente.setText(loc.getIngrediente());
            TextView b1=(TextView) view.findViewById(R.id.butonpizza);
            TextView gramaj=(TextView) view.findViewById(R.id.pizzzagramaj);
            gramaj.setText(loc.getGramaj());
            b1.setText(loc.getPret()+" lei");

        }

        return view;
    }



}