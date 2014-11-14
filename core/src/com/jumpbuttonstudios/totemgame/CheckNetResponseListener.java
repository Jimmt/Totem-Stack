package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpStatus;

public class CheckNetResponseListener implements HttpResponseListener {
	boolean hasInternet;
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		hasInternet = true;
		JBSApi.initialize();
	}

	@Override
	public void failed(Throwable t) {
		hasInternet = false;
	}

	@Override
	public void cancelled() {
		hasInternet = false;
	}
	
	public boolean hasInternet(){
		return hasInternet;
	}
}
