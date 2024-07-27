package com.nicordesigns.ws;

import com.nicordesigns.ws.generated.CharityRegistrationRequestType;
import com.nicordesigns.ws.generated.CharityRegistrationResponseType;

public interface CharityRegistrationService { //name to align with generated WSDL wsdl:portType
	
	    CharityRegistrationResponseType charityRegistration(CharityRegistrationRequestType request);

}
