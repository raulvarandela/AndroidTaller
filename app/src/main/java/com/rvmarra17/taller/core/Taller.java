package com.rvmarra17.taller.core;

import java.util.ArrayList;
import java.util.Arrays;

public class Taller {
    public static final String[] SERVICIOS = new String[]{
            "Revison motor",
            "Cambio de aceite",
            "Equelibrado de ruedas"
    };

    public static final double[] COSTES = new double[]{
            9.99,
            29.99,
            4.99
    };

    public Taller() {
        this.serviosContratados = new boolean[SERVICIOS.length];
        this.serviosContratados[0] = true;

    }

    public double getTotalServicios() {
        double toret = 0f;

        for (int i = 0; i < COSTES.length; i++) {
            if (this.serviosContratados[i]) toret += COSTES[i];
        }
        return toret;
    }

    public void contrataServicio(int i, boolean value) {
        serviosContratados[i] = value;
    }

    private boolean[] serviosContratados;

    public String[] getServiciosAsString() {
        ArrayList<String> toret = new ArrayList<>();

        for (int i = 0; i < SERVICIOS.length; i++) {
            if (serviosContratados[i]) {
                toret.add(SERVICIOS[i]);
            }
        }

        return toret.toArray(new String[0]);
    }

    public boolean[] getServicios() {
        //return serviosContratados;
        return Arrays.copyOf(serviosContratados, serviosContratados.length);

    }

    public void eliminaServicios(){
        /*for (int i = 0;i<serviosContratados.length;i++){
            this.serviosContratados[i] = false;
        }*/
        Arrays.fill(serviosContratados,false);
    }
}
