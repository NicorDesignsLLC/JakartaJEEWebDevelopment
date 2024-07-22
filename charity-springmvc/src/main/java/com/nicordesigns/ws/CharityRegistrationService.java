package com.nicordesigns.ws;

import com.nicordesigns.ws.CharityRegistrationRequest;
import com.nicordesigns.ws.CharityRegistrationResponse;

public interface CharityRegistrationService { //name to align with generated WSDL wsdl:portType
	
	    CharityRegistrationResponse charityRegistration(CharityRegistrationRequest request);

}
