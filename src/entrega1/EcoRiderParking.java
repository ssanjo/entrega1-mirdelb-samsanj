package entrega1;

import java.util.ArrayList;
import fabricante.externo.tarjetas.TarjetaMonedero;


/**
 * Representa un parking de EcoRider con plazas para vehiculos electricos, su identificador, nombre, direccion y localizacion GPS.
 * 
 * @author Miryam Del Bosque Fernandez
 * @author Samuel San Jose Faulin
 *
 */
public class EcoRiderParking {
	private String id;
	private String nombre;
	private String direccion;
	private Gps gps;
	private int plazasPorBloque;
	private ArrayList<EcoRiderPlace> plazas;

	/**
	 * Constructor de la clase EcoRiderParking.
	 * @param id El identificador del parking.
	 * @param nombre El nombre del parking.
	 * @param direccion La direccion del parking.
	 * @param gps La localizacion GPS del parking.
	 * @param plazasPorBloque El numero de plazas por bloque.
	 * @param numeroBloquesIniciales El numero de bloques iniciales.
	 * @throws IllegalArgumentException Si el identificador, nombre o direccion son nulos o
	 * vacios, si la localizacion GPS es nula, o si el numero de plazas por bloque
	 * es menor o igual a 0.
	 */
	public EcoRiderParking( String id, String nombre, String direccion, Gps gps, int plazasPorBloque, int numeroBloquesIniciales) {
		if(plazasPorBloque <= 0) {
			throw new IllegalArgumentException("El numero de plazas por bloque debe ser mayor que 0.");
		}
		if(id == null || id.isEmpty()) {
			throw new IllegalArgumentException("El identificador no puede ser nulo o vacio.");
		}
		if(nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede ser nulo o vacio.");
		}
		if(direccion == null || direccion.isEmpty()) {
			throw new IllegalArgumentException("La direccion no puede ser nula o vacia.");
		}
		if(gps == null) {
			throw new IllegalArgumentException("La localizacion no puede ser nula.");
		}
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.gps = gps;
		this.plazasPorBloque = plazasPorBloque;
		this.plazas = new ArrayList<>(plazasPorBloque * numeroBloquesIniciales);

		for(int i = 0; i < plazasPorBloque * numeroBloquesIniciales; i++) {
			plazas.add(new EcoRiderPlace(i));
		}

	}

	/**
	 * Constructor de copia de la clase EcoRiderParking.
	 * @param parking El parking a copiar.
	 * @throws IllegalArgumentException Si el parking es nulo.
	 */
	public EcoRiderParking(EcoRiderParking parking) {
		if(parking == null) {
			throw new IllegalArgumentException("El parking no puede ser nulo.");
		}
		this.id = parking.getId();
		this.nombre = parking.getNombre();
		this.direccion = parking.getDireccion();
		this.gps = new Gps(parking.getGps());
		this.plazasPorBloque = parking.getPlazasPorBloque();
		this.plazas = new ArrayList<>();
		for(EcoRiderPlace plaza : parking.getPlazas()) {
			this.plazas.add(new EcoRiderPlace(plaza));
		}
	}

	
	/**
	 * Devuelve el numero total de plazas en el parking.
	 * @return El numero total de plazas.
	 */
	public int getNumeroPlazas() {
		return getPlazas().size();
	}

	/**
	 * Devuelve el numero de plazas disponibles en el parking.
	 * @return El numero de plazas disponibles.
	 */
	public int getNumeroPlazasDisponibles() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.DISPONIBLE) {
				contador++;
			}
		}
		return contador;
	}

	/**
	 * Devuelve el numero de plazas ocupadas en el parking.
	 * @return El numero de plazas ocupadas.
	 */
	public int getNumeroPlazasOcupadas() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.OCUPADA) {
				contador++;
			}
		}
		return contador;
	}

	/**
	 * Devuelve el numero de plazas inoperativas en el parking.
	 * @return El numero de plazas inoperativas.
	 */
	public int getNumeroPlazasInoperativas() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.INOPERATIVA) {
				contador++;
			}
		}
		return contador;
	}

	/**
	 * Devuelve el numero de plazas reservadas en el parking.
	 * @return El numero de plazas reservadas.
	 */
	public int getNumeroPlazasReservadas() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.RESERVADA) {
				contador++;
			}
		}
		return contador;
	}

	/**
	 * Devuelve una copia de la localizacion GPS del parking.
	 * @return La localizacion GPS.
	 */
	public Gps getGps() {
		return new Gps(gps);
	}

	/**
	 * Devuelve el identificador del parking.
	 * @return El identificador.
	 */
	public String getId() {
		return id;
	}
	/**
	 * Devuelve el nombre del parking.
	 * @return El nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve la direccion del parking.
	 * @return La direccion.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Devuelve el numero de plazas por bloque del parking.
	 * @return El numero de plazas por bloque.
	 */
	public int getPlazasPorBloque() {
		return plazasPorBloque;
	}

	/**
	 * Devuelve la lista de plazas del parking.
	 * @return La lista de plazas.
	 */
	private ArrayList<EcoRiderPlace> getPlazas() {
		return plazas;
	}
	
	/**
	 * Devuelve el estado de una plaza del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @return El estado de la plaza.
	 * @throws IllegalArgumentException Si el identificador de la plaza no es valido.
	 */
	public EcoRiderPlace.Estado getEstadoPlaza(int idPlaza) {
		comprobarPlazaValida(idPlaza);
		
		return getPlazas().get(idPlaza).getEstado();
	}

	/**
	 * Devuelve la distancia entre el parking y otro parking.
	 * @param parking El otro parking.
	 * @return La distancia en kilometros.
	 */
	public double getDistancia(EcoRiderParking parking) {
		return gps.distanciaEnKm(parking.getGps());
	}
	
	/**
	 * Devuelve la distancia entre el parking y una localizacion GPS.
	 * @param gps La localizacion GPS.
	 * @return La distancia en kilometros.
	 */
	public double getDistancia(Gps gps) {
		return gps.distanciaEnKm(gps);
	}

	/**
	 * Comprueba si el identificador de una plaza es valido.
	 * @param idPlaza El identificador de la plaza.
	 * @return true si el identificador es valido, false en caso contrario.
	 */
	private boolean isPlazaValida(int idPlaza) {
		return idPlaza >= 0 && idPlaza < getNumeroPlazas();
	}

	/**
	 * Lanza una excepcion si el identificador de una plaza no es valido.
	 * @param idPlaza El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador no es valido.
	 */
	private void comprobarPlazaValida(int idPlaza) {
		if(!isPlazaValida(idPlaza)) {
			throw new IllegalArgumentException("El identificador de plaza no es valido.");
		}
	}

	/**
	 * Alquila un vehiculo en una plaza del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @param tarjeta La tarjeta monedero para realizar el pago.
	 * @param fianza La fianza a cargar en la tarjeta.
	 * @throws IllegalArgumentException Si el identificador de la plaza no es valido,
	 * si la plaza no esta disponible, o si la tarjeta es nula o no tiene saldo suficiente.
	 */
	public void alquilarVehiculoEnPlaza(int idPlaza, TarjetaMonedero tarjeta, double fianza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).llevarCoche(tarjeta, fianza);
	}

	/**
	 * Devuelve un vehiculo en una plaza del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @param tarjeta La tarjeta monedero para realizar el pago.
	 * @param fianza La fianza a cargar en la tarjeta.
	 * @throws IllegalArgumentException Si el identificador de la plaza no es valido,
	 * si la plaza no esta ocupada, o si la tarjeta es nula o no tiene saldo suficiente.
	 */
	public void devolverVehiculoEnPlaza(int idPlaza, TarjetaMonedero tarjeta, double fianza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).dejarCoche(tarjeta, fianza);
	}


	/**
	 * Reserva una plaza del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador de la plaza no es valido,
	 * o si la plaza no esta disponible.
	 */
	public void reservarPlaza(int idPlaza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).reservarPlaza();
	}

	/**
	 * Marca una plaza del parking como inoperativa.
	 * @param idPlaza El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador de la plaza no es valido,
	 * o si la plaza ya esta inoperativa.
	 */
	public void setPlazaInoperativa(int idPlaza)  {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).setPlazaInoperativa();
	}

	/**
	 * Marca una plaza del parking como operativa.
	 * @param idPlaza El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador de la plaza no es valido,
	 * o si la plaza ya esta operativa.
	 */
	public void setPlazaOperativa(int idPlaza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).setPlazaOperativa();
	}

	/**
	 * Anade un bloque de plazas al parking.
	 */
	public void anadirBloque() {
		int inicio = getNumeroPlazas();
	    int fin = inicio + getPlazasPorBloque();

		for(int i = inicio; i < fin; i++) {
			getPlazas().add(new EcoRiderPlace(i));
		}
	}
	
	/**
	 * Devuelve una representacion en cadena del parking.
	 * @return La representacion en cadena del parking.
	 */
	@Override
	public String toString() {
		return "EcoRiderParking [id=" + getId() + ", nombre=" + getNombre() + ", direccion=" + getDireccion() + ", gps=" + gps + ", plazasPorBloque=" + getPlazasPorBloque()
				+ ", plazas=" + getPlazas() + "]";

	}

	/**
	 * Compara dos objetos EcoRiderParking para determinar si son iguales.
	 * @param objeto El objeto a comparar.
	 * @return true si los objetos son iguales, false en caso contrario.
	 */
	@Override
	public boolean equals(Object objeto) {
		if (this == objeto){
			return true;
		}
		if (!(objeto instanceof EcoRiderParking)) {
			return false;
		}
		EcoRiderParking parking = (EcoRiderParking) objeto;

		return (
			getId().equals(parking.getId()) &&
			getNombre().equals(parking.getNombre()) &&
			getDireccion().equals(parking.getDireccion()) &&
			gps.equals(parking.getGps()) &&
			getPlazasPorBloque() == parking.getPlazasPorBloque() &&
			getPlazas().equals(parking.getPlazas())
		);
	
	}
}