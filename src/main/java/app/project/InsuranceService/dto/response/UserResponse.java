package app.project.InsuranceService.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String firstName;
    String lastName;
    String fullName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    String username;
    String password;
    String email;
    String identityNumber;
}
