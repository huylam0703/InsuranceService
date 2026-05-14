package app.project.InsuranceService.service.Auth;

import app.project.InsuranceService.dto.request.Auth.AuthenticationRequest;
import app.project.InsuranceService.dto.request.Auth.IntrospectRequest;
import app.project.InsuranceService.dto.request.Auth.LogOutRequest;
import app.project.InsuranceService.dto.request.Auth.RefeshRequest;
import app.project.InsuranceService.dto.response.Auth.AuthenticationResponse;
import app.project.InsuranceService.dto.response.Auth.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logOut(LogOutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refeshToken(RefeshRequest request) throws ParseException, JOSEException;
}
