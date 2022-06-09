package br.inatel.quotationmanagement.support;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import br.inatel.quotationmanagement.services.ApiStockService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApiInitialRegister {

	private ApiStockService apiStockService;

	public ApiInitialRegister(ApiStockService apiStockService) {
		this.apiStockService = apiStockService;
	}

	@PostConstruct
	public void startRegister() {
		log.info("Registrando na API externa");
		apiStockService.registerOnApi();
	}
}
