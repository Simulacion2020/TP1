package sample.model;

import java.text.DecimalFormat;

public class Estadistica {

    public static float obtenerLimiteSuperior(float aListaValores[])
    {
        float maximo = 0;

        for (int i = 0; i < aListaValores.length; i++)
            if (maximo < aListaValores[i])
                maximo = aListaValores[i];

        return maximo;
    }

    public static float obtenerLimiteInferior(float aListaValores[])
    {
        float minimo = aListaValores[0];

        for (int i = 1; i < aListaValores.length; i++)
            if (minimo > aListaValores[i])
                minimo = aListaValores[i];

        return minimo;
    }

    /*
        calcula los limites inferiores y superiores de cada rango y lo devuelve en una matriz
        de aIntervalos*2 donde:
            intervalos[nroIntervalo][0]=LimiteInferior;
            intervalos[nroIntervalo][0]=LimiteSuperior;
     */
    public static float[][] definirIntervalos(float aListaValores[], int aIntervalos)
    {
        //variable de retorno
        float intervalos[][] = new float[aIntervalos][2];
        float maximo = 1;//obtenerLimiteSuperior(aListaValores);
        float minimo = 0;//obtenerLimiteInferior(aListaValores);
        float rango = 1;//(float) Math.ceil(maximo - minimo);
        float amplitudIntervalo = definirPrecision(rango / aIntervalos);
        float limiteInferior = 0;//minimo - (rango - (maximo - minimo))/2;
        for (int i = 0; i < aIntervalos; i++) {
            intervalos[i][0] = limiteInferior;
            limiteInferior = definirPrecision(limiteInferior + amplitudIntervalo);
            intervalos[i][1] = limiteInferior;
        }

        return intervalos;
    }

    public static float definirPrecision(float aFlotante)
    {
        float resultado;
        final int PRECISION = 10000;

        resultado = (float) Math.floor(aFlotante*PRECISION)/PRECISION;
        return resultado;
    }
    public static float[][] definirIntervalos(float aLimiteInferior, float aLimiteSuperior, int aIntervalos)
    {
        //variable de retorno
        float intervalos[][] = new float[aIntervalos][2];
        float rango = (float) Math.ceil(aLimiteSuperior - aLimiteInferior);
        float amplitudIntervalo = rango / (float) aIntervalos;
        float limiteInferior = aLimiteInferior - (rango + aLimiteInferior - aLimiteSuperior)/2;

        for (int i = 0; i < aIntervalos; i++) {
            intervalos[i][0] = limiteInferior;
            limiteInferior += amplitudIntervalo;
            intervalos[i][1] = limiteInferior;
        }

        return intervalos;
    }

    public static boolean PasaPrueba(int k, float valorPrueba)
    {
        int gradosLibertad = k - 1;

        float[] tabla = {3.84f, 5.99f, 7.81f, 9.49f, 11.1f, 12.6f, 14.1f, 15.5f, 16.9f, 18.3f, 19.7f, 21.0f, 22.4f, 23.7f, 25.0f, 26.3f, 27.6f, 28.9f, 30.1f, 32.7f, 33.9f, 35.2f, 36.4f, 37.7f, 38.9f, 40.1f, 41.3f, 42.6f, 43.8f};

        if (gradosLibertad == 0)
            return false;

        if (tabla[gradosLibertad-1] >= valorPrueba )
            return true;
        else
            return false;
    }


    public static int[] definirTablaDeFrecuencias(float[][] aIntervalos, float[] aListaValores)
    {
        //variable de retorno
        int[] frecuencias = new int[aIntervalos.length];
        //inicializamos los contadores
        for (int i = 0; i < frecuencias.length; i++) {
            frecuencias[i] = 0;
        }

        boolean b;
        for (int i = 0; i < aListaValores.length; i++)
        {
            // Si es menor o igual al maximo valor del rango se analiza a cual pertenece.
            // el limite inferior se analizara en la primera iteracion del ciclo de comparacion.
            // Con esta comparacion inicial reducimos la cantidad de operaciones para mejorar
            // la performance en largas listas de valores a analizar y aseguramos el conteo
            // en caso que el valor sea igual al limite superior
            if (aListaValores[i] <= aIntervalos[aIntervalos.length-1][1])
            {
                frecuencias[aIntervalos.length-1]++;
                // Se analiza fila por fila, con el primet limite inferior de un intervalo
                // se actualiza el contador correspondiente
                for (int j = 1; j < aIntervalos.length; j++)
                {
                    if ((aListaValores[i] < aIntervalos[j][0]))
                    {
                        frecuencias[j-1]++;
                        frecuencias[aIntervalos.length-1]--;
                        break;
                    }
                }
            }
            else
                System.out.println(i);
        }

        return frecuencias;
    }

    public static float calcularDesviaciones(float aFrecuenciaEsperada, int aFrecuenciaObservada)
    {
        float estadisticoPrueba = 0;
        float diferencia = aFrecuenciaEsperada - aFrecuenciaObservada;
        float cuadrado = (float) Math.pow(diferencia, 2);
        if (aFrecuenciaEsperada != 0)
            estadisticoPrueba = cuadrado / aFrecuenciaEsperada;

        return estadisticoPrueba;
    }

    public static float[] calcularDesviaciones(float[] aFrecuenciasEsperadas, int[] aFrecuenciasObservadas)
    {
        float[] estadisticoPrueba = new float[aFrecuenciasEsperadas.length];

        for (int i = 0; i < aFrecuenciasEsperadas.length; i++) {
            estadisticoPrueba[i] = definirPrecision(calcularDesviaciones(aFrecuenciasEsperadas[i], aFrecuenciasObservadas[i]));
        }

        return estadisticoPrueba;
    }

    public static float[] calcularDesviacionesAcumuladas(float[] aDesviaciones)
    {
        float[] desviacionesAcumuladas = new float[aDesviaciones.length];
        desviacionesAcumuladas[0] = aDesviaciones[0];
        for (int i = 1; i < desviacionesAcumuladas.length; i++) {
            desviacionesAcumuladas[i] = desviacionesAcumuladas[i-1] + aDesviaciones[i];
        }

        return desviacionesAcumuladas;
    }

    public static float calcularEstadisticoPrueba(float[] aFrecuenciasEsperadas, int[] aFrecuenciasObservadas)
    {
        float[] desviaciones = calcularDesviaciones(aFrecuenciasEsperadas, aFrecuenciasObservadas);
        float estadisticoPrueba = calcularEstadisticoPrueba(desviaciones);
        return estadisticoPrueba;
    }

    public static float calcularEstadisticoPrueba(float[] aDesviaxiones)
    {
        float estadisticoPrueba = 0;

        for (int i = 0; i < aDesviaxiones.length; i++) {
            estadisticoPrueba += aDesviaxiones[i];
        }

        return estadisticoPrueba;
    }
}
