package com.nicordesigns.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.nicordesigns.ws.generated.CharityRegistrationRequestType;
import com.nicordesigns.ws.generated.CharityRegistrationResponseType;

@Endpoint
public class CharityRegistrationServiceEndpoint {

	private static final String NAMESPACE_URI = "http://www.nicordesigns.com/charityregistration";

	private CharityRegistrationService charityRegistrationService;
	
	@Autowired
	public CharityRegistrationServiceEndpoint(CharityRegistrationService charityRegistrationService) {
		this.charityRegistrationService = charityRegistrationService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CharityRegistrationRequestType")
	@ResponsePayload
	public CharityRegistrationResponseType charityRegistration(@RequestPayload CharityRegistrationRequestType request) {
		
		return charityRegistrationService.charityRegistration(request);
	}
}
