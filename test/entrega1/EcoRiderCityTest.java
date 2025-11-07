package entrega1;

import static org.junit.Assert.*;
import fabricante.externo.tarjetas.TarjetaMonedero;
import org.junit.Before;
import java.util.ArrayList;
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
    
    //CONSTRUCTOR********************************************************
    @Test
    public void testConstructorEcoRiderCity() {
        assertEquals("Valladolid", city.getNombre());
        assertEquals(20.0, city.getFianza(), 0.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderCityNombreNulo() {
        new EcoRiderCity(null, 20.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderCityNombreVacio() {
        new EcoRiderCity("", 20.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderCityFianzaNegativa() {
        new EcoRiderCity("Valladolid", -5.0);
    }
    //*********************************************************************
    
    //CONSTRUCTORCOPIA******************************************************
    @Test
    public void testEcoRiderCityConstructorCopia() {
        city.anadirParking(parking1);
        EcoRiderCity copia = new EcoRiderCity(city);
        
        assertEquals(city.getNombre(), copia.getNombre());
        assertEquals(city.getFianza(), copia.getFianza(), 0.001);
        assertEquals(city.getParkings().size(), copia.getParkings().size());
        assertNotSame(city, copia);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEcoRiderCityConstructorCopiaNulo() {
        new EcoRiderCity(null);
    }
    //*******************************************************************
	
    //GETTERS***********************************************************
    @Test
    public void testGetNombre() {
        assertEquals("Valladolid", city.getNombre());
    }
    
    @Test
    public void testGetFianza() {
        assertEquals(20.0, city.getFianza(), 0.001);
    }
    
    @Test
    public void testGetParkings() {
        city.anadirParking(parking1);
        city.anadirParking(parking2);
        
        ArrayList<EcoRiderParking> parkings = city.getParkings();
        
        assertEquals(2, parkings.size());
        assertNotSame(city.getParkings(), parkings);
    }
    //***********************************************************************
    
    //AñadirParkingValido*************************************************
    @Test
    public void testAnadirParkingValido() {
        city.anadirParking(parking1);
        assertEquals(1, city.getParkings().size());
        assertEquals("P001", city.getParkings().get(0).getId());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAnadirParkingNulo() {
        city.anadirParking(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAnadirParkingIdDuplicado() {
        city.anadirParking(parking1);
        city.anadirParking(parking1);
    }
    
    @Test
    public void testEliminarParkingValido() {
        city.anadirParking(parking1);
        city.anadirParking(parking2);
        
        city.eliminarParking("P001");
        
        assertEquals(1, city.getParkings().size());
        assertEquals("P002", city.getParkings().get(0).getId());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEliminarParkingIdNulo() {
        city.eliminarParking(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEliminarParkingIdVacio() {
        city.eliminarParking("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEliminarParkingNoExistente() {
        city.eliminarParking("P003");
    }
    
    @Test
    public void testGetParkingsDisponibles() {
        parking1.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking1.alquilarVehiculoEnPlaza(1, tarjeta, 10.0);
        
        city.anadirParking(parking1);
        city.anadirParking(parking2);
        
        ArrayList<EcoRiderParking> disponibles = city.getParkingsDisponibles();
        
        assertEquals(1, disponibles.size());
        assertEquals("P001", disponibles.get(0).getId());
    }
    
    @Test
    public void testGetParkingsOcupados() {
        parking1.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        
        city.anadirParking(parking1);
        city.anadirParking(parking2);
        
        ArrayList<EcoRiderParking> ocupados = city.getParkingsOcupados();
        
        assertEquals(1, ocupados.size());
        assertEquals("P002", ocupados.get(0).getId());
    }

	@Test
	public void testGetParkingsConPlazaInoperativa() {
		parking1.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking1.setPlazaInoperativa(0);
        
        city.anadirParking(parking1);
        city.anadirParking(parking2);
        
        ArrayList<EcoRiderParking> conInoperativas = city.getParkingsConPlazaInoperativa();
        
        assertEquals(1, conInoperativas.size());
        assertEquals("P001", conInoperativas.get(0).getId());
	}

	@Test
	public void testGetParkingsCercanos(){
		Gps gpsCercano = new Gps(32.5641, -18.70);
        parking1.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        
        city.anadirParking(parking1);
        city.anadirParking(parking2);
        
        ArrayList<EcoRiderParking> cercanos = city.getParkingsCercanos(gpsCercano, 1000);
        
        assertEquals(1, cercanos.size());
        assertEquals("P001", cercanos.get(0).getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetParkingsCercanosGpsNull() {
		Gps ubicacionUsuario = null;
		city.getParkingsCercanos(ubicacionUsuario, 1000);
	}

    @Test(expected = IllegalArgumentException.class)
    public void testSetFianzaNegativa() {
        city.setFianza(-10.0);
    }
    //********************************************************************

    //AlquilarVehiculoEnParking***************************************
    @Test
    public void testAlquilarVehiculoEnParking() {
        city.anadirParking(parking1);
        double saldoInicial = tarjeta.getSaldoActual();
        
        city.alquilarVehiculoEnParking("P001", 0, tarjeta);
        
        assertEquals(EcoRiderPlace.Estado.DISPONIBLE, parking1.getEstadoPlaza(0));
        assertEquals(saldoInicial - city.getFianza(), tarjeta.getSaldoActual(), 0.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAlquilarVehiculoEnParkingNoExiste() {
        city.alquilarVehiculoEnParking("P999", 0, tarjeta);
    }
    
    @Test
    public void testDevolverVehiculoEnParking() {
        city.anadirParking(parking1);
        city.alquilarVehiculoEnParking("P001", 0, tarjeta);
        
        double saldoInicial = tarjeta.getSaldoActual();
        city.devolverVehiculoEnParking("P001", 0, tarjeta);
        
        assertEquals(EcoRiderPlace.Estado.OCUPADA, parking1.getEstadoPlaza(0));
        assertEquals(saldoInicial + city.getFianza(), tarjeta.getSaldoActual(), 0.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDevolverVehiculoEnParkingNoExiste() {
        city.devolverVehiculoEnParking("P003", 0, tarjeta);
    }
    
    @Test
    public void testReservarPlazaEnParking() {
        city.anadirParking(parking1);
        city.alquilarVehiculoEnParking("P001", 0, tarjeta);
        
        city.reservarPlazaEnParking("P001", 0);
        
        assertEquals(EcoRiderPlace.Estado.RESERVADA, parking1.getEstadoPlaza(0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testReservarPlazaEnParkingNoExiste() {
        city.reservarPlazaEnParking("P003", 0);
    }
    
    //SETTERS*************************************************************
    @Test
    public void testSetFianzaValida() {
    	city.setFianza(25.0);
    	assertEquals(25.0, city.getFianza(), 0.001);
    }
    
    @Test
    public void testSetPlazaInoperativaEnParking() {
        city.anadirParking(parking1);
        city.alquilarVehiculoEnParking("P001", 0, tarjeta);
        
        city.setPlazaInoperativaEnParking("P001", 0);
        
        assertEquals(EcoRiderPlace.Estado.INOPERATIVA, parking1.getEstadoPlaza(0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetPlazaInoperativaEnParkingNoExiste() {
        city.setPlazaInoperativaEnParking("P003", 0);
    }
    
    @Test
    public void testSetPlazaOperativaEnParking() {
        city.anadirParking(parking1);
        city.alquilarVehiculoEnParking("P001", 0, tarjeta);
        city.setPlazaInoperativaEnParking("P001", 0);
        
        city.setPlazaOperativaEnParking("P001", 0);
        
        assertEquals(EcoRiderPlace.Estado.DISPONIBLE, parking1.getEstadoPlaza(0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetPlazaOperativaEnParkingNoExiste() {
        city.setPlazaOperativaEnParking("P003", 0);
    }
	//********************************************************************
    
    //ToString******************************************************
    @Test
    public void testToString() {
        city.anadirParking(parking1);
        String resultado = city.toString();
        
        assertTrue(resultado.contains("EcoRiderCity"));
        assertTrue(resultado.contains("Valladolid"));
        assertTrue(resultado.contains("20.0"));
    }
    //************************************************************
    
    //EQUALS*******************************************************
    @Test
    public void testEqualsMismoObjeto() {
        assertTrue(city.equals(city));
    }
    
    @Test
    public void testEqualsObjetosIguales() {
        EcoRiderCity city2 = new EcoRiderCity("Valladolid", 20.0);
        city.anadirParking(parking1);
        city2.anadirParking(new EcoRiderParking(parking1));
        
        assertTrue(city.equals(city2));
    }
    
    @Test
    public void testEqualsDiferenteNombre() {
        EcoRiderCity city2 = new EcoRiderCity("Salamanca", 20.0);
        assertFalse(city.equals(city2));
    }
    
    @Test
    public void testEqualsDiferenteFianza() {
        EcoRiderCity city2 = new EcoRiderCity("Valladolid", 25.0);
        assertFalse(city.equals(city2));
    }
    
    @Test
    public void testEqualsDiferenteParkings() {
        EcoRiderCity city2 = new EcoRiderCity("Valladolid", 20.0);
        city.anadirParking(parking1);
        
        assertFalse(city.equals(city2));
    }
    
    @Test
    public void testEqualsConNull() {
        assertFalse(city.equals(null));
    }
    
    @Test
    public void testEqualsConOtroTipo() {
    	String otro = "No soy una ciudad";
        assertFalse(city.equals(otro));
    }
    //******************************************************************************
}