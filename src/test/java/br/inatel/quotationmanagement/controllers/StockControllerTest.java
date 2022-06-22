package br.inatel.quotationmanagement.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.quotationmanagement.dtos.StockDTO;
import br.inatel.quotationmanagement.forms.StockForm;
import br.inatel.quotationmanagement.models.Quote;
import br.inatel.quotationmanagement.models.Stock;
import br.inatel.quotationmanagement.repositories.StockRepository;
import br.inatel.quotationmanagement.services.ApiStockService;
import br.inatel.quotationmanagement.services.StockService;

class StockControllerTest {

	private StockController stockController;

	@Mock
	private StockRepository stockRepository;
	@Mock
	private ApiStockService apiStockService;
	@Mock
	private StockService stockService;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.stockController = new StockController(stockService);
	}


	@Test
	void deveriaLancarUmaExceptionAoTentarCriarUmStockQuoteComStockIdInexistente() {
		assertThrows(ResponseStatusException.class, () -> {
			StockForm stockForm = new StockForm(null, null);
			stockController.createANewStockQuote(stockForm, null);
		});
	}
	
	@Test
	void deveriaRetornarUmHttpStatusNotFoundAoTentarCriarUmStockQuoteComStockIdInexistente() { 		
		try {
			StockForm stockForm = createStockForm();
			stockController.createANewStockQuote(stockForm, null);			
		} catch (ResponseStatusException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
			assertEquals("Stock de ID: (petr4) não cadastrado na API externa", e.getReason());
		}
	}
	
	@Test
	void deveriaRetornarUmHttpStatusOKquandoTentarListarTodosStockQuotes() {		
		ResponseEntity<List<StockDTO>> allStockQuotes = stockController.getAllStockQuotes();
		HttpStatus statusCode = allStockQuotes.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	void deveriaRetornarUmHttpStatusOKQuandoTentarListarUmStockQuoteComIdExistente() {
		Stock stock = createAStock();
		when(stockService.getById("petr4")).thenReturn(stock);
		ResponseEntity<?> stockQuoteById = stockController.getStockQuoteById("petr4");
		HttpStatus statusCode = stockQuoteById.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	void deveriaRetornarUmHttpStatusNotFoundQuandoTentarListarUmStockQuoteComIdInexistente() {
		Stock stock = createAStock();
		when(stockService.getById("any")).thenReturn(stock);
		ResponseEntity<?> stockQuoteById = stockController.getStockQuoteById("petr4");
		HttpStatus statusCode = stockQuoteById.getStatusCode();
		assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	
//	@Test
//	void aaa() throws URISyntaxException {
//		UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
//		StockForm stockForm = createStockForm();
//		when(stockService.stockExistOnExtApi(stockForm.getStockId())).thenReturn(true);
//		Stock stock = createAStock();
//		URI uri = uriBuilder.path("/stock/{id}").buildAndExpand(stock.getId()).toUri();
//		when(uriBuilder.path("/stock/{id}"));
//		when(stockService.toEntity(stockForm)).thenReturn(stock);
//		when(uriBuilder).thenReturn(uriBuilder);
//		ResponseEntity<?> createANewStockQuote = stockController.createANewStockQuote(stockForm, uriBuilder);
//		
//		
//		HttpStatus statusCode = createANewStockQuote.getStatusCode();
//		assertEquals(HttpStatus.CREATED, statusCode);
//	}
	
	

	private StockForm createStockForm() {
		Map<String, String> quoteMap = new HashMap<>();
		quoteMap.put("12-12-2022", "100");
		StockForm stockForm = new StockForm("petr4", quoteMap);
		return stockForm;

	}
	private Stock createAStock() {
		Stock stock = new Stock("petr4");
		stock.setId(UUID.randomUUID().toString());
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(1L, LocalDate.now(), new BigDecimal("300"), stock));
		stock.addQuotes(quotes);
		return stock;
	}
	private List<Stock> createStockList() {
		Stock stock = new Stock("petr4");
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(1L, LocalDate.now(), new BigDecimal("300"), stock));
		quotes.add(new Quote(2L, LocalDate.now().plusDays(1), new BigDecimal("400"), stock));
		quotes.add(new Quote(3L, LocalDate.now().plusDays(2), new BigDecimal("500"), stock));

		stock.addQuotes(quotes);

		List<Stock> stocks = new ArrayList<>();
		stocks.add(stock);
		stocks.add(stock);
		stocks.add(stock);

		return stocks;
	}
}
