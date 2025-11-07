package entrega1;

import static org.junit.Assert.*;
import fabricante.externo.tarjetas.TarjetaMonedero;


import org.junit.Test;

public class EcoRiderCityTest {

	private EcoRiderCity city;
    private EcoRiderParking parking1;
    private EcoRiderParking parking2;
    private TarjetaMonedero tarjeta;
    private Gps gps;

    @Before
    public void setUp() {
        gps = new Gps(32.564, -18.70);
        city = new EcoRiderCity("Valladolid", 20.0);
        parking1 = new EcoRiderParking("P001", "Parking Centro", "Calle Central 12", gps, 10, 2);
        parking2 = new EcoRiderParking("P002", "Parking Norte", "Calle Nueva 5", gps, 5, 3);
        tarjeta = new TarjetaMonedero("A156Bv09_1zXo894", 100.0);
	}

	@Test
	public void testGetParkingsConPlazaInoperativa() {
		city.anadirParking(parking1);
		city.anadirParking(parking2);
		parking1.setPlazaInoperativa(2);
		assertEquals(1, city.getParkingsConPlazaInoperativa().size());
		assertTrue(city.getParkingsConPlazaInoperativa().contains(parking1));
		assertFalse(city.getParkingsConPlazaInoperativa().contains(parking2));
	}

	@Test
	public void testGetParkingsCercanos(){
		city.anadirParking(parking1);
		city.anadirParking(parking2);
		EcoRiderParking parking3 = new EcoRiderParking("P003", "Parking Sur", "Calle Baja 8", new Gps(32.566, -18.706), 8, 1);
		city.anadirParking(parking3);
		Gps ubicacionUsuario = new Gps(32.565, -18.705);
		// 1 km
		assertFalse(city.getParkingsCercanos(ubicacionUsuario, 1000).contains(parking1));
		assertFalse(city.getParkingsCercanos(ubicacionUsuario, 1000).contains(parking2));
		assertTrue(city.getParkingsCercanos(ubicacionUsuario, 1000).contains(parking3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetParkingsCercanosGpsNull() {
		Gps ubicacionUsuario = null;
		city.getParkingsCercanos(ubicacionUsuario, 1000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetParkingsCercanosRadio0() {
		
		city.getParkingsCercanos(new Gps(32.565, -18.705), 0);
	}




}
