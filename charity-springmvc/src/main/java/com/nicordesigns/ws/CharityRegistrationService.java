package com.nicordesigns.ws;

import com.nicordesigns.ws.generated.CharityRegistrationRequest;
import com.nicordesigns.ws.generated.CharityRegistrationResponse;

public interface CharityRegistrationService { //name to align with generated WSDL wsdl:portType
	
	    CharityRegistrationResponse charityRegistration(CharityRegistrationRequest request);

}
