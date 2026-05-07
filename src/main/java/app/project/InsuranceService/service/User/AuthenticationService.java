package app.project.InsuranceService.service.User;

import app.project.InsuranceService.dto.request.AuthenticationRequest;
import app.project.InsuranceService.dto.request.IntrospectRequest;
import app.project.InsuranceService.dto.response.AuthenticationResponse;
import app.project.InsuranceService.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
