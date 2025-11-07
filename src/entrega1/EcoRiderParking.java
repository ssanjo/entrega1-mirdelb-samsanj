package entrega1;

import java.util.ArrayList;
import fabricante.externo.tarjetas.TarjetaMonedero;


public class EcoRiderParking {
	private String id;
	private String nombre;
	private String direccion;
	private Gps gps;
	private int plazasPorBloque;
	private ArrayList<EcoRiderPlace> plazas;

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

	
	public int getNumeroPlazas() {
		return getPlazas().size();
	}

	public int getNumeroPlazasDisponibles() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.DISPONIBLE) {
				contador++;
			}
		}
		return contador;
	}

	public int getNumeroPlazasOcupadas() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.OCUPADA) {
				contador++;
			}
		}
		return contador;
	}

	public int getNumeroPlazasInoperativas() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.INOPERATIVA) {
				contador++;
			}
		}
		return contador;
	}

	public int getNumeroPlazasReservadas() {
		int contador = 0;
		for(EcoRiderPlace plaza : getPlazas()) {
			if(plaza.getEstado() == EcoRiderPlace.Estado.RESERVADA) {
				contador++;
			}
		}
		return contador;
	}

	public Gps getGps() {
		return new Gps(gps);
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public int getPlazasPorBloque() {
		return plazasPorBloque;
	}

	private ArrayList<EcoRiderPlace> getPlazas() {
		return plazas;
	}
	
	public EcoRiderPlace.Estado getEstadoPlaza(int idPlaza) {
		comprobarPlazaValida(idPlaza);
		
		return getPlazas().get(idPlaza).getEstado();
	}
	
	public double getDistancia(EcoRiderParking parking) {
		return gps.distanciaEnKm(parking.getGps());
	}
	
	public double getDistancia(Gps gps) {
		return gps.distanciaEnKm(gps);
	}
	
	private boolean isPlazaValida(int idPlaza) {
		return idPlaza >= 0 && idPlaza < getNumeroPlazas();
	}

	private void comprobarPlazaValida(int idPlaza) {
		if(!isPlazaValida(idPlaza)) {
			throw new IllegalArgumentException("El identificador de plaza no es valido.");
		}
	}

	public void alquilarVehiculoEnPlaza(int idPlaza, TarjetaMonedero tarjeta, double fianza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).llevarCoche(tarjeta, fianza);
	}

	public void devolverVehiculoEnPlaza(int idPlaza, TarjetaMonedero tarjeta, double fianza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).dejarCoche(tarjeta, fianza);
	}

	public void reservarPlaza(int idPlaza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).reservarPlaza();
	}

	public void setPlazaInoperativa(int idPlaza)  {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).setPlazaInoperativa();
	}

	public void setPlazaOperativa(int idPlaza) {
		comprobarPlazaValida(idPlaza);

		getPlazas().get(idPlaza).setPlazaOperativa();
	}

	public void anadirBloque() {
		int inicio = getNumeroPlazas();
	    int fin = inicio + getPlazasPorBloque();

		for(int i = inicio; i < fin; i++) {
			getPlazas().add(new EcoRiderPlace(i));
		}
	}
	
	@Override
	public String toString() {
		return "EcoRiderParking [id=" + getId() + ", nombre=" + getNombre() + ", direccion=" + getDireccion() + ", gps=" + gps + ", plazasPorBloque=" + getPlazasPorBloque()
				+ ", plazas=" + getPlazas() + "]";

	}

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