package app.project.InsuranceService.service.Bank;

import app.project.InsuranceService.dto.request.BankJson.BankJson;
import app.project.InsuranceService.entity.Bank;
import app.project.InsuranceService.repository.BankRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankMasterDataService {
    BankRepository bankRepository;
    ObjectMapper objectMapper;

    @Transactional
    public void importBanksFromJson() throws IOException {
        if(bankRepository.count() > 0){
            return;
        }

        try {
            ClassPathResource resource = new ClassPathResource("data/vietnam_bank_list.json");

            List<BankJson> bankJsons = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<BankJson>>() {}
                );

            List<Bank> banks = bankJsons.stream()
                    .map(bankJson -> Bank.builder()
                            .id(bankJson.getId())
                            .code(bankJson.getCode())
                            .shortName(bankJson.getShortName())
                            .name(bankJson.getName())
                            .bin(bankJson.getBin())
                            .build())
                    .toList();

            bankRepository.saveAll(banks);
        }
        catch (IOException e) {
            throw new RuntimeException("cannot import banks master data", e);
        }
    }
}
