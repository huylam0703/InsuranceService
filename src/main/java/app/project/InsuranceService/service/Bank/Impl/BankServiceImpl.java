package app.project.InsuranceService.service.Bank.Impl;

import app.project.InsuranceService.dto.response.Bank.BankResponse;
import app.project.InsuranceService.mapper.BankMapper;
import app.project.InsuranceService.repository.BankRepository;
import app.project.InsuranceService.service.Bank.BankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankServiceImpl implements BankService {
    BankRepository bankRepository;
    BankMapper bankMapper;


    @Override
    @PreAuthorize("hasRole('USER')")
    public List<BankResponse> getAllBanks() {
        return bankRepository.findAllByOrderByShortNameAsc()
                .stream()
                .map(bankMapper::toBankResponse)
                .toList();
    }
}
