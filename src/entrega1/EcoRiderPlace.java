package entrega1;

import fabricante.externo.tarjetas.TarjetaMonedero;

public class EcoRiderPlace {
	private static final String CARGAR_SALDO = "A156Bv09_1zXo894";
	private static final String DESCONTAR_SALDO = "6Z1y00Nm31aA-571";


	private Estado estado;
	private int id;
	
	public enum Estado{
		DISPONIBLE,
		OCUPADA,
		RESERVADA,
		INOPERATIVA
	}
	
	public EcoRiderPlace(int id) {
		if(id < 0) {
			throw new IllegalArgumentException("El identificador no puede ser negativo.");
		}
		
		estado = Estado.OCUPADA;
		this.id = id;
	}

	public EcoRiderPlace(EcoRiderPlace plaza) {
		if(plaza == null) {
			throw new IllegalArgumentException("La plaza no puede ser nula.");
		}
		this.estado = plaza.getEstado();
		this.id = plaza.getId();
	}
	
	
	public Estado getEstado() {
		return estado;
	}
	
	public int getId() {
		return id;
	}
	
	public void reservarPlaza() {
		if(getEstado() != Estado.DISPONIBLE) {
			throw new IllegalStateException("La plaza debe estar disponible.");
		}
		estado = Estado.RESERVADA;
	}

	public void dejarCoche(TarjetaMonedero tarjeta, double fianza) {
		if(getEstado() != Estado.DISPONIBLE && getEstado() != Estado.RESERVADA) {
			throw new IllegalStateException("La plaza debe estar vacia.");
		}

		tarjeta.recargaSaldo(CARGAR_SALDO, fianza);


		estado = Estado.OCUPADA;
	}
	
	public void llevarCoche(TarjetaMonedero tarjeta, double fianza) {
		if(getEstado() != Estado.OCUPADA) {
			throw new IllegalStateException("La plaza debe tener un vehiculo.");
		}

		tarjeta.descontarDelSaldo(DESCONTAR_SALDO, fianza);


		estado = Estado.DISPONIBLE;
	}
	
	public void setPlazaInoperativa() {
		if(getEstado() != Estado.DISPONIBLE) {
			throw new IllegalStateException("La plaza debe estar vacia.");
		}
		estado = Estado.INOPERATIVA;
	}

	public void setPlazaOperativa() {
		if(getEstado() != Estado.INOPERATIVA) {
			throw new IllegalStateException("La plaza debe estar inoperativa.");
		}
		estado = Estado.DISPONIBLE;
	}
	
	@Override
	public String toString() {
		return "La plaza tiene el identificador " + getId() + " y el estado " + getEstado();
	}
	
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