package com.jumpbuttonstudios.totemgame;

import com.jumpbuttonstudio.api.API;

public class JBSApi {
	static API api;
	
	public static void initialize(){
		api = new API();
		api.connect();
		api.authenticate("m72od5evppzDV4equwhq", "jimmt", 8);
	}
	
	

}
