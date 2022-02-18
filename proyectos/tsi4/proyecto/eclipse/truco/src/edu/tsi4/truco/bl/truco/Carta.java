package edu.tsi4.truco.bl.truco;

import edu.tsi4.truco.util.Logger;

public class Carta {

	public static int PALO_BASTO = 1;
	public static int PALO_ORO = 2;
	public static int PALO_ESPADA = 3;
	public static int PALO_COPA = 4;
								//  1,2,3,4,5,6,7,10,11,12
	private static int[] valores = {13,8,9,0,1,2,11,4,5,6, // espadas
									7,8,9,0,1,2,10,4,5,6,   // oros
									12,8,9,0,1,2,3,4,5,6,  //bastos
									7,8,9,0,1,2,3,4,5,6   //copas
									};
	
	int valor;
	int palo;
	boolean fueJugada = false;
	
	
	public int valor(Carta muestra){
		if(pieza(muestra)){
			if(valor == 2)
				return 30;
			if(valor == 4)
				return 29;
			if(valor == 5)
				return 28;
			if(valor == 10)
				return muestra.valor(muestra);
			return 27;
		}
		else{
			if(valor > 7)
				return 0;
			return valor;
		}
	}
	
	public boolean pieza(Carta muestra){
		if(this.valor == 10) // es un rey
			return muestra.pieza(muestra) && palo == muestra.palo;
		if(this.valor == 2 || this.valor == 4 || this.valor == 5 || this.valor == 9 || this.valor == 8)
			return palo == muestra.palo;
		return false;
	}
	
	public boolean mata(Carta muestra){
		if(this.palo == Carta.PALO_ESPADA) // es un rey
			return this.valor == 1 || this.valor == 7;
		if(this.valor == 7)
			return palo == Carta.PALO_ORO;
		if(this.valor == 1)
			return palo == Carta.PALO_BASTO;
		return false;
	}
	// res > 0 si this > c, res = 0 si son iguales, res < 0 si c > this
	public int comparar(Carta c, Carta muestra){
		if(this.pieza(muestra) || c.pieza(muestra))
			return this.valor(muestra) - c.valor(muestra);
		int posThis = Integer.valueOf(this.toSprite()).intValue();		
		int posC = Integer.valueOf(c.toSprite()).intValue();
		Logger.Log(Logger.INFO, this.getClass(), "</////////>carta creador valor:"+valores[posThis]+" valor inv:"+valores[posC]+"res:"+(valores[posThis] - valores[posC])+"<--");
		return valores[posThis] - valores[posC]; 
	}

	public String toSprite() {
		// retorno el string con el numero del sprite de la carta
		int val = valor-1;
		if(palo == PALO_ORO)
			val += 10;
		else if(palo == PALO_BASTO)
			val += 20;
		else if(palo == PALO_COPA)
			val += 30;
		return String.valueOf(val);
	}
	
	public static Carta toCarta(String sprite) {
		// retorno el string con el numero del sprite de la carta
		Carta carta = new Carta();
		int c = Integer.valueOf(sprite).intValue();
		carta.valor = (int)(c%10) +1; // valor va del 1 al 10
		if(c < 10)
			carta.palo = Carta.PALO_ESPADA;
		else if(c < 20)
			carta.palo = Carta.PALO_ORO;
		else if(c < 30)
			carta.palo = Carta.PALO_BASTO;
		else
			carta.palo = Carta.PALO_COPA;
		return carta;
	}
}
