package com.example.appssm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServidorContexto {

    public ServidorContexto() {
    }

    public static String cambiarHorario(String strHorario, double intervalo) {
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
}
