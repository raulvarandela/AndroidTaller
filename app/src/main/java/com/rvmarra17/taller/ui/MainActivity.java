package com.rvmarra17.taller.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        final ListView TAREAS = this.findViewById(R.id.servicos);
        inserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });
        this.registerForContextMenu(TAREAS);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        this.getMenuInflater().inflate(R.menu.menu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId()) {
            case R.id.op_insertar_tarea:
                this.insertar();
                break;
            case R.id.salir:
                this.finish();
                break;
            default:
                Toast.makeText(this, "Option item?", Toast.LENGTH_LONG);
                break;
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.servicos) {
            this.getMenuInflater().inflate(R.menu.contextual_lista, menu);

        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);

        boolean toret = false;

        if (item.getItemId() == R.id.op_modificar_tareas_contextual) {
            this.insertar();
            toret = true;
        }

        return toret;
    }


    @Override
    protected void onPause() {
        super.onPause();
        //Guardar los servicios del taller
        final SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = prefs.edit();

        final boolean[] SERVICIOS = this.taller.getServicios();
        final StringBuilder STR_SERVICIOS = new StringBuilder();

        for (int i = 0; i < SERVICIOS.length; i++) {
            if (SERVICIOS[i]) {
                STR_SERVICIOS.append(i);
                STR_SERVICIOS.append(" ");
            }
        }

        prefsEditor.putString("servicios", STR_SERVICIOS.toString());
        prefsEditor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Recuperar los servicios del taller
        this.taller.eliminaServicios();
        final SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
        final String SERVICIOS = prefs.getString("servicios", "");
        final String[] STR_SERVICIOS = SERVICIOS.split(" ");

        if (STR_SERVICIOS.length == 1 && STR_SERVICIOS[0].equals("")) {
            taller.contrataServicio(0, true);
        } else {
            for (String str_servicio : STR_SERVICIOS) {
                if (!str_servicio.isEmpty()) {
                    int indice_servicio = Integer.parseInt(str_servicio);
                    this.taller.contrataServicio(indice_servicio, true);
                }
            }
        }
        this.actualiza();
    }
}