package com.luisantolin.daw.prog.movil;

/**
 * Modela un dispositivo movil. Cada movil mantiene:
 * <ol>
 * <li>Una lista de aplicaciones instaladas</li>
 * <li>La RAM que queda libre en cada momento, en MiB</li>
 * <li>El disco duro que queda libre en cada momento, en MiB</li>
 * </ol>
 * 
 * @author lantolin
 *
 */
public class Movil {
	static final public int RAM_MAX = 4096;
	static final public int HD_MAX = 64000;
	private int ramLibre;
	private int hdLibre;
	private boolean encendido;

	private Aplicacion[] apps;

	/**
	 * Constructor por defecto.
	 */
	public Movil() {
		this.ramLibre = RAM_MAX;
		this.hdLibre = HD_MAX;
		this.apps = new Aplicacion[] {};
		this.encendido = false;
	}

	public int getRamLibre() {
		return ramLibre;
	}

	public int getHdLibre() {
		return hdLibre;
	}

	public int getNumAppsInstaladas() {
		return apps.length;
	}

	public int getNumAppsCorriendo() {
		int appsRunning = 0;
		for (Aplicacion app : apps) {
			if (app.isRunning())
				appsRunning++;
		}
		return appsRunning;
	}

	public boolean isEncendido() {
		return encendido;
	}

	/**
	 * Imprime el estado completo del movil. Imprime la siguiente información: si
	 * esta o no encendido, la RAM libre, el HD libre, las Aplicaciones instaladas y
	 * las Aplicaciones que se estan ejecutando.
	 * 
	 * @return
	 */
	public String status() {
		String resultado = "";
		resultado += "¿El movil esta encendido? " + encendido + "\n";
		resultado += "RAM libre: " + ramLibre + "MB\n";
		resultado += "HD libre:  " + hdLibre + "MB\n";
		resultado += "Aplicaciones instaladas:\n";
		for (Aplicacion app : apps) {
			resultado += app.getNombre() + "\n";
		}
		resultado += "Aplicaciones corriendo:\n";
		for (Aplicacion app : apps) {
			if (app.isRunning()) {
				resultado += app.getNombre() + "\n";
			}
		}
		return resultado;
	}

	/**
	 * Devuelve un objeto Aplicacion si el movil tiene instalada la Aplicacion con
	 * el nombre dado
	 * 
	 * @param name Nombre de la aplicación
	 * @return El objeto Aplicacion o null si la aplicacion no está instalada
	 */
	public Aplicacion getApp(String name) {
		for (Aplicacion app : apps) {
			if (name.equals(app.getNombre())) {
				return app;
			}
		}
		return null;
	}

// A partir de aqui están los métodos que tienes que implementar
	
	/**
	 * Añade la aplicacion a la lista de aplicaciones instaladas. Decrementa el
	 * disco duro disponible. Para que la aplicación se pueda instalar, se requiere
	 * que el movil esté encendido y que tenga suficiente disco duro disponible para
	 * instalar la aplicacion
	 * 
	 * @param app Aplicacion a instalar
	 */
	public void instalarAplicacion(Aplicacion app) {
		// AQUI VA TU CODIGO
		if (!isEncendido()) {
			return;
		}
		else {
			if ((this.getHdLibre()-app.getHd())<0) {
				return;
			}else {
				for (int i = 0; i < apps.length; i++) {
					if (apps[i]==null) {
						apps[i]=app;
						return;
					}
				}
			}
		}
	}

	/**
	 * Desinstala la aplicacion con el nombre dado. Elimina la aplicacion con ese
	 * nombre de las aplicaciones instaladas. Para que la aplicación se pueda
	 * desinstalar, se requiere que el movil esté encendido y que este instalada.
	 * 
	 * @param name Nombre de la aplicación a eliminar
	 */
	public void desinstalarAplicacion(String name) {
		// AQUI VA TU CODIGO
		if (isEncendido()) {
			for (int i = 0; i < apps.length; i++) {
				if (apps[i].getNombre().equals(name)) {
					apps[i]=null;
					this.hdLibre+=apps[i].getHd();
					return;
				}
			}
		}else {
			return;
		}
	}

	/**
	 * Ejecuta la aplicacion con el nombre dado. Para que la aplicación se pueda
	 * ejecutar, se requiere que el movil esté encendido y que la aplicacion esté
	 * instalada. Si se ejecuta con éxito, se decrementa la RAM disponible.
	 * 
	 * @param name Nombre de la aplicación a ejecutar
	 */
	public void ejecutarAplicacion(String name) {
		// AQUI VA TU CODIGO
		if (!isEncendido()) {
			return;
		}else {
			for (int i = 0; i < apps.length; i++) {
				if (apps[i].getNombre().equals(name)) {
					apps[i].run();
					this.ramLibre -=apps[i].getRam();
					return;
				}
			}
		}
	}

	/**
	 * Detiene la ejecucion de la aplicacion con el nombre dado. Para que la
	 * aplicación se pueda detener, se requiere que el movil esté encendido y que la
	 * aplicación se esté ejecutando. Si se detiene con exito, se incrementa la RAM
	 * disponible.
	 * 
	 * @param name Nombre de la aplicación a detener
	 */
	public void pararAplicacion(String name) {
		// AQUI VA TU CODIGO
		if (!isEncendido()) {
			return;
		}else {
			for (int i = 0; i < apps.length; i++) {
				if (apps[i].getNombre().equals(name)&&apps[i].isRunning()) {
					apps[i].stop();
					this.ramLibre += apps[i].getRam();
				}
			}
		}
	}

	/**
	 * Enciende el movil. Para todas las aplicaciones que se estén ejecutando.
	 */
	public void encender() {
		// AQUI VA TU CODIGO
		encendido = true;
		for (int i = 0; i < apps.length; i++) {
			apps[i].stop();
			this.ramLibre = RAM_MAX;
		}
	}

	/**
	 * Apaga el movil y detiene la ejecución de todas las Aplicaciones que se esten
	 * ejecutando. Las aplicaciones instaladas siguen instaladas.
	 */
	public void apagar() {
		// AQUI VA TU CODIGO
		encendido = false;
		for (int i = 0; i < apps.length; i++) {
			apps[i].stop();
			this.ramLibre = RAM_MAX;
		}
	}

}
