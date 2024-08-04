package com.nicordesigns.site.ws;

import org.springframework.stereotype.Service;

import com.nicordesigns.ws.CharityRegistrationRequest;
import com.nicordesigns.ws.CharityRegistrationResponse;

@Service
public class CharityRegistrationImpl implements CharityRegistrationService  {

	@Override
	public CharityRegistrationResponse charityRegistration(CharityRegistrationRequest request) {
		System.out.println("charityRegistration incoming request: " + ((CharityRegistrationRequest) request));
		CharityRegistrationResponse response = new CharityRegistrationResponse();
        response.setValue("Registration Successful");
        response.setRegistrationId((short) 1234);
        response.setStatusCode((short) 200);
        response.setStatusMessage("OK");
        System.out.println("charityRegistration outgoing response: " + response);
		
        return response;
	}

}
