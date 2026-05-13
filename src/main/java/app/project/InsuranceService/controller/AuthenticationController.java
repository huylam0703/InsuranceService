package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.AuthenticationRequest;
import app.project.InsuranceService.dto.request.IntrospectRequest;
import app.project.InsuranceService.dto.request.LogOutRequest;
import app.project.InsuranceService.dto.request.RefeshRequest;
import app.project.InsuranceService.dto.response.ApiResponse;
import app.project.InsuranceService.dto.response.AuthenticationResponse;
import app.project.InsuranceService.dto.response.IntrospectResponse;
import app.project.InsuranceService.service.User.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("auth")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .code(1000)
                        .result(result)
                        .build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<IntrospectResponse>builder()
                        .code(1000)
                        .result(result)
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogOutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logOut(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(1000)
                        .message("Logout successful")
                        .build());
    }

    @PostMapping("/refesh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refesh(@RequestBody RefeshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refeshToken(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .code(1000)
                        .result(result)
                        .build());
    }

}
