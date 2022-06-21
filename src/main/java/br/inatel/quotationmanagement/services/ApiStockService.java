package br.inatel.quotationmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
	
//	private RestTemplate restTemplate;
	
//	private WebClient webClient;
//
//	public ApiStockService(WebClient webClient) {
//		this.webClient = webClient;
//	}

	@Cacheable(value = "allStocksApi")
	public List<ApiStockDTO> getAllStocksFromExtAPI() {
		log.info("Buscando Stocks registrados na API externa");
		List<ApiStockDTO> apiStockDTO = new ArrayList<ApiStockDTO>();
		Flux<ApiStockDTO> fluxNewStock = WebClient.create(stockManagerURL)
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
		Mono<ApiStockDTO> monoNewStock = WebClient.create(stockManagerURL)
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
		json.put("port", 8081);

		WebClient.create(stockManagerURL)
				.method(HttpMethod.POST)				
				.uri("/notification")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(json.toString())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		log.info("Quotation Management registrado com sucesso!");
	}
	
//	public void registerOnApi() {
//		this.restTemplate = new RestTemplate();
//				
//		log.info("Registering for notification!");
//
//		HttpHeaders header = new HttpHeaders();
//		header.setContentType(MediaType.APPLICATION_JSON);
//
//		JSONObject body = new JSONObject();
//		body.put("host", "localhost");
//		body.put("port", 8081);
//
//		HttpEntity<String> request = new HttpEntity<String>(body.toString(), header);
//
//		restTemplate.postForObject("http://localhost:8080/notification", request, String.class);
//	}

}
