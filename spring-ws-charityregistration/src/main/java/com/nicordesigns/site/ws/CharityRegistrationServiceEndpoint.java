package com.nicordesigns.site.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.nicordesigns.ws.CharityRegistrationRequest;
import com.nicordesigns.ws.CharityRegistrationResponse;

@Endpoint
public class CharityRegistrationServiceEndpoint {

	private static final String NAMESPACE_URI = "http://www.nicordesigns.com/charityregistration";

	private CharityRegistrationService charityRegistrationService;
	
	@Autowired
	public CharityRegistrationServiceEndpoint(CharityRegistrationService charityRegistrationService) {
		this.charityRegistrationService = charityRegistrationService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CharityRegistrationRequest")
	@ResponsePayload
	public CharityRegistrationResponse charityRegistration(@RequestPayload CharityRegistrationRequest request) {
		
		return charityRegistrationService.charityRegistration(request);
		
//		CharityRegistrationResponse response = new CharityRegistrationResponse();
//		response.setValue("Registration Successful");
//		response.setRegistrationId((short) 1234);
//		response.setStatusCode((short) 200);
//		response.setStatusMessage("OK");
//		return response;
	}
}
