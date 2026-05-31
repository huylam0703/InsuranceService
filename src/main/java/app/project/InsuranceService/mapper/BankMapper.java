package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.response.Bank.BankResponse;
import app.project.InsuranceService.entity.Bank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankResponse toBankResponse(Bank bank);
}
