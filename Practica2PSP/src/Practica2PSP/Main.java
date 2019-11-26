package Practica2PSP;

import java.util.Scanner;

//Implementamos Runnable para poder crear hilos sobras instancias
public class Main implements Runnable {
	private static Carrera[] pc;
	static Scanner scan = new Scanner(System.in);
	private static Thread[] hilo;
	static int participantes = 0, posicionesTotales = 0;
	boolean ganador = true;
	Carrera pcAux;
	int primero;

	public void run() {
		while (ganador) {
			for (int i = 1; i <= participantes; i++) {
				if (Thread.currentThread().getName().equals("Camello " + i)) {
					gestionaPosicionesCarrera(pc[i].tirada(), pc[i]);
				}
			}
		}
	}

	// Imprime y maneja el avance de los participantes
	public synchronized void gestionaPosicionesCarrera(int posicionesTirada, Carrera pos) {
		if (pos.getPosicionesLibres() > 0) {

			// Si la tirada es 0, el camello descansará
			if (posicionesTirada == 0) {
				System.out.println(Thread.currentThread().getName() + " se toma un descanso");
			}

			// Si no, avanzará
			else {
				System.out.println(Thread.currentThread().getName() + " avanza " + posicionesTirada + " posiciones");
			}

			pos.posicionesRestantes(posicionesTirada);

			System.out.println(Thread.currentThread().getName() + " se encuentra en la posición: "
					+ (posicionesTotales - pos.getPosicionesLibres() + " de " + posicionesTotales));
			primero = pos.getMax(pc);
			if (pc[primero].getPosicionesLibres() == posicionesTotales) {
				System.out.println("Los participantes se lo están tomando con calma");
				System.out.println();
			} else {
				if (Thread.currentThread().getName().equals(hilo[primero].getName())) {
					// Si el camello llega a la meta lo imprime por pantalla
					if (posicionesTotales <= (posicionesTotales - pos.getPosicionesLibres())) {
						System.err.println(Thread.currentThread().getName() + " HA LLEGADO A LA META!");
						System.out.println();
					} else {
						// Si el camello va el primero lo imprime por pantalla
						System.out.println(Thread.currentThread().getName() + " va en cabeza!");
						System.out.println();
					}
				} else {
					System.out.println("Está a " + (pos.getPosicionesLibres() - pc[primero].getPosicionesLibres())
							+ " posiciones de " + hilo[primero].getName() + ", que lidera la carrera");
					System.out.println();
				}
				// Dormimos el hilo durante un segundo y medio
				try {
					Thread.sleep(1500);
					// Capturamos excepcion
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			// Si la posición actual es igual o superior la total, el camello ha llegado a
			// la meta y cerramos los hilos
			if (pos.getPosicionesLibres() <= 0) {
				System.out.println();
				System.err.println("¡TENEMOS GANADOR! " + Thread.currentThread().getName() + " SE LLEVA EL PREMIO ");
				System.out.println();
				// Obtenemos podio
				getPodio(pc, hilo);
				// Cerramos hilos
				System.exit(1);
			}
		}
	}

	// Se piden los datos para empezar la carrera
	public static void main(String[] args) {
		System.out.println("Número de Camellos: ");
		participantes = scan.nextInt();
		System.out.println("Longitud de la carrera: ");
		posicionesTotales = scan.nextInt();
		pc = new Carrera[participantes + 1];
		hilo = new Thread[participantes + 1];
		Main objAg = new Main();
		for (int i = 1; i <= participantes; i++) {
			hilo[i] = new Thread(objAg);
			hilo[i].setName("Camello " + i);
			hilo[i].start();
			pc[i] = new Carrera();
		}
		System.out.println();
	}

	public static int getLongitudCarrera() {
		// TODO Auto-generated method stub
		return posicionesTotales;
	}

	// Ordena los camellos al final de la carrera para ver el resultado
	public void getPodio(Carrera[] podio, Thread[] camel) {
		int n = podio.length;
		Carrera temp = new Carrera();
		Thread temp2 = new Thread();

		for (int i = 1; i <= n; i++) {
			for (int j = 2; j <= (n - i); j++) {
				if (podio[j - 1].getPosicionesLibres() > podio[j].getPosicionesLibres()) {
					temp = podio[j - 1];
					podio[j - 1] = podio[j];
					podio[j] = temp;

					temp2 = camel[j - 1];
					camel[j - 1] = camel[j];
					camel[j] = temp2;
				}
			}
		}
		// Imprimimos clasificación
		System.out.println("\nCLASIFICACIÓN");
		System.out.println("------------------------------------------------------------------");
		for (int i = 1; i < n; i++) {
			if (podio[i].getPosicionesLibres() <= 0) {
				System.out.println(i + "º: " + camel[i].getName());
			} else {
				System.out.println(i + "º: " + camel[i].getName() + " - Posiciones restantes para llegar a la meta: "
						+ podio[i].getPosicionesLibres() + " de " + posicionesTotales);
			}

		}
		System.out.println("------------------------------------------------------------------");
	}
}