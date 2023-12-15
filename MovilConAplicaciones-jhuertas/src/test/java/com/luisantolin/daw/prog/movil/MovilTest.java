package com.luisantolin.daw.prog.movil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// nota del profesor: soy consciente de que muchos de los tests no son estrictamente "unitarios" ya que
// prueban varias cosas a la vez. La vida no es perfecta :)

public class MovilTest {
	static private Movil m;
	static private Aplicacion chrome;
	static private Aplicacion facebook;
	static private Aplicacion instagram;

	@Test
	void apagarConservaLasAppsInstaladas() {
		prepararElMovil();

		int numAppsInstaladas = m.getNumAppsInstaladas();
		m.apagar();
		assertEquals(m.getNumAppsInstaladas(), numAppsInstaladas);
	}

	@Test
	void apagarDejaAppsParadasTest() {
		prepararElMovil();

		m.ejecutarAplicacion(chrome.getNombre());
		m.ejecutarAplicacion(facebook.getNombre());
		m.ejecutarAplicacion(instagram.getNombre());

		m.apagar();
		assertEquals(m.getNumAppsCorriendo(), 0);
		assertEquals(m.getRamLibre(), Movil.RAM_MAX);
	}

	@Test
	void apagarTest() {
		m.encender();
		assertTrue(m.isEncendido());
		m.apagar();
		assertFalse(m.isEncendido());
	}

	@Test
	void constructorMovilVacio() {
		// estrictamente no es un test unitario, lo se :)
		assertFalse(m.isEncendido());
		assertEquals(m.getRamLibre(), Movil.RAM_MAX);
		assertEquals(m.getHdLibre(), Movil.HD_MAX);
		assertEquals(m.getNumAppsInstaladas(), 0);
	}

	@BeforeEach
	void crearMovilYAplicaciones() {
		m = new Movil();
		chrome = new Aplicacion("Chrome", 512, 1024);
		facebook = new Aplicacion("Facebook", 1024, 5000);
		instagram = new Aplicacion("Instagram", 400, 3000);
	}

	@Test
	void desinstalarAplicacionDecrementaHd() {
		prepararElMovil();

		int hdAntes;
		int hdDespues;

		hdAntes = m.getHdLibre();
		m.desinstalarAplicacion(facebook.getNombre());
		hdDespues = m.getHdLibre();
		assertEquals(hdAntes + facebook.getHd(), hdDespues);

		hdAntes = m.getHdLibre();
		m.desinstalarAplicacion(chrome.getNombre());
		hdDespues = m.getHdLibre();
		assertEquals(hdAntes + chrome.getHd(), hdDespues);

		hdAntes = m.getHdLibre();
		m.desinstalarAplicacion(instagram.getNombre());
		hdDespues = m.getHdLibre();
		assertEquals(hdAntes + instagram.getHd(), hdDespues);
	}

	@Test
	void desinstalarAplicacionNoVariaLaRam() {
		prepararElMovil();

		int ramAntes = m.getRamLibre();
		m.desinstalarAplicacion(chrome.getNombre());
		int ramDespues = m.getRamLibre();
		assertEquals(ramAntes, ramDespues);
	}

	@Test
	void desinstalarAplicacionTestDecrementoNumApps() {
		prepararElMovil();

		int numAppsAntes = m.getNumAppsInstaladas();
		m.desinstalarAplicacion(chrome.getNombre());
		int numAppsDespues = m.getNumAppsInstaladas();
		assertEquals(numAppsAntes - 1, numAppsDespues);
	}

	@Test
	void desinstalarNoFuncionaConElMovilApagado() {
		m.encender();
		m.instalarAplicacion(chrome);

		m.apagar();
		int numAppsAntes = m.getNumAppsInstaladas();
		m.desinstalarAplicacion(chrome.getNombre());
		int numAppsDespues = m.getNumAppsInstaladas();
		assertEquals(numAppsAntes, numAppsDespues);
	}

	@Test
	void ejecutarAplicacionDecrementaRam() {
		prepararElMovil();

		int ramAntes = m.getRamLibre();
		m.ejecutarAplicacion(facebook.getNombre());
		int ramDespues = m.getRamLibre();
		assertEquals(ramAntes - facebook.getRam(), ramDespues);
	}

	@Test
	void ejecutarAplicacionNoVariaHd() {
		prepararElMovil();

		int hdAntes = m.getHdLibre();
		m.ejecutarAplicacion(facebook.getNombre());
		int hdDespues = m.getHdLibre();
		assertEquals(hdAntes, hdDespues);
	}

	@Test
	void ejecutarAplicacionOk() {
		prepararElMovil();

		int numAppsCorriendoAntes = m.getNumAppsCorriendo();
		assertFalse(facebook.isRunning());
		m.ejecutarAplicacion(facebook.getNombre());
		assertTrue(facebook.isRunning());
		assertEquals(m.getNumAppsCorriendo(), numAppsCorriendoAntes + 1);
	}

	@Test
	void ejecutarAppNoFuncionaSiLaAppNoCabe() {
		prepararElMovil();

		Aplicacion enorme = new Aplicacion("Enorme", 40000, 3000);
		m.instalarAplicacion(enorme);
		int numAppsCorriendoAntes = m.getNumAppsCorriendo();
		int ramAntes = m.getRamLibre();
		m.ejecutarAplicacion(enorme.getNombre());
		int ramDespues = m.getRamLibre();
		assertEquals(ramAntes, ramDespues);
		assertEquals(m.getNumAppsCorriendo(), numAppsCorriendoAntes );
	}

	@Test
	void ejecutarAppNoFuncionaSiLaAppNoEstaInstalada() {
		m.encender();

		m.instalarAplicacion(chrome);
		m.instalarAplicacion(instagram);

		int numAppsCorriendoAntes = m.getNumAppsCorriendo();
		int ramAntes = m.getRamLibre();
		m.ejecutarAplicacion(facebook.getNombre());
		int ramDespues = m.getRamLibre();
		assertEquals(ramAntes, ramDespues);
		assertEquals(m.getNumAppsCorriendo(), numAppsCorriendoAntes );
	}

	@Test
	void ejecutarNoFuncionaConElMovilApagado() {
		prepararElMovil();
		m.apagar();

		int numAppsCorriendoAntes = m.getNumAppsCorriendo();
		m.ejecutarAplicacion(facebook.getNombre());
		assertFalse(facebook.isRunning());
		assertEquals(m.getNumAppsCorriendo(), numAppsCorriendoAntes );
	}

	@Test
	void encenderTest() {
		assertFalse(m.isEncendido());
		m.encender();
		assertTrue(m.isEncendido());
		assertEquals(m.getNumAppsCorriendo(), 0);
	}

	@Test
	void instalarAplicacionIncrementaHd() {
		m.encender();

		int hdAntes;
		int hdDespues;

		hdAntes = m.getHdLibre();
		m.instalarAplicacion(facebook);
		hdDespues = m.getHdLibre();
		assertEquals(hdAntes - facebook.getHd(), hdDespues);

		hdAntes = m.getHdLibre();
		m.instalarAplicacion(chrome);
		hdDespues = m.getHdLibre();
		assertEquals(hdAntes - chrome.getHd(), hdDespues);

		hdAntes = m.getHdLibre();
		m.instalarAplicacion(instagram);
		hdDespues = m.getHdLibre();
		assertEquals(hdAntes - instagram.getHd(), hdDespues);
	}

	@Test
	void instalarAplicacionTestIncrementoNumApps() {
		m.encender();

		int numAppsAntes = m.getNumAppsInstaladas();
		m.instalarAplicacion(chrome);
		m.instalarAplicacion(facebook);
		m.instalarAplicacion(instagram);
		int numAppsDespues = m.getNumAppsInstaladas();
		assertEquals(numAppsAntes + 3, numAppsDespues);
	}

	@Test
	void instalarAplicacionTestLaAppEsta() {
		prepararElMovil();

		assertEquals(m.getApp(chrome.getNombre()), chrome);
		assertEquals(m.getApp(facebook.getNombre()), facebook);
		assertEquals(m.getApp(instagram.getNombre()), instagram);
	}

	@Test
	void instalarAppNoFuncionaSiHayUnaConElMismoNombre() {
		prepararElMovil();

		Aplicacion duplicada = new Aplicacion("Instagram", 400, 1);
		int numAppsAntes = m.getNumAppsInstaladas();
		int hdAntes = m.getHdLibre();
		m.instalarAplicacion(duplicada);
		int numAppsDespues = m.getNumAppsInstaladas();
		int hdDespues = m.getHdLibre();
		assertEquals(numAppsAntes, numAppsDespues);
		assertEquals(hdAntes, hdDespues);

	}

	@Test
	void instalarAppNoFuncionaSiLaAppNoCabe() {
		prepararElMovil();

		Aplicacion enorme = new Aplicacion("Enorme", 400, 300000);
		int numAppsAntes = m.getNumAppsInstaladas();
		int hdAntes = m.getHdLibre();
		m.instalarAplicacion(enorme);
		int numAppsDespues = m.getNumAppsInstaladas();
		int hdDespues = m.getHdLibre();
		assertEquals(numAppsAntes, numAppsDespues);
		assertEquals(hdAntes, hdDespues);

	}

	@Test
	void instalarNoFuncionaConElMovilApagado() {
		m.apagar();
		int numAppsAntes = m.getNumAppsInstaladas();
		int hdAntes = m.getHdLibre();
		m.instalarAplicacion(chrome);
		int numAppsDespues = m.getNumAppsInstaladas();
		int hdDespues = m.getHdLibre();
		assertEquals(numAppsAntes, numAppsDespues);
		assertEquals(hdAntes, hdDespues);
	}

	@Test
	@Disabled
	public void megaTestNoUnitario() {
		System.out.println(m.status());
		m.encender();
		m.instalarAplicacion(chrome);
		System.out.println(m.status());
		m.instalarAplicacion(facebook);
		System.out.println(m.status());
		m.ejecutarAplicacion(facebook.getNombre());
		System.out.println(m.status());
		m.pararAplicacion(facebook.getNombre());
		System.out.println(m.status());
		m.desinstalarAplicacion(facebook.getNombre());
		System.out.println(m.status());
		m.desinstalarAplicacion(chrome.getNombre());
		System.out.println(m.status());
		m.instalarAplicacion(instagram);
		System.out.println(m.status());
		m.ejecutarAplicacion(instagram.getNombre());
		System.out.println(m.status());
		m.apagar();
		System.out.println(m.status());
	}

	@Test
	void pararAplicacionIncrementaRam() {
		prepararElMovil();

		m.ejecutarAplicacion(facebook.getNombre());
		int ramAntes = m.getRamLibre();
		m.pararAplicacion(facebook.getNombre());
		int ramDespues = m.getRamLibre();
		assertEquals(ramAntes + facebook.getRam(), ramDespues);
	}

	@Test
	void pararAplicacionNoVariaHd() {
		prepararElMovil();

		m.ejecutarAplicacion(facebook.getNombre());
		int hdAntes = m.getHdLibre();
		m.pararAplicacion(facebook.getNombre());
		int hdDespues = m.getHdLibre();
		assertEquals(hdAntes, hdDespues);
	}

	@Test
	void pararAplicacionOk() {
		prepararElMovil();

		m.ejecutarAplicacion(facebook.getNombre());
		assertTrue(facebook.isRunning());
		m.pararAplicacion(facebook.getNombre());
		assertFalse(facebook.isRunning());
	}

	@Test
	void ejecutarUnaAppQueYaSeEjecutabaNoHaceUnDobleDecremento() {
		prepararElMovil();

		m.ejecutarAplicacion(facebook.getNombre());

		int numAppsCorriendoAntes = m.getNumAppsCorriendo();
		int ramAntes = m.getRamLibre();
		m.ejecutarAplicacion(facebook.getNombre());
		assertEquals(m.getRamLibre(), ramAntes);
		assertEquals(m.getNumAppsCorriendo(), numAppsCorriendoAntes );
		
	}
	
	/**
	 * Deja el movil en el estado en el que lo esperan la mayoría de tests:
	 * encendido y con 3 aplicaciones instaladas.
	 */
	private void prepararElMovil() {
		m.encender();

		m.instalarAplicacion(chrome);
		m.instalarAplicacion(facebook);
		m.instalarAplicacion(instagram);
	}
}
