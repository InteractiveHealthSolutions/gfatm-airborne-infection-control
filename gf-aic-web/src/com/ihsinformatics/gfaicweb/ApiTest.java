package com.ihsinformatics.gfaicweb;

import com.ihsinformatics.tbreachapi.core.TBR;
import com.ihsinformatics.tbreachapi.core.service.impl.ServerService;

public class ApiTest {

	public static void main(String[] args) {
		ServerService service = new ServerService();
		TBR.readProperties("tbreach-api.properties");
		service.startup();
		service.login("admin", "Admin123");
	}
	
}
