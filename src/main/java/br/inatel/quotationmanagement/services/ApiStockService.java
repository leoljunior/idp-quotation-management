package br.inatel.quotationmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.quotationmanagement.dtos.ApiStockDTO;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ApiStockService {

	@Value("${stock.manager.url}")
	private String stockManagerURL;

	private WebClient webClient;

	public ApiStockService(WebClient webClient) {
		this.webClient = webClient;
	}

	@Cacheable(value = "allStocksApi")
	public List<ApiStockDTO> getAllStocksFromExtAPI() {
		log.info("Buscando Stocks registrados na API externa");
		List<ApiStockDTO> apiStockDTO = new ArrayList<ApiStockDTO>();
		Flux<ApiStockDTO> fluxNewStock = this.webClient
				.method(HttpMethod.GET)
				.uri("/stock")
				.retrieve()
				.bodyToFlux(ApiStockDTO.class);
		fluxNewStock.subscribe(flux -> apiStockDTO.add(flux));
		fluxNewStock.blockLast();
		return apiStockDTO;
	}
	
	@CacheEvict(value = {"allStocksApi", "stockByIdApi"}, allEntries = true)
	public ApiStockDTO createANewStockOnExtAPI(ApiStockDTO apiStockDTO) {
		Mono<ApiStockDTO> monoNewStock = this.webClient
				.method(HttpMethod.POST)
				.uri("/stock")
				.bodyValue(apiStockDTO)
				.retrieve()
				.bodyToMono(ApiStockDTO.class);
		return monoNewStock.block();
	}

	public void registerOnApi() {
		JSONObject json = new JSONObject();
		json.put("host", "localhost");
		json.put("port", "8081");

		System.out.println(json);

		this.webClient.method(HttpMethod.POST)
				.uri(stockManagerURL + "/notification")
				.bodyValue(json)
				.retrieve()
				.bodyToMono(Void.class);
		log.info("Quotation Management registrado com sucesso!");
	}

}
