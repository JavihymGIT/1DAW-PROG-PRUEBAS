package org.prueba.examen.CalculadoraCidrPoo_jhuertas;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	if (args.length < 2) {
    		System.out.println("Cantidad de argumentos incorrecta");
    		return;
    	}
    	String mascara = args[1];
    	String[] mascaraSeparada = mascara.split(".");
    	int []mascaraSeparadaNum = new int [mascaraSeparada.length];
    	for (int i = 0; i < mascaraSeparadaNum.length; i++) {
			mascaraSeparadaNum[i]= Integer.parseInt(mascaraSeparada[i]);
		}
        String ip = args[0];
        String  []ipSeparada = ip.split(".");
        int []ipNumero = new int [ipSeparada.length];
        for (int i = 0; i < ipNumero.length; i++) {
			ipNumero[i]=Integer.parseInt(ipSeparada[i]);
		}
        if (ipSeparada.length != 4) {
        	System.out.println("La Ip no es correcta");
        }
        
    }
}
