package sample.model;

public class Estadistica {

    static double obtenerLimiteSuperior(double aListaValores[])
    {
        double maximo = 0;

        for (int i = 0; i < aListaValores.length; i++)
            if (maximo < aListaValores[i])
                maximo = aListaValores[i];

        return maximo;
    }

    static double obtenerLimiteInferior(double aListaValores[])
    {
        double minimo = aListaValores[0];

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
    static double[][] definirIntervalos(double aListaValores[], int aIntervalos)
    {
        //variable de retorno
        double intervalos[][] = new double[aIntervalos][2];
        double maximo = obtenerLimiteSuperior(aListaValores);
        double minimo = obtenerLimiteInferior(aListaValores);
        double rango = Math.ceil(maximo - minimo);
        double amplitudIntervalo = rango / aIntervalos;
        double limiteInferior = minimo - (rango - (maximo - minimo))/2;

        for (int i = 0; i < aIntervalos; i++) {
            intervalos[i][0] = limiteInferior;
            limiteInferior += amplitudIntervalo;
            intervalos[i][1] = limiteInferior;
        }

        return intervalos;
    }

    static int[] definirTablaDeFrecuencias(double[][] aIntervalos, double[] aListaValores)
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
                // Se analiza fila por fila, con el primet limite inferior de un intervalo
                // se actualiza el contador correspondiente
                for (int j = 0; j < aIntervalos.length; j++)
                {
                    if ((aListaValores[i] <= aIntervalos[j][0]))
                    {
                        frecuencias[j]++;
                        break;
                    }
                }
            }
        }

        return frecuencias;
    }
}
