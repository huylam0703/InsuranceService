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
    VEHICLE_DETAIL_NOT_FOUND(1308,"vehicle not found", HttpStatus.NOT_FOUND),
    TRAVEL_DETAIL_NOT_FOUND(1309,"travel not found", HttpStatus.NOT_FOUND),

    HEALTH_HEIGHT_CONTRACT(1310, "Height is required", HttpStatus.BAD_REQUEST),
    MIN_HEIGHT_HEALTH(1311, "Height must be at least 50 cm", HttpStatus.BAD_REQUEST),
    MAX_HEIGHT_HEALTH(1312, "Height must be at most 300 cm", HttpStatus.BAD_REQUEST),
    HEALTH_WEIGHT_CONTRACT(1313, "Weight is required", HttpStatus.BAD_REQUEST),
    BLOOD_TYPE_HEALTH(1314, "Blood type is required", HttpStatus.BAD_REQUEST),
    BLOOD_TYPE(1315, "Blood type must be A+, A-, B+, B-, AB+, AB-, O+, or O-", HttpStatus.BAD_REQUEST),
    MEDICAL_HISTORY_HEALTH(1316, "Medical history must not exceed 1000 characters", HttpStatus.BAD_REQUEST),
    ALLERGIES_HEALTH(1317, "Allergies must not exceed 500 characters", HttpStatus.BAD_REQUEST),
    SMOKING_HEALTH(1318, "Smoking status is required", HttpStatus.BAD_REQUEST),
    ALCOHOL_USE_HEALTH(1319, "Alcohol use status is required", HttpStatus.BAD_REQUEST),

    OCCUPATION_LIFE(1320, "Occupation is required", HttpStatus.BAD_REQUEST),
    ANNUAL_INCOME_LIFE(1321, "Annual income is required", HttpStatus.BAD_REQUEST),
    ANNUAL_INCOME(1322, "Annual income must be greater than 0", HttpStatus.BAD_REQUEST),
    ANNUAL_INCOME_INVALID(1323, "Annual income format is invalid", HttpStatus.BAD_REQUEST),
    BENEFICIARY_NAME_LIFE(1324, "BENEFICIARY_NAME_LIFE", HttpStatus.BAD_REQUEST),
    BENEFICIARY_RELATIONSHIP_LIFE(1325, "Beneficiary relationship is required", HttpStatus.BAD_REQUEST),
    BENEFICIARY_PHONE_LIFE(1326, "Beneficiary phone is required", HttpStatus.BAD_REQUEST),
    BENEFICIARY_PHONE_INVALID(1327, "Invalid beneficiary phone number", HttpStatus.BAD_REQUEST),
    MEDICAL_HISTORY_LIFE(1328, "Medical history must not exceed 1000 characters", HttpStatus.BAD_REQUEST),
    SMOKING_LIFE(1329, "Smoking status is required", HttpStatus.BAD_REQUEST),

    DESTINATION_COUNTRY_TRAVEL(1330, "Destination country is required", HttpStatus.BAD_REQUEST),
    DEPARTURE_DATE_TRAVEL(1331, "Departure date is required", HttpStatus.BAD_REQUEST),
    DEPARTURE_DATE_VALID(1332, "Departure date must be today or in the future", HttpStatus.BAD_REQUEST),
    RETURN_DATE_TRAVEL(1333, "Return date is required", HttpStatus.BAD_REQUEST),
    RETURN_DATE_VALID(1334, "Return date must be in the future", HttpStatus.BAD_REQUEST),
    PASSPORT_NUMBER(1335, "Passport number is required", HttpStatus.BAD_REQUEST),
    PASSPORT_NUMBER_VALID(1336, "Invalid passport number format", HttpStatus.BAD_REQUEST),
    TRAVEL_PURPOSE(1337, "Travel purpose is required", HttpStatus.BAD_REQUEST),
    EMERGENCY_CONTACT_NAME(1338, "Emergency contact name is required", HttpStatus.BAD_REQUEST),
    EMERGENCY_CONTACT_PHONE(1339, "Emergency contact phone is required", HttpStatus.BAD_REQUEST),
    EMERGENCY_CONTACT_PHONE_INVALID(1340, "Invalid emergency contact phone number", HttpStatus.BAD_REQUEST),
    IS_RETURN_DATE_VALID(1341, "Return date must be after departure date", HttpStatus.BAD_REQUEST),

    VEHICLE_TYPE(1342, "Vehicle type is required", HttpStatus.BAD_REQUEST),
    LICENSE_PLATE(1343, "License plate is required", HttpStatus.BAD_REQUEST),
    BRAND_VEHICLE(1344, "Brand is required", HttpStatus.BAD_REQUEST),
    MODEL_VEHICLE(1345, "Model is required", HttpStatus.BAD_REQUEST),
    MANUFACTURE_YEAR_VEHICLE(1346, "Manufacture year is required", HttpStatus.BAD_REQUEST),
    MANUFACTURE_YEAR_VALID(1347, "Manufacture year must be greater than or equal to 1900", HttpStatus.BAD_REQUEST),
    CHASSIS_NUMBER_VEHICLE(1348, "Chassis number is required", HttpStatus.BAD_REQUEST),
    ENGINE_NUMBER_VEHICLE(1349, "Engine number is required", HttpStatus.BAD_REQUEST),
    IS_MANUFACTURE_YEAR_VALID(1350, "Manufacture year cannot be in the future", HttpStatus.BAD_REQUEST),

    CONTRACT_NOT_ACTIVE(1360, "Contract is not active", HttpStatus.BAD_REQUEST),

    //Policy
    POLICY_NAME(1400, "Policy name is required", HttpStatus.BAD_REQUEST),
    POLICY_TYPE(1401, "Policy type is required", HttpStatus.BAD_REQUEST),
    POLICY_DESCRIPTION(1402, "Description is required", HttpStatus.BAD_REQUEST),
    COVERAGE_AMOUNT(1403, "Coverage amount is required", HttpStatus.BAD_REQUEST),
    COVERAGE_AMOUNT_VALID(1404, "Coverage amount must be greater than 0", HttpStatus.BAD_REQUEST),
    COVERAGE_AMOUNT_FORMAT(1405, "Coverage amount format is invalid", HttpStatus.BAD_REQUEST),
    PREMIUM_AMOUNT(1406, "Premium amount is required", HttpStatus.BAD_REQUEST),
    PREMIUM_AMOUNT_VALID(1407, "Premium amount must be greater than 0", HttpStatus.BAD_REQUEST),
    PREMIUM_AMOUNT_FORMAT(1408, "Premium amount format is invalid", HttpStatus.BAD_REQUEST),
    DURATION_MONTHS_MIN(1409, "Duration months must be at least 1", HttpStatus.BAD_REQUEST),
    DURATION_MONTHS_MAX(1410, "Duration months must not exceed 600", HttpStatus.BAD_REQUEST),
    CLAIM_LIMIT_MIN(1411, "Claim limit must be at least 1", HttpStatus.BAD_REQUEST),
    CLAIM_LIMIT_MAX(1412, "Claim limit must not exceed 600", HttpStatus.BAD_REQUEST),
    IS_PREMIUM_VALID(1413, "Premium amount must not exceed coverage amount", HttpStatus.BAD_REQUEST),

    //Claim
    CLAIM_AMOUNT_EXCEED_REMAINING_COVERAGE(1500, "amount claim exceed remaining coverage", HttpStatus.BAD_REQUEST),
    INVALID_INCIDENT_DATE(1501, "incident date invalid", HttpStatus.BAD_REQUEST),
    CLAIM_NOT_FOUND(1502, "claim not found", HttpStatus.NOT_FOUND),

    CONTRACT_ID_REQUIRED(1503, "Contract id is required", HttpStatus.BAD_REQUEST),
    TITLE_REQUIRED(1504, "Title is required", HttpStatus.BAD_REQUEST),
    DESCRIPTION_REQUIRED(1505, "Description is required", HttpStatus.BAD_REQUEST),
    INCIDENT_DATE_REQUIRED(1506, "Incident date is required", HttpStatus.BAD_REQUEST),
    INCIDENT_DATE_INVALID(1507, "Incident date cannot be in the future", HttpStatus.BAD_REQUEST),
    CLAIM_AMOUNT_REQUIRED(1508, "Claim amount is required", HttpStatus.BAD_REQUEST),
    CLAIM_AMOUNT_INVALID(1509, "Claim amount must be greater than 0", HttpStatus.BAD_REQUEST),
    STATUS_REQUIRED(1510, "Status is required", HttpStatus.BAD_REQUEST),
    APPROVED_AMOUNT_INVALID(1511, "Approved amount must be greater than or equal to 0", HttpStatus.BAD_REQUEST),
    REJECTION_REASON_TOO_LONG(1512, "Rejection reason must not exceed 2000 characters", HttpStatus.BAD_REQUEST),
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
