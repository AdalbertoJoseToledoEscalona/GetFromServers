package modelo.utils.ordenamientos;

import java.util.List;

/*
 * quickSort.c
 *
 *  Created on: Mar 1, 2015
 *      Author: adalberto
 */
//#include <stdio.h>
//#include <time.h>

public class QuickSortMatriz {

	// Algoritmo quicksort recursivo
	private void doQuickSort(List<String[]> arreglo, int left_pos, int right_pos,
			int len_array, int indexOrder, boolean asc) {
		int pivotElementIndex/*, k, i*/;

		if (left_pos < right_pos) {
			pivotElementIndex = arrangeArray(arreglo, left_pos, right_pos,
					len_array, indexOrder, asc);

			// printf("izquierda: %d, derecha: %d", left_pos, right_pos);
			// printf("\nIndice del elemento pivote: %d\n",pivotElementIndex);
			// printf("------------------------------------------------------\n");
			// printf("\n");
			doQuickSort(arreglo, left_pos, pivotElementIndex - 1, len_array, indexOrder, asc);
			doQuickSort(arreglo, pivotElementIndex + 1, right_pos, len_array, indexOrder, asc);
		}
	}

	/*
	 * void printArray(int *myArray, int n, int pivotIndex, int leftIndex, int
	 * rightIndex) { int i; for(i = 0; i < n; i++) { if (i==leftIndex) {
	 * printf("("); } if (i==pivotIndex) { printf(" [%d] ", myArray[i]); } else
	 * { printf(" %d ", myArray[i]); } if (i==rightIndex) { printf(")"); } } }
	 */

	private String[] findPivotElement(List<String[]> arreglo, int left_pos,
			int right_pos, int len_array, int indexOrder) {
		int mid_point;
		String[] temp;
		//int k;
		mid_point = (left_pos + right_pos) / 2;
		// printf("\n------------ El Arreglo con Nuevo Pivote ---------\n");
		// printArray(myArray, len_array, mid_point, left_pos, right_pos);
		// printf("------------------------------------------------------\n");

		// Cambia el elemento más a la izquierda con el elemento del Punto
		// medio. Retorna el elemento más a la izquierda como el elemento
		// pivote a la función que lo llama
		temp = arreglo.get(left_pos);
		arreglo.set(left_pos, arreglo.get(mid_point));
		arreglo.set(mid_point, temp);

		return (arreglo.get(left_pos));
	}

	private int arrangeArray(List<String[]> arreglo, int left_pos, int right_pos,
			int len_array, int indexOrder, boolean asc) {
		int i, k;
		String[] temp;
		String[] pivotElement;

		// Obtiene el elemento pivote
		pivotElement = findPivotElement(arreglo, left_pos, right_pos, len_array, indexOrder);

		// Guarda la ubicación de la posición más a la izquierda
		// en una variable temporal
		k = left_pos;
		// Usando el elemento pivote y su ubicación encuentra la
		// ubicación correcta para el elemento pivote en el arreglo
		i = left_pos;
		while (i <= right_pos) {
			boolean entrar = false;
			if(asc){
				if (Double.parseDouble(pivotElement[indexOrder]) > Double.parseDouble(arreglo.get(i)[indexOrder])) {
					entrar = true;
				}
			}else{
				if (Double.parseDouble(pivotElement[indexOrder]) < Double.parseDouble(arreglo.get(i)[indexOrder])) {
					entrar = true;
				}
			}
			if(entrar){
				k++;
				if (k != i) {
					temp = arreglo.get(k);
					arreglo.set(k, arreglo.get(i));
					arreglo.set(i, temp);
					// printf("temp: %d, k: %d, i: %d\n", temp, k, i);
				}
				// printArray(myArray, len_array, k, left_pos, right_pos);
				// printf("\n");
			}
			i++;
		}
		temp = arreglo.get(left_pos);
		arreglo.set(left_pos, arreglo.get(k));
		arreglo.set(k, temp);
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
	public List<String[]> quickSort(List<String[]> arreglo, int n, int indexOrder, boolean asc) {
		//Date t_ini, t_fin;
		//double secs;
		//t_ini = new Date();
		doQuickSort(arreglo, 0, n - 1, n, indexOrder, asc);
		/* Ahora tenemos el arreglo ordenado */
		//t_fin = new Date();
		//secs = (double) (t_fin.getTime() - t_ini.getTime()); // CLOCKS_PER_SEC;
		//System.out.println((t_fin.getTime() - t_ini.getTime()) + " milisegundos");
		
		return arreglo;
		 
	}

	
	/*public static void main(String[] args) { double[] arreglo = new double[6]; arreglo[0] = 260;
	  arreglo[1] = -15; arreglo[2] = 20; arreglo[3] = 30; arreglo[4] = 11;
	  arreglo[5] = -19;
	  
	  new QuickSort().quickSort(arreglo, 6); }*/
	
	/*public static void main(String[] args) { 
		//String[][] arr = {{"a", "106"}, {"b", "101"}, {"c", "204"}, {"d", "210"}, {"e", "203"}, {"f", "201"}, {"g", "240"}, {"h", "168"}};
		List<String[]> arr = new ArrayList<String[]>();
		arr.add(new String[]{"a", "106"});
		arr.add(new String[]{"b", "101"});
		arr.add(new String[]{"c", "204"});
		arr.add(new String[]{"d", "210"});
		arr.add(new String[]{"e", "203"});
		arr.add(new String[]{"f", "201"});
		arr.add(new String[]{"g", "240"});
		arr.add(new String[]{"h", "168"});
		int indexOrder = 1;
		boolean asc = false;
		List<String[]> arreglo = new QuickSortMatriz().quickSort(arr, 8, indexOrder, asc);
	int i; for (i = 0; i < arreglo.size(); ++i) { System.out.println(arreglo.get(i)[indexOrder]); }
	}*/

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