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
        float maximo = obtenerLimiteSuperior(aListaValores);
        float minimo = obtenerLimiteInferior(aListaValores);
        float rango = (float) Math.ceil(maximo - minimo);
     //   float rango =1;
        float amplitudIntervalo = definirPrecision(rango / aIntervalos);
        float limiteInferior = minimo - (rango - (maximo - minimo))/2;
      //  float limiteInferior = 0;
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

        resultado = (float) Math.floor(aFlotante*PRECISION);
        aFlotante = resultado/PRECISION;
        return resultado/PRECISION;
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


    public static int[] definirTablaDeFrecuencias(float[][] aIntervalos, float[] aListaValores)
    {
        //variable de retorno
        int[] frecuencias = new int[aIntervalos.length];
        //inicializamos los contadores
        for (int i = 0; i < frecuencias.length; i++) {
            frecuencias[i] = 0;
        }

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
