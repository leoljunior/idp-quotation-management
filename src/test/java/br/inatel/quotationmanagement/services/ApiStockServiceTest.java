package br.inatel.quotationmanagement.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.inatel.quotationmanagement.dtos.ApiStockDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiStockServiceTest {

	@Value("${stock.manager.url}")
	private String stockManagerURL;
	
	@Autowired
	private WebTestClient webTestClient;
	        
    @Test
    void deveriaCriarUmStockNaApiExt() {
    	ApiStockDTO apiStockDTO = new ApiStockDTO("netflix", "Netflix1");
    	
    	webTestClient.post()
    	.uri(stockManagerURL + "/stock")
    	.body(BodyInserters.fromValue(apiStockDTO))
    	.exchange()
    	.expectStatus().isOk()
    	.expectBody().returnResult();
    }
    
    @Test
    void nãoDeveriaCriarUmStockNaApiExt() {
    	ApiStockDTO apiStockDTO = new ApiStockDTO("", "Netflix1");
    	
    	webTestClient.post()
    	.uri(stockManagerURL + "/stock")
    	.body(BodyInserters.fromValue(apiStockDTO))
    	.exchange()
    	.expectStatus().is5xxServerError()
    	.expectBody().returnResult();
    }
    
    
    @Test
    void deveriaRetornarTodasStockDaApiExt() {
    	webTestClient.get()
    	.uri(stockManagerURL + "/stock")
    	.exchange()
    	.expectStatus().isOk()
    	.expectBody()
    	.jsonPath("$[0].id").isEqualTo("petr4")
    	.jsonPath("$[0].description").isEqualTo("Petrobras PN")
    	.jsonPath("$[1].id").isEqualTo("netflix")
    	.jsonPath("$[1].description").isEqualTo("Netflix1")
    	.jsonPath("$[2].id").isEqualTo("vale5")
    	.jsonPath("$[2].description").isEqualTo("Vale do Rio Doce PN");
    }
    
    @Test
    void deveriaRetornarUmaStockDaApiExtPelaId() {
    	webTestClient.get()
    	.uri(stockManagerURL + "/stock/petr4")
    	.exchange()
    	.expectStatus().isOk()
    	.expectBody()
    	.jsonPath("$.id").isEqualTo("petr4")
    	.jsonPath("$.description").isEqualTo("Petrobras PN");    	
    }
    
    @Test
    void naoDeveriaRetornarUmaStockDaApiExtPelaId() {
    	webTestClient.get()
    	.uri(stockManagerURL + "/stock/petr")
    	.exchange()
    	.expectStatus().isOk()
    	.expectBody()
    	.jsonPath("$.id").doesNotExist()
    	.jsonPath("$.description").doesNotExist();
    }
}
