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
    
    //SETTERS*************************************************************
    @Test
    public void testSetFianzaValida() {
        city.setFianza(25.0);
        assertEquals(25.0, city.getFianza(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetFianzaNegativa() {
        city.setFianza(-10.0);
    }
    //********************************************************************
}
