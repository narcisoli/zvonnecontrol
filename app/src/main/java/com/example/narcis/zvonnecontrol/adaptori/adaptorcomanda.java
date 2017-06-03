package com.example.narcis.zvonnecontrol.adaptori;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.narcis.zvonnecontrol.R;
import com.example.narcis.zvonnecontrol.obiecte.coman;

import java.util.List;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptorcomanda extends ArrayAdapter<coman> {


    private int layoutResource;
    private coman loc;


    public adaptorcomanda(Context context, int layoutResource, List<coman> pizzalist) {
        super(context, layoutResource, pizzalist);
        this.layoutResource = layoutResource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);

        }
        TextView data = (TextView) view.findViewById(R.id.adaptorrezervaredata);
        TextView numar = (TextView) view.findViewById(R.id.numar);
        TextView detalii = (TextView) view.findViewById(R.id.adaptorrezervaredetalii);

        TextView status = (TextView) view.findViewById(R.id.adaptoristoricstatus);


        loc = getItem(position);

        if (loc != null) {
            data.setText(loc.getData());
            numar.setText(loc.getNrdetelefon());
            detalii.setText(loc.getText()+"\n"+loc.getNume());
            if (loc.getStatus() == 1)
                status.setText("Neconfirmata");
            status.setTextColor(Color.WHITE);
            if (loc.getStatus() == 2) {
                status.setText("Confirmata");
                status.setTextColor(Color.CYAN);
            }
            if (loc.getStatus() == 3) {
                status.setText("La cuptor");
                status.setTextColor(Color.YELLOW);
            }
            if (loc.getStatus() == 4) {
                status.setText("Pe drum");
                status.setTextColor(Color.GREEN);
            }
            if (loc.getStatus() == 5) {
                status.setText("Finalizata");
                status.setTextColor(Color.GREEN);
            }
            if (loc.getStatus() == 0) {
                status.setText("Anulata");
                status.setTextColor(Color.RED);
            }

        }

        return view;
    }


}
