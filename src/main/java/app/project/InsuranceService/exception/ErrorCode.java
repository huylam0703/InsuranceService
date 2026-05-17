package app.project.InsuranceService.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998, "invalid key", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(9997, "role not found", HttpStatus.NOT_FOUND),

    //User
    USER_EXISTS(1001, "username already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "user not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTS(1003, "user not exists", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1004, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "You do not have permission", HttpStatus.FORBIDDEN),
    //--
    FIRST_NAME(1011, "The first name should not be left blank", HttpStatus.BAD_REQUEST),
    LAST_NAME(1012, "The last name should not be left blank", HttpStatus.BAD_REQUEST),
    USERNAME_UNVALID(1013, "username must be least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_UNVALID(1014, "password must be least {min} characters", HttpStatus.BAD_REQUEST),
    EMAIL_UNVALID(1015, "email invalid format", HttpStatus.BAD_REQUEST),
    IDENTITY_NUMBER_UNVALID(1016, "identity number must be least {min} characters", HttpStatus.BAD_REQUEST),

    //Auth
    AUTH_USERNAME(1101, "username should not be left blank", HttpStatus.BAD_REQUEST),
    AUTH_PASSWORD(1102, "password should not be left blank", HttpStatus.BAD_REQUEST),

    //Policy
    NAME_POLICY_EXISTS(1201,"name policy already exists", HttpStatus.BAD_REQUEST),
    POLICY_CODE_EXISTS(1202,"policy code already exists", HttpStatus.BAD_REQUEST),
    POLICY_NOT_FOUND(1203,"policy not found", HttpStatus.NOT_FOUND),

    //contract
    CONTRACT_NOT_FOUND(1301,"Contract not found", HttpStatus.NOT_FOUND),
    HEALTH_DETAIL_REQUIRED(1302,"health detail required", HttpStatus.BAD_REQUEST),
    LIFE_DETAIL_REQUIRED(1303,"life detail required", HttpStatus.BAD_REQUEST),
    VEHICLE_DETAIL_REQUIRED(1304,"vehicle detail required", HttpStatus.BAD_REQUEST),
    TRAVEL_DETAIL_REQUIRED(1305, "travel detail required", HttpStatus.BAD_REQUEST),
    HEALTH_DETAIL_NOT_FOUND(1306,"health detail not found", HttpStatus.NOT_FOUND),
    LIFE_DETAIL_NOT_FOUND(1307,"life detail not found", HttpStatus.NOT_FOUND),
    VEHICLE_NOT_FOUND(1308,"vehicle not found", HttpStatus.NOT_FOUND),
    TRAVEL_NOT_FOUND(1309,"travel not found", HttpStatus.NOT_FOUND),
    ;


    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
