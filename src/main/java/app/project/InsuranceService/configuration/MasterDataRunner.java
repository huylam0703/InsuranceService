package app.project.InsuranceService.configuration;

import app.project.InsuranceService.service.Bank.BankMasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MasterDataRunner {
    private final BankMasterDataService bankMasterDataService;

    @Bean
    CommandLineRunner seedMasterData() {
        return args -> bankMasterDataService.importBanksFromJson();
    }
}
