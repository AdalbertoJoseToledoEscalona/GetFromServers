package modelo.utils.ordenamientos;

import java.util.Date;

/*
 * mergeSort.c
 *
 *  Created on: Mar 1, 2015
 *      Author: adalberto
 */

//#include <stdio.h>
//#include <time.h>
public class MergeSort {
	// # define MAX_SIZE 100
	private final static int MAX_SIZE = 100;

	/** Calcula el límite superior del subarreglo que se crea. **/
	private int calculateUpperBound(int lowerBound2, int size, int listSize) {
		if ((lowerBound2 + size - 1) < listSize) {
			return lowerBound2 + size - 1;
		} else {
			return listSize - 1;
		}
	}

	/**
	 * Esta función ordena los subarreglos inidividuales y los asigna al arreglo
	 * auxiliar, dummy
	 */
	private void sortAndAssign(int lb1, int lb2, int ub1, int ub2, int i,
			int j, int targetIndex, double[] arreglo, double dummy[]) {
		for (i = lb1, j = lb2; i <= ub1 && j <= ub2; targetIndex = targetIndex + 1) {
			if (arreglo[i] <= arreglo[j]) {
				dummy[targetIndex] = arreglo[i];
				i = i + 1;
			} else {
				dummy[targetIndex] = arreglo[j];
				j = j + 1;
			}
		}
	}

	/**
	 * Asignar los subarreglos ordenados al arreglo auxiliar, dummy. Hace la
	 * asignación de los subarreglos apropiadamente divididos mediante el
	 * procedimiento de ordenamiento por fusión.
	 */
	private void assignSub(int i, int ub, int targetIndex, double[] arreglo,
			double[] dummy) {
		// int numLoop = 0;

		while (i <= ub) {
			dummy[targetIndex] = arreglo[i++];
			targetIndex = targetIndex + 1;
		}
	}

	/**
	 * Asignar los subarreglos ordenados al arreglo auxiliar, dummy. Trabaja con
	 * la parte restante del subarreglo.
	 */
	private void assignRemaining(int lb1, int k, int n, double[] arreglo,
			double[] dummy) {
		int i;
		for (i = lb1; k < n; ++i) {
			dummy[k++] = arreglo[i];
		}
	}

	/**
	 * Toma como entrada el arreglo a ser ordenado y el número de elementos en
	 * el arreglo y controla el ordenamiento merge sort (por fusión) invocando a
	 * las funciones apropiadas. Estas funciones ayudan a crear particiones de
	 * tamaños apropiados durante cada pasada. También ayudan a calcular el
	 * límite superior de los subarreglos, a ordenar subarreglos, fusionarlos y
	 * asignarlos al arreglo auxiliar.
	 */
	public void mergeSort(double[] arreglo, int n) {
		Date t_ini, t_fin;
		// double secs;
		t_ini = new Date();
		double dummy[] = new double[MAX_SIZE];
		int i = 0, j = 0, targetIndex = 0;
		int /* loopCount = 0, */lowerBound1, lowerBound2;
		int upperBound1, upperBound2, size;

		size = 1;
		while (size < n) {
			lowerBound1 = 0;
			targetIndex = 0;
			while (lowerBound1 + size < n) {
				lowerBound2 = lowerBound1 + size;
				upperBound1 = lowerBound2 - 1;
				upperBound2 = calculateUpperBound(lowerBound2, size, n);
				sortAndAssign(lowerBound1, lowerBound2, upperBound1,
						upperBound2, i, j, targetIndex, arreglo, dummy);
				assignSub(i, upperBound1, targetIndex, arreglo, dummy);
				assignSub(j, upperBound2, targetIndex, arreglo, dummy);
				lowerBound1 = upperBound2 + 1;
			}
			assignRemaining(lowerBound1, targetIndex, n, arreglo, dummy);
			for (i = 0; i < n; ++i) {
				arreglo[i] = dummy[i];
			}
			size *= 2;
		}
		/* Ahora tenemos el arreglo ordenado */
		t_fin = new Date();
		// secs = (double) (t_fin.getTime() - t_ini.getTime()); //
		// CLOCKS_PER_SEC;
		System.out.println((t_fin.getTime() - t_ini.getTime())
				+ " milisegundos");

		// for (i = 0; i < n; ++i) { System.out.println(arreglo[i]); }
	}

	/*
	 * int main(int argc, char **argv) { int arreglo[6]; arreglo[0] = 260;
	 * arreglo[1] = -15; arreglo[2] = 20; arreglo[3] = 30; arreglo[4] = 11;
	 * arreglo[5] = -19;
	 * 
	 * mergeSort(arreglo, 6); }
	 */

	
	public static void main(String[] args) { double[] arr = {106, 101, 204, 210, 203, 201, 240, 168};
	  new MergeSort().mergeSort(arr, 8); /*system("pause");*/ }
	 

	/*public static void main(String[] args) {
		// srand(time(NULL)); //semilla
		double arreglo[] = new double[10000];
		int i;
		for (i = 0; i < 10000; ++i) {
			arreglo[i] = Math.random();
		}
		new MergeSort().mergeSort(arreglo, 10000);
	}*/
}