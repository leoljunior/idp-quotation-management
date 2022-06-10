package br.inatel.quotationmanagement.services;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.inatel.quotationmanagement.dtos.ApiStockDTO;
import br.inatel.quotationmanagement.models.Quote;
import br.inatel.quotationmanagement.models.Stock;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
class ApiStockServiceTest {

	@Value("${stock.manager.url}")
	private String stockManagerURL;
	
	@Autowired
	private WebTestClient webTestClient;
	
//	@Autowired
	private ApiStockService apiStockService;
	
    private MockWebServer server;
    
//    @BeforeEach
//    void setUp() throws IOException {
//        server = new MockWebServer();
//        server.start();
//        String rootUrl = server.url(stockManagerURL + "/stock").toString();
//        apiStockService = new ApiStockService(WebClient.create(rootUrl));
//    }
//    
//    @AfterEach
//    void tearDown() throws IOException {
//        server.shutdown();
//    }
	
    
//    @Test
//    void sum_usesSumAPI() throws InterruptedException {
//        MockResponse response = new MockResponse();
//        server.enqueue(response);
//        List<ApiStockDTO> result = apiStockService.getAllStocksFromExtAPI();
//        RecordedRequest request = server.takeRequest();
//        asser(request.getMethod()).isEqualTo("GET");
//        assertThat(request.getPath()).startsWith("/api/sum");
//        assertThat(request.getRequestUrl().queryParameter("value1")).isEqualTo("5");
//        assertThat(request.getRequestUrl().queryParameter("value3")).isEqualTo("3");
//    }
    
    
    @Test
    void deveriaCriarUmStockNaApiExt() throws JSONException {
    	ApiStockDTO apiStockDTO = new ApiStockDTO("netflix", "Netflix1");
    	
    	webTestClient.post()
    	.uri(stockManagerURL + "/stock")
    	.body(BodyInserters.fromValue(apiStockDTO))
    	.exchange()
    	.expectStatus().isOk()
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
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//	@Test
//	void deveriaRetornarTodasStockDaApiExt() {
//		webTestClient.get()
//		.uri(stockManagerURL + "/stock")
//		.exchange()
//		.expectHeader().contentType(MediaType.APPLICATION_JSON)
//		.expectStatus().isOk()
//		.expectBody().returnResult();
//	}

//	@Test
//	void deveriaCriarUmNovoStockNaApiExt() {
//		
//		
//		Stock stock = createAStock();
//		
//		webTestClient.post()
//		.uri(stockManagerURL + "/stock")
//		.contentType(MediaType.APPLICATION_JSON)
//		.accept(MediaType.APPLICATION_JSON)
//		.body(Mono.just(stock), Stock.class)
//		.exchange()
//		.expectStatus().isCreated()
//		.expectHeader().contentType(MediaType.APPLICATION_JSON)
//		.expectBody().returnResult();
//	}
	
	private Stock createAStock() {
		Stock stock = new Stock("petr4");
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(1L, LocalDate.now(), new BigDecimal("300"), stock));
		stock.addQuotes(quotes);
		return stock;
	}
}
