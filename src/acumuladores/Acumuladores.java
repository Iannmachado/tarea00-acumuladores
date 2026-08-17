package acumuladores;

public class Acumuladores {

	/**
	 * Dada una matriz de enteros y un número, verifica si existe alguna fila 
	 * donde todos sus elementos sean múltiplos del número recibido por 
	 * parámetro.
	 * 
	 * Si la matriz está vacía o si el número no es positivo, devuelve falso.
	 * 
	 * @param mat
	 * @param num
	 * @return
	 */
	public boolean todosMultiplosEnAlgunaFila(int[][] mat, int num) {
		if (mat.length == 0 || num <= 0) {
			return false;
		}
		boolean existeFila = false;
		for (int i = 0; i < mat.length; i++) {
			boolean todosMultiplos = true;
			for (int j = 0; j < mat[i].length; j++) {
				todosMultiplos = todosMultiplos && (mat[i][j] % num == 0);
			}
			existeFila = existeFila || todosMultiplos;
		}
		return existeFila;
	}
	
	/**
	 * Dado 2 matrices se verifica si hay intersección entre las filas de cada
	 * matriz, fila a fila.
	 * 
	 * Si las matrices tienen distinta cantidad de filas o si alguna matriz 
	 * está vacía, devuelve falso.
	 * 
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public boolean hayInterseccionPorFila(int[][] mat1, int[][]mat2) {
		if (mat1.length == 0 || mat2.length == 0 || mat1.length != mat2.length) {
			return false;
		}
		boolean hayInterseccionEnTodas = true;
		for (int i = 0; i < mat1.length; i++) {
			boolean hayInterseccionFila = false;
			for (int j = 0; j < mat1[i].length; j++) {
				for (int k = 0; k < mat2[i].length; k++) {
					hayInterseccionFila = hayInterseccionFila || (mat1[i][j] == mat2[i][k]);
				}
			}
			hayInterseccionEnTodas = hayInterseccionEnTodas && hayInterseccionFila;
		}
		return hayInterseccionEnTodas;
	}
	
	/**
	 * Dada una matriz y el índice de una columna, se verifica si existe alguna
	 * fila cuya suma de todos sus elementos sea mayor estricto que la suma de
	 * todos los elementos de la columna indicada por parámetro.
	 * 
	 * Si el índice de la columna es inválido o la matriz está vacía, devuelve 
	 * falso.
	 * 
	 * @param mat
	 * @param nColum
	 * @return
	 */
	public boolean algunaFilaSumaMasQueLaColumna(int[][] mat, int nColum) {
		if (mat.length == 0 || nColum < 0 || nColum >= mat[0].length) {
			return false;
		}
		int sumaColumna = 0;
		for (int i = 0; i < mat.length; i++) {
			sumaColumna += mat[i][nColum];
		}
		boolean existeFilaMayor = false;
		for (int i = 0; i < mat.length; i++) {
			int sumaFila = 0;
			for (int j = 0; j < mat[i].length; j++) {
				sumaFila += mat[i][j];
			}
			existeFilaMayor = existeFilaMayor || (sumaFila > sumaColumna);
		}
		return existeFilaMayor;
	}
	
	/**
	 * Dadas 2 matrices, se verifica si hay intersección entre las columnas de
	 * cada matriz, columna a columna.
	 * 
	 * Si las matrices tienen distinta cantidad de columnas o alguna matriz 
	 * está vacía, devuelve falso. 
	 * 
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public boolean hayInterseccionPorColumna(int[][] mat1, int[][]mat2) {
		if (mat1.length == 0 || mat2.length == 0 || mat1[0].length != mat2[0].length) {
			return false;
		}
		boolean hayInterseccionEnTodas = true;
		for (int col = 0; col < mat1[0].length; col++) {
			boolean hayInterseccionColumna = false;
			for (int i = 0; i < mat1.length; i++) {
				for (int k = 0; k < mat2.length; k++) {
					hayInterseccionColumna = hayInterseccionColumna || (mat1[i][col] == mat2[k][col]);
				}
			}
			hayInterseccionEnTodas = hayInterseccionEnTodas && hayInterseccionColumna;
		}
		return hayInterseccionEnTodas;
	}
}
