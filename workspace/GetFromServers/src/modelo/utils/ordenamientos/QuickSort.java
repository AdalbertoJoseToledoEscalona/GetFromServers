package modelo.utils.ordenamientos;


/*
 * quickSort.c
 *
 *  Created on: Mar 1, 2015
 *      Author: adalberto
 */
//#include <stdio.h>
//#include <time.h>

public class QuickSort {

	// Algoritmo quicksort recursivo
	private void doQuickSort(double[] arreglo, int left_pos, int right_pos,
			int len_array) {
		int pivotElementIndex/*, k, i*/;

		if (left_pos < right_pos) {
			pivotElementIndex = arrangeArray(arreglo, left_pos, right_pos,
					len_array);

			// printf("izquierda: %d, derecha: %d", left_pos, right_pos);
			// printf("\nIndice del elemento pivote: %d\n",pivotElementIndex);
			// printf("------------------------------------------------------\n");
			// printf("\n");
			doQuickSort(arreglo, left_pos, pivotElementIndex - 1, len_array);
			doQuickSort(arreglo, pivotElementIndex + 1, right_pos, len_array);
		}
	}

	/*
	 * void printArray(int *myArray, int n, int pivotIndex, int leftIndex, int
	 * rightIndex) { int i; for(i = 0; i < n; i++) { if (i==leftIndex) {
	 * printf("("); } if (i==pivotIndex) { printf(" [%d] ", myArray[i]); } else
	 * { printf(" %d ", myArray[i]); } if (i==rightIndex) { printf(")"); } } }
	 */

	private double findPivotElement(double[] arreglo, int left_pos,
			int right_pos, int len_array) {
		int mid_point;
		double temp;
		//int k;
		mid_point = (left_pos + right_pos) / 2;
		// printf("\n------------ El Arreglo con Nuevo Pivote ---------\n");
		// printArray(myArray, len_array, mid_point, left_pos, right_pos);
		// printf("------------------------------------------------------\n");

		// Cambia el elemento más a la izquierda con el elemento del Punto
		// medio. Retorna el elemento más a la izquierda como el elemento
		// pivote a la función que lo llama
		temp = arreglo[left_pos];
		arreglo[left_pos] = arreglo[mid_point];
		arreglo[mid_point] = temp;

		return (arreglo[left_pos]);
	}

	private int arrangeArray(double[] arreglo, int left_pos, int right_pos,
			int len_array) {
		int i, k;
		double temp;
		double pivotElement;

		// Obtiene el elemento pivote
		pivotElement = findPivotElement(arreglo, left_pos, right_pos, len_array);

		// Guarda la ubicación de la posición más a la izquierda
		// en una variable temporal
		k = left_pos;
		// Usando el elemento pivote y su ubicación encuentra la
		// ubicación correcta para el elemento pivote en el arreglo
		i = left_pos;
		while (i <= right_pos) {
			if (pivotElement > arreglo[i]) {
				k++;
				if (k != i) {
					temp = arreglo[k];
					arreglo[k] = arreglo[i];
					arreglo[i] = temp;
					// printf("temp: %d, k: %d, i: %d\n", temp, k, i);
				}
				// printArray(myArray, len_array, k, left_pos, right_pos);
				// printf("\n");
			}
			i++;
		}
		temp = arreglo[left_pos];
		arreglo[left_pos] = arreglo[k];
		arreglo[k] = temp;
		// printf("\n temp: %d, k: %d, i: %d\n", temp, k, i);
		// printArray(myArray, len_array, k, left_pos, right_pos);
		// printf("\n------------ El Arreglo Ordenado ----------------\n");
		// printArray(myArray, len_array, k, left_pos, right_pos);
		return k;
	}

	/* Programa QuickSort */
	/*
	 * Esta función sólo lanza el proceso de quicksort, invocando la función
	 * Do_Quick_Sort
	 */
	public void quickSort(double[] arreglo, int n) {
		//Date t_ini, t_fin;
		//double secs;
		//t_ini = new Date();
		doQuickSort(arreglo, 0, n - 1, n);
		/* Ahora tenemos el arreglo ordenado */
		//t_fin = new Date();
		//secs = (double) (t_fin.getTime() - t_ini.getTime()); // CLOCKS_PER_SEC;
		//System.out.println((t_fin.getTime() - t_ini.getTime()) + " milisegundos");
		
		  //int i; for (i = 0; i < n; ++i) { System.out.println(arreglo[i]); }
		 
	}

	
	/*public static void main(String[] args) { double[] arreglo = new double[6]; arreglo[0] = 260;
	  arreglo[1] = -15; arreglo[2] = 20; arreglo[3] = 30; arreglo[4] = 11;
	  arreglo[5] = -19;
	  
	  new QuickSort().quickSort(arreglo, 6); }*/
	
	/*public static void main(String[] args) { double[] arr = {106, 101, 204, 210, 203, 201, 240, 168};
	new QuickSort().quickSort(arr, 8);}*/

	/*public static void main(String[] args) {
		// srand(time(NULL)); //semilla
		double arreglo[] = new double[10000];
		int i;
		for (i = 0; i < 10000; ++i) {
			arreglo[i] = Math.random();
		}
		new QuickSort().quickSort(arreglo, 10000);
	}*/
}