package edu.tsi1.gr5.crazyfinger.skinning;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("skinBean")
@Scope(ScopeType.SESSION)
public class SkinBean {
	 
	   private String skin = "deepMarine";
	 
	   public String getSkin() {
		return skin;
	   }
	   public void setSkin(String skin) {
		this.skin = skin;
	   }
	}
