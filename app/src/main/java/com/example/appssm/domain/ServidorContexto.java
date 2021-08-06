package com.example.appssm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServidorContexto {

    public ServidorContexto() {
    }

    //    crear receta - crearReceta()
    //    checar codigo QR de medicamento - checarQR()
    //    revisar fechas (que receta y medicamento esten en rango de fechas) - revisarFechas()
    //    modificar fechas (en caso de cambio por retraso de aplicacion de medicamento) - modificarFechas()
    //    revisar hora de aplicacion (para notificacion) - revisarHrAplicacion()

    //    modificar hora de aplicacion (nueva hora de aplicacion del medicamento)
    public static String modificarHrAplicacion(String strHorario, double intervalo) {
        int tmp = (int) (intervalo * 60.0);
        String[] time = new String[3];
        int[] intHorario = new int[2];
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("hh:mm");

        time = strHorario.split(":");

        for (int i = 0; i < 2; i++) {
            intHorario[i] = Integer.valueOf(time[i]);
        }

        cal.set(Calendar.HOUR_OF_DAY, intHorario[0]);
        cal.set(Calendar.MINUTE, intHorario[1]);
        cal.add(Calendar.MINUTE, tmp);

        return df.format(cal.getTime());
    }

    //    revisar dependencia (relacion entre medicamentos) - revisarDependencia()
    //    revisar prioridad (indice de gravedad por no aplicar un medicamento) - revisarPrioridad()
    //    actualizar historial - actualizarHistorial()
}
