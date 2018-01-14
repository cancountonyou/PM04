package ws1718_a4.darstellung;

import java.util.regex.Pattern;

public class AnweisungsCheck {
	private String zuchecken;
	
	
	public AnweisungsCheck(String string){
		zuchecken = string.toUpperCase().trim();
	}

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
