/**
* Praktikum PM2, WS17/18
* Gruppe: Daniel Biederman, Katerina Milenkovski 
* Aufgabe: Aufgabenblatt 4
* 
*/
package ws1718_a4.darstellung;

import java.util.regex.Pattern;

/**
*
* Überprüfen der Syntax einer Anweisung.
* 
* @author Daniel Biedermann, Katerina Milenkovski
* 
* 
*/
public class AnweisungsCheck {
	private String zuchecken;
	
	
	public AnweisungsCheck(String string){
		zuchecken = string.toUpperCase().trim();
	}
		
	/**
	 * Regex-Methode.
	 * 
	 * @return boolean
	 */
		public boolean check(){
			if (this.zuchecken!= null && !this.zuchecken.isEmpty()){
				if (Pattern.matches("GEHE UHR_\\d{1,2}", this.zuchecken)){
					return true;
				}else if (Pattern.matches("BEKAEMPFE GEGNER UHR_\\d{1,2}", this.zuchecken)){
					return true;
				}else if (Pattern.matches("RETTE FREUND UHR_\\d{1,2}", this.zuchecken)){
					return true;
				}
			
			}
			
			return false;
		}
}
