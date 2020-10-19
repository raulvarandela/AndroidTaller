package com.rvmarra17.taller.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rvmarra17.taller.R;
import com.rvmarra17.taller.core.Taller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializar taller
        taller = new Taller();

        //inicializar las vistas
        final Button inserta = this.findViewById(R.id.inserta);
        inserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });

        this.actualiza();
    }


    private void actualiza() {
        actualizarLista();
        actualizaTotal();
    }

    private void actualizaTotal() {
        final TextView total = this.findViewById(R.id.total);
        total.setText(String.format("Total: %4.2f€", taller.getTotalServicios()));
    }

    private void actualizarLista() {
        final ListView servicios = this.findViewById(R.id.servicos);
       /*ArrayAdapter<String> adap = new ArrayAdapter<>(
                this,
                //android.R.layout.simple_list_item_1,
                R.layout.entrada_servicio,
                taller.getServiciosAsString()
        );*/

        AdaptadorServicios adap = new AdaptadorServicios(this, this.taller.getServiciosAsString());

        servicios.setAdapter(adap);

    }

    private void insertar() {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMultiChoiceItems(
                Taller.SERVICIOS,
                this.taller.getServicios(),
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        taller.contrataServicio(which, isChecked);
                    }
                }
        );

        dlg.setPositiveButton("Añadir",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actualiza();
                    }
                });

        dlg.create().show();
    }


    private Taller taller;
}