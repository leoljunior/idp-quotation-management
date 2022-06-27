package br.inatel.quotationmanagement.controllers;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.inatel.quotationmanagement.forms.StockForm;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void deveriaRetornarStatusCode200AoListarTodosStockQuotes() {
		webTestClient.get()
		.uri("/stock")
		.exchange()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectStatus().isOk()
		.expectBody().returnResult();
	}

	@Test
	void deveriaRetornarStatusCode201AoCriarUmStockQuote() {
		StockForm stock = createStockForm();
		webTestClient.post()
		.uri("/stock")
		.body(BodyInserters.fromValue(stock))
		.exchange()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectStatus().isCreated();
	}
	
	@Test
	void deveriaRetornarStatusCode400AoTentarCadastrarUmStockQuoteComDataJaCadastrada() {
		StockForm stock = createStockForm();
		webTestClient.post()
		.uri("/stock").body(BodyInserters.fromValue(stock))
		.exchange()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectStatus().isBadRequest();
	}

	@Test
	void deveriaRetornarStatusCode404AoTentarCadastrarUmStockQuoteComStockIdInexistente() {
		StockForm stock = createStockForm();
		stock.setStockId("blablabla");
		webTestClient.post()
		.uri("/stock")
		.body(BodyInserters.fromValue(stock))
		.exchange()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectStatus().isNotFound();
	}
		
	@Test
	void deveriaRetornarStatusCode200RetornarUmStockQuotePeloStockId() {
		webTestClient.get()
		.uri("/stock/petr4")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk();
	}
	
	@Test
	void deveriaRetornarStatusCode404AoRetornarStockQuotePeloStockIdInexistente() {
		webTestClient.get()
		.uri("/stock/blablabla")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isNotFound();
	}

	
	private StockForm createStockForm() {
		Map<String, String> quoteMap = new HashMap<>();
		quoteMap.put("12-12-2021", "100");
		StockForm stockForm = new StockForm("petr4", quoteMap);
		return stockForm;
	}

}
