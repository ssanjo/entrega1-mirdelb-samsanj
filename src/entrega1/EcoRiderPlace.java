package entrega1;

import fabricante.externo.tarjetas.TarjetaMonedero;

/**
 * 
 * 
 * Representa una plaza de parking de EcoRider con su estado e identificador.
 * 
 * 
 * @author Miryam Del Bosque Fernandez
 * @author Samuel San Jose Faulin
 *
 */
public class EcoRiderPlace {
	private static final String CARGAR_SALDO = "A156Bv09_1zXo894";
	private static final String DESCONTAR_SALDO = "6Z1y00Nm31aA-571";


	private Estado estado;
	private int id;
	
	/**
	 * Representa los posibles estados de una plaza de parking.
	 */
	public enum Estado{
		/** La plaza esta vacia y operativa. */
		DISPONIBLE,
		/** La plaza tiene un vehiculo. */
		OCUPADA,
		/** La plaza esta reservada. */
		RESERVADA,
		/** La plaza no esta operativa. */
		INOPERATIVA
	}
	
	/**
	 * Constructor de la clase EcoRiderPlace.
	 * @param id El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador es negativo.
	 */
	public EcoRiderPlace(int id) {
		if(id < 0) {
			throw new IllegalArgumentException("El identificador no puede ser negativo.");
		}
		
		estado = Estado.OCUPADA;
		this.id = id;
	}

	/**
	 * Constructor de copia de la clase EcoRiderPlace.
	 * @param plaza La plaza a copiar.
	 * @throws IllegalArgumentException Si la plaza es nula.
	 */
	public EcoRiderPlace(EcoRiderPlace plaza) {
		if(plaza == null) {
			throw new IllegalArgumentException("La plaza no puede ser nula.");
		}
		this.estado = plaza.getEstado();
		this.id = plaza.getId();
	}
	
	
	/**
	 * Devuelve el estado de la plaza.
	 * @return El estado de la plaza.
	 */
	public Estado getEstado() {
		return estado;
	}
	
	/**
	 * Devuelve el identificador de la plaza.
	 * @return El identificador de la plaza.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Reserva la plaza.
	 * @throws IllegalStateException Si la plaza no esta disponible.
	 */
	public void reservarPlaza() {
		if(getEstado() != Estado.DISPONIBLE) {
			throw new IllegalStateException("La plaza debe estar disponible.");
		}
		estado = Estado.RESERVADA;
	}

	/**
	 * Deja un coche en la plaza.
	 * @param tarjeta La tarjeta monedero para realizar el pago.
	 * @param fianza La fianza a cargar en la tarjeta.
	 * @throws IllegalStateException Si la plaza no esta disponible o reservada.
	 * @throws IllegalArgumentException Si la tarjeta es nula o no tiene saldo suficiente.
	 */
	public void dejarCoche(TarjetaMonedero tarjeta, double fianza) {
		if(getEstado() != Estado.DISPONIBLE && getEstado() != Estado.RESERVADA) {
			throw new IllegalStateException("La plaza debe estar vacia.");
		}

		tarjeta.recargaSaldo(CARGAR_SALDO, fianza);


		estado = Estado.OCUPADA;
	}
	
	/**
	 * Lleva un coche de la plaza.
	 * @param tarjeta La tarjeta monedero para realizar el pago.
	 * @param fianza La fianza a cargar en la tarjeta.
	 * @throws IllegalStateException Si la plaza no tiene un vehiculo.
	 * @throws IllegalArgumentException Si la tarjeta es nula o no tiene saldo suficiente.
	 */
	public void llevarCoche(TarjetaMonedero tarjeta, double fianza) {
		if(getEstado() != Estado.OCUPADA) {
			throw new IllegalStateException("La plaza debe tener un vehiculo.");
		}

		tarjeta.descontarDelSaldo(DESCONTAR_SALDO, fianza);


		estado = Estado.DISPONIBLE;
	}
	
	/**
	 * Marca la plaza como inoperativa.
	 * @throws IllegalStateException Si la plaza no esta disponible.
	 */
	public void setPlazaInoperativa() {
		if(getEstado() != Estado.DISPONIBLE) {
			throw new IllegalStateException("La plaza debe estar vacia.");
		}
		estado = Estado.INOPERATIVA;
	}

	/**
	 * Marca la plaza como operativa.
	 * @throws IllegalStateException Si la plaza no esta inoperativa.
	 */
	public void setPlazaOperativa() {
		if(getEstado() != Estado.INOPERATIVA) {
			throw new IllegalStateException("La plaza debe estar inoperativa.");
		}
		estado = Estado.DISPONIBLE;
	}
	
	/**
	 * Devuelve una representacion en cadena de la plaza.
	 * @return La representacion en cadena de la plaza.
	 */
	@Override
	public String toString() {
		return "La plaza tiene el identificador " + getId() + " y el estado " + getEstado();
	}
	
	/**
	 * Compara dos objetos EcoRiderPlace para determinar si son iguales.
	 * @param objeto El objeto a comparar.
	 * @return true si los objetos son iguales, false en caso contrario.
	 */
	@Override
	public boolean equals(Object objeto) {

		if (this == objeto) {
			return true;
		}

		if(!(objeto instanceof EcoRiderPlace)) {
			return false;
		}

		EcoRiderPlace plaza = (EcoRiderPlace) objeto;
		
		return (
			getId() == plaza.getId() &&
			getEstado() == plaza.getEstado()
		);
	}
}