package edu.tsi4.truco.bl.truco;

import java.util.Random;

public class Juego {
	// ESTADOS BASICOS
	public static int ESTADO_PARA_INICIAR = 1;
	public static int ESTADO_EN_JUEGO = 2;
	public static int ESTADO_PAUSADO = 3;
	public static int ESTADO_FINALIZADO = 4;
	
	
	String idJuego; // id del juego que lo identifica en el servidor
	int estado;
	int tantosCreador;
	int tantosInvitado;
	
	Usuario creador;
	Usuario invitado;
	Mano manoActiva;
	Mano manos[];
	private int cantManos = 0;
	public boolean creadorEsMano = false; //es pasada a true en la primera mano
	
	private Mano[] getManos(){
		if(manos == null)
			manos = new Mano[20];
		if(cantManos == manos.length){
			int nl = manos.length +20;
			Mano aux[] = new Mano[nl];
			for(int i = 0; i < manos.length;i++){
				aux[i] = manos[i];
			}
			manos = aux;
		}
		return manos;
	}
	
	public Mano iniciarMano(){
		//guardo la mano activa
		if(manoActiva != null){
			getManos()[cantManos] = manoActiva;
			cantManos++;
			tantosCreador = manoActiva.totalTantosCreador();
			tantosInvitado = manoActiva.totalTantosInvitado();
		}
		//creo la nueva mano
		manoActiva = new Mano();
		manoActiva.tantosCreador = tantosCreador;
		manoActiva.tantosInvitado = tantosInvitado;
		manoActiva.juego = this;
		manoActiva.jugadas = new Jugada[3];
		return manoActiva;
	}
	
	public void repartir(){
		Carta carta1 = null, carta2 = null, carta3 = null, carta4 = null, carta5 = null, carta6 = null;
		carta1 = getRandomCarta(carta1, carta2, carta3, carta4, carta5, carta6);
		carta2 = getRandomCarta(carta1, carta2, carta3, carta4, carta5, carta6);
		carta3 = getRandomCarta(carta1, carta2, carta3, carta4, carta5, carta6);
		carta4 = getRandomCarta(carta1, carta2, carta3, carta4, carta5, carta6);
		carta5 = getRandomCarta(carta1, carta2, carta3, carta4, carta5, carta6);
		carta6 = getRandomCarta(carta1, carta2, carta3, carta4, carta5, carta6);
		manoActiva.muestra = getRandomCarta(carta1, carta2, carta3, carta4, carta5, carta6);
		//asignamos las cartas
		creador.cartas = new Carta[3];
		invitado.cartas = new Carta[3];
		creador.cartas[0] = carta1;
		creador.cartas[1] = carta2;
		creador.cartas[2] = carta3;
		invitado.cartas[0] = carta4;
		invitado.cartas[1] = carta5;
		invitado.cartas[2] = carta6;
	}

	private Carta getRandomCarta(Carta carta1, Carta carta2, Carta carta3,
			Carta carta4, Carta carta5, Carta carta6) {
		// nos basamos en el sprite de cartas
		// espadas del 0 a 9
		// oros del 10 al 19
		// bastos del 20 al 29
		// copas del 30 al 39
		boolean fin = false;
		Carta carta = null;
		while(!fin){
			
			Random generator = new Random();
		    generator.setSeed(System.currentTimeMillis());
			long c = generator.nextInt() %40;
			if(c <0) c = c*(-1);
			carta = new Carta();
			carta.valor = (int)(c%10) +1; // valor va del 1 al 10
			if(c < 10)
				carta.palo = Carta.PALO_ESPADA;
			else if(c < 20)
				carta.palo = Carta.PALO_ORO;
			else if(c < 30)
				carta.palo = Carta.PALO_BASTO;
			else
				carta.palo = Carta.PALO_COPA;
			
			//controlamos que no sea igual a las otras cartas
			fin = true;
			if(carta1 != null && carta1.valor == carta.valor && carta1.palo == carta.palo)
				fin = false;
			if(carta2 != null && carta2.valor == carta.valor && carta2.palo == carta.palo)
				fin = false;
			if(carta3 != null && carta3.valor == carta.valor && carta3.palo == carta.palo)
				fin = false;
			if(carta4 != null && carta4.valor == carta.valor && carta4.palo == carta.palo)
				fin = false;
			if(carta5 != null && carta5.valor == carta.valor && carta5.palo == carta.palo)
				fin = false;
			if(carta6 != null && carta6.valor == carta.valor && carta6.palo == carta.palo)
				fin = false;
		}
		return carta;
	}
	/**@pre no tengo flor */
	public int calcularEnvido(Usuario u, Carta muestra){
		int envido = 0;
		
		// idea: recorro las cartas y si es muestra la sumo y la descarto, si es mayor que 7 la descarto (negras no suman), 
		// ordeno de mayor a menor
		// luego si quedo una carta la sumo
		// si quedaron dos y tengo pieza sumo la mayor, sino sumo las dos
		// si quedaron las tres, sumo las dos mayores
		
		return envido; 
		
	}
	
	
	public boolean tengoFlor(Usuario u, Carta muestra){
		int envido = 0;
		
		// idea: buscar si tengo 2 piezas, 1 pieza y dos del mismo palo, 3 del mismo palo
		
		return false; 
		
	}
	
	/**@pre tengo flor */
	public int calcularFlor(Usuario u, Carta muestra){
		int v1 = u.cartas[0].valor(muestra);
		int v2 = u.cartas[1].valor(muestra);
		int v3 = u.cartas[2].valor(muestra);
		int flor = 0;
		//busco la mayor pieza
		if(v1 >=27)
			flor = v1;
		if(v2 >=27 && v2 > v1)
			flor = v2;
		if(v3 >=27 && v3 > v2 && v3 > v1)
			flor = v3;
		if(flor == 0)// no tengo piezas, sumo los tres valores
			flor = v1+v2+v3;
		else{//tengo almenos una pieza
			int aux = 0;
			if(flor != v1)
				if(v1 >= 27)
					aux += v1-20;
				else
					aux += v1;
			if(flor != v2)
				if(v2 >= 27)
					aux += v2-20;
				else
					aux += v2;
			if(flor != v3)
				if(v3 >= 27)
					aux += v3-20;
				else
					aux += v3;
			
		}
		
		return flor; 
		
	}
	
	
}
