package com.rvmarra17.taller.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rvmarra17.taller.R;



public class AdaptadorServicios extends ArrayAdapter {

    public AdaptadorServicios(@NonNull Context context, @NonNull Object[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            Activity activity = (Activity) this.getContext();
            final LayoutInflater INFLATER = activity.getLayoutInflater();
            convertView = INFLATER.inflate(R.layout.entrada_servicio,
                    null);
        }

        final String SERVICIO = (String) this.getItem(position);
        final TextView LBL_NOMBRE = convertView.findViewById(R.id.nombre);
        LBL_NOMBRE.setText(SERVICIO);

        return convertView;
    }
}