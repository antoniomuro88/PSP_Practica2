package Practica2PSP;

import java.util.Random;

class Carrera {
	// Tomamos la longitud de carrera indicada por teclado
	private int posicionesTotales = Main.getLongitudCarrera();

	public void posicionesRestantes(int posicionesTirada) {
		posicionesTotales = posicionesTotales - posicionesTirada;
	}
	
	public int getPosicionesLibres() {
		return posicionesTotales;
	}

	// Genera la tirada de cada camello y sus puntos
	public int tirada() {
		Random r = new Random();
		int puntos = 0;
		int min = 0;
		int max = 99;
		int result = r.nextInt(max - min) + min;
		//30% d probababilidad de sacar 0 puntos
		if (result >= 0 && result < 30) {
			puntos = 0;
			//40% d probabilidad de sacar 1 puntos
		} else if (result >= 30 && result < 70) {
			puntos = 1;
			//20% d probabilidad de sacar 2 puntos
		} else if (result >= 70 && result < 90) {
			puntos = 2;
			//10% d probabilidad de sacar 3 puntos
		} else {
			puntos = 3;
		}
		return puntos;
	}

	// Devuelve el indice del primer camello
	public int getMax(Carrera[] lista) {
		int maxIndice = 1;
		for (int i = 1; i < lista.length; i++) {
			int nuevo = lista[i].getPosicionesLibres();
			if ((nuevo <= lista[maxIndice].getPosicionesLibres())) {
				maxIndice = i;
			}
		}
		return maxIndice;
	}
}