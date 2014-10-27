package com.jumpbuttonstudios.totemgame;

import com.jumpbuttonstudio.api.API;

public class JBSApi {
	static API api;
	static boolean loggedIn;
	
	public static void initialize(){
		api = new API();
		api.connect();
		api.authenticate("ZLkf5T0KdkSoxw4Q2CUh", "jimmt", 14);
	}
	
	

}
