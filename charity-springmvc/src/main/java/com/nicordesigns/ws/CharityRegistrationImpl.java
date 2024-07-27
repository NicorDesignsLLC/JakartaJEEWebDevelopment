package com.nicordesigns.ws;

import org.springframework.stereotype.Service;

import com.nicordesigns.ws.generated.CharityRegistrationRequestType;
import com.nicordesigns.ws.generated.CharityRegistrationResponseType;

@Service
public class CharityRegistrationImpl implements CharityRegistrationService  {

	@Override
	public CharityRegistrationResponseType charityRegistration(CharityRegistrationRequestType request) {
		System.out.println("charityRegistration incoming request: " + ((CharityRegistrationRequestType) request));
		CharityRegistrationResponseType response = new CharityRegistrationResponseType();
        response.setValue("Registration Successful");
        response.setRegistrationId((short) 1234);
        response.setStatusCode((short) 200);
        response.setStatusMessage("OK");
        System.out.println("charityRegistration outgoing response: " + response);
		
        return response;
	}

}
