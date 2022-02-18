package edu.tsi4.truco.bl.truco;

import edu.tsi4.truco.util.Logger;

public class Mano {
	
	public static final int GANA_CREADOR = 1;
	public static final int GANA_INVITADO = 2;
	public static final int ESTADO_FIN = 2;
	
	public boolean tocado = false;
	public boolean cantado = false;
	public boolean truco = false;
	public boolean retruco = false;
	public boolean vale4 = false;
	public boolean rabonCreador = false;
	public boolean turnoCreador = false;
	
	public int estado;
	public int tantosCreador  = 0;
	public int tantosInvitado = 0;
	public int florCreador = 0;
	public int florInvitado = 0;
	public int envidoCreador = 0;
	public int envidoInvitado = 0;
	public int trucoCreador = 0;
	public int trucoInvitado = 0;
	
	public Juego juego;
	public Jugada jugadas[];
	public Jugada enproceso;
	public int cant = 0;
	public Carta muestra;
	
	public int totalTantosCreador(){
		return tantosCreador + florCreador + envidoCreador + trucoCreador;
		
	}
	
	public int totalTantosInvitado(){
		return tantosInvitado + florInvitado + envidoInvitado + trucoInvitado;
		
	}
	
	public int jugarCarta(Carta c, Usuario u, boolean creador){
		int res = 0;
		if(enproceso == null)
			enproceso = new Jugada();
		
		if(creador)
			enproceso.cartaCreador = c;
		else
			enproceso.cartaInvitado = c;
		Logger.Log(Logger.INFO, this.getClass(), "-->jugar carta:"+enproceso.cartaCreador+"--"+enproceso.cartaInvitado);
		for(int i = 0; i <3; i++){
			if(u.cartas[i].fueJugada != true && u.cartas[i].valor == c.valor && u.cartas[i].palo == c.palo)
				u.cartas[i].fueJugada = true;
		}
		if(enproceso.cartaCreador != null && enproceso.cartaInvitado != null){
			res = enproceso.ganador(muestra); Logger.Log(Logger.INFO, this.getClass(), "-->se termina la mano:"+res+"<--");
			getJugadas()[cant] = enproceso;
			cant++;
			turnoCreador = enproceso.estado == Jugada.ESTADO_GANA_CREADOR || (enproceso.estado == Jugada.ESTADO_PARDA && juego.creadorEsMano);
			enproceso = null;				
		}
		if(cant>=2 && enproceso == null){
			// se jugaron todas las cartas, o gano el mismo las dos primeras.
			int aux = ganadorMano(); Logger.Log(Logger.INFO, this.getClass(), "-->mano todas las cartas, o 2 jugadas:"+res+"<--");
			if(aux > 0){
				res = aux;
				if(!truco){// no se grito nada, sumo un punto al ganador.
					if(res == Jugada.ESTADO_GANA_CREADOR)
						trucoCreador ++;
					else
						trucoInvitado ++;
				}
				else{
					if(res == Jugada.ESTADO_GANA_CREADOR){
						trucoCreador += 2;
						if(retruco) trucoCreador ++;
						if(vale4) trucoCreador ++;
					}
					else{
						trucoInvitado += 2;
						if(retruco) trucoInvitado ++;
						if(vale4) trucoInvitado ++;
					}
					
				}
				this.estado = Mano.ESTADO_FIN;
			}
			return res;
		}
		return res; // mano no termina
	}

	private int ganadorMano() {
		//recorro las jugadas y me fijo quien es el ganador
		int res = getJugadas()[0].estado;
		int res2 = getJugadas()[1].estado;
		int res3 = getJugadas()[2] != null?getJugadas()[2].estado:0;
		if(res == Jugada.ESTADO_PARDA)
			if(res2 == Jugada.ESTADO_PARDA)
				if(res3 == Jugada.ESTADO_PARDA)
					return juego.creadorEsMano? Jugada.ESTADO_GANA_CREADOR: Jugada.ESTADO_GANA_INVITADO;
				else
					return res3; //puede ser cero si todavia no se jugo
			else
				return res2;
		else if(res2 == Jugada.ESTADO_PARDA)
			return res;
		else if(res3 == Jugada.ESTADO_PARDA)
			return res;
		else if(res == res2 || res == res3) //si gana la primera y segunda, o primera y tercera
			return res;
		else if(res2 == res3) // si gana la segunda y la tercera
			return res2;
		
		return res3;//es cero si no hay ganador, y no se jugo la tercera
	}

	private Jugada[] getJugadas() {
		if(jugadas == null)
			jugadas = new Jugada[3];
		return jugadas;
	}
	
	public void envido(boolean gui){ //siempre se ejecuta en el creador, si viene del gui, el que toco fue el creador
		tocado = true;
		if(gui) // sumo el primer punto, luego cuando quiera, segun se gane o pierda sumo el resto
			envidoCreador++;
		else
			envidoInvitado++;
	}
	
	public String quieroEnvido(int valorEnvido){
		if(valorEnvido == 0){// se toco la falta
			int tc = totalTantosCreador();
			int ti = totalTantosInvitado();
			if(tc>ti)
				valorEnvido += 40 - tc;
			else
				valorEnvido += 40 - ti;
		}
		int vc = juego.calcularEnvido(juego.creador, muestra);
		int vi = juego.calcularEnvido(juego.invitado, muestra);
		if(vc > vi){
			envidoCreador += valorEnvido -1;
			envidoInvitado = 0;
			return "Gané con "+vc;
		}
		else{
			envidoInvitado  += valorEnvido -1;
			envidoCreador = 0;
			return "Gané con "+vi;
		}
	}
	
	public void noQuieroTruco(){
		estado = ESTADO_FIN;
		if(truco)
			sumarTantoTruco();
		if(retruco)
			sumarTantoTruco();
		if(vale4)
			sumarTantoTruco();
	}
	
	private void sumarTantoTruco(){
		if(rabonCreador)
			trucoCreador++;
		else
			trucoInvitado++;
	}
}
