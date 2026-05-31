package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.response.UserBankAccount.UserBankAccountResponse;
import app.project.InsuranceService.entity.UserBankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserBankAccountMapper {

    @Mapping(source = "bank.code", target = "bankCode")
    @Mapping(source = "bank.name", target = "bankName")
    @Mapping(source = "bank.shortName", target = "bankShortName")
    @Mapping(source = "user.id", target = "userId")
    UserBankAccountResponse toResponse(UserBankAccount account);

}
