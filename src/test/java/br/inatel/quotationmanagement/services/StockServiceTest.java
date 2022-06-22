package br.inatel.quotationmanagement.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.inatel.quotationmanagement.dtos.ApiStockDTO;
import br.inatel.quotationmanagement.forms.StockForm;
import br.inatel.quotationmanagement.models.Quote;
import br.inatel.quotationmanagement.models.Stock;
import br.inatel.quotationmanagement.repositories.StockRepository;

class StockServiceTest {

	private StockService stockService;

	@Mock
	private StockRepository stockRepository;
	@Mock
	private ApiStockService apiStockService;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.stockService = new StockService(stockRepository, apiStockService);
	}
	
	@Test
	public void deveriaCriarUmStockQuote() {		
		Stock stock = createAStock();
		
		when(stockRepository.save(stock)).thenReturn(stock);
		
		Stock createdStock = stockService.create(stock);		
		
		assertEquals(stock, createdStock);
		assertEquals(stock.getId(), createdStock.getId());
		assertEquals(createdStock.getQuotes().size(), 1);		
	}
	
	@Test
	public void naoDeveriaCriarUmStockQuoteComIdNulaOuVazia() {		
		Stock stock = createAStock();
		stock.setId(null);
		Stock createdStock = stockService.create(stock);		
		
		assertNotEquals(stock, createdStock);
		assertTrue(createdStock == null);
	}
	
	@Test
	public void naoDeveriaCriarUmStockQuoteComValorNegativo() {		
		Stock stock = createAStock();
		stock.getQuotes().forEach(d -> {
			d.setValue(new BigDecimal("-1"));
		});
		Stock createdStock = stockService.create(stock);		
		
		assertNotEquals(stock, createdStock);
		assertTrue(createdStock == null);
	}
	
	@Test
	public void deveriaRetornarTodosOsStocksQuotes() {
		List<Stock> stockList = createStockList();
		
		when(stockRepository.findAll()).thenReturn(stockList);
		
		List<Stock> newStockList = stockService.getAll();
		
		assertEquals(stockList, newStockList);
		assertEquals(newStockList.size(), 3);
	}
	
	@Test
	public void deveriaRetornarUmaListaVaziaDeStockQuotes() {		
		List<Stock> newStockList = stockService.getAll();
		
		assertTrue(newStockList.isEmpty());
		assertEquals(newStockList.size(), 0);
	}
	
	@Test
	public void deveriaRetornarUmStockQuotePeloId() {
		String id = "petr4";
		Stock stock = createAStock();
		
		when(stockRepository.findByStockId(id)).thenReturn(stock);
		
		Stock foundStockQuote = stockService.getById(id);
		
		assertEquals(id, foundStockQuote.getStockId());
	}
	
	@Test
	public void naoDeveriaRetornarUmStockQuotePeloId() {
		String id = "petr";
		Stock stock = createAStock();
		
		when(stockRepository.findByStockId(id)).thenReturn(stock);
		
		Stock foundStockQuote = stockService.getById(id);
		
		assertTrue(foundStockQuote != null);
	}
	
	@Test
	public void deveriaConverterUmStockFormParaUmStockModel() {
		StockForm stockForm = createStockForm();
		
		Stock createdStock = stockService.toEntity(stockForm);
		
		assertEquals(Stock.class, createdStock.getClass());
		
	}
	
	@Test
	public void deveriaConverterUmQuoteMapEmQuoteList() {
		Stock stock = createAStock();
		StockForm stockForm =  createStockForm();
		
		List<Quote> quoteList = stockService.convertQuoteMapToQuoteList(stockForm, stock);
		
		assertTrue(!quoteList.isEmpty());
	}
	
	@Test
	public void naoDeveriaConverterUmQuoteMapEmQuoteListComValoresInvalidos() {
		Stock stock = createAStock();
		StockForm stockForm =  createStockForm();
		
		Map<String, String> quote = new HashMap<>();
		quote.put("22-12-202", "100");
		
		stockForm.setQuotes(quote);
		
		assertThrows(DateTimeParseException.class,
				() -> stockService.convertQuoteMapToQuoteList(stockForm, stock));
	}
	
	
	@Test
	void deveriaRetornarTrueSeStockExistirNaApiExt() {
		List<ApiStockDTO> list = new ArrayList<>();
		list.add(new ApiStockDTO("petr4", "Petrobras"));
		when(apiStockService.getAllStocksFromExtAPI()).thenReturn(list);
		
		boolean stockExistOnExtApi = stockService.stockExistOnExtApi("petr4");
		
		assertTrue(stockExistOnExtApi);
	}
	
	@Test
	void deveriaRetornarFalseSeStockNaoExistirNaApiExt() {
		List<ApiStockDTO> list = new ArrayList<>();
		list.add(new ApiStockDTO("petr4", "Petrobras"));
		when(apiStockService.getAllStocksFromExtAPI()).thenReturn(list);
		
		boolean stockExistOnExtApi = stockService.stockExistOnExtApi("petr");
		
		assertFalse(stockExistOnExtApi);
	}
	
	
	@Test
	void deveriaRetornarTrueSeDataDoQuoteJaFoiCadastrada() {
		Stock stock = createAStock();	
		when(stockRepository.findByStockIdAndQuotesDate(stock.getStockId(), stock.getQuotes().get(0).getDate())).thenReturn(stock);
		
		boolean stockQuoteDateAlreadyExists = stockService.stockQuoteDateAlreadyExists("petr4", LocalDate.now());
		
		assertTrue(stockQuoteDateAlreadyExists);
	}
	
	@Test
	void deveriaRetornarFalseSeDataDoQuoteNaoFoiCadastrada() {
		Stock stock = createAStock();	
		when(stockRepository.findByStockIdAndQuotesDate(stock.getStockId(), stock.getQuotes().get(0).getDate())).thenReturn(stock);
		
		boolean stockQuoteDateAlreadyExists = stockService.stockQuoteDateAlreadyExists("petr4", LocalDate.now().plusDays(1));
		
		assertFalse(stockQuoteDateAlreadyExists);
	}
	
	
	
	
	
	
	
	
	
	
	
	

//	@Test
//	void deveRetornarTodosOsStockQuotes() {
//		stockRepository.findAll();
//		verify(stockRepository).findAll();
//
//	}

	private StockForm createStockForm() {
		Map<String, String> quoteMap = new HashMap<>();
		quoteMap.put("12-12-2022", "100");
		StockForm stockForm = new StockForm("petr4", quoteMap);
		return stockForm;

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
	
	private Stock createAStock() {
		Stock stock = new Stock("petr4");
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(1L, LocalDate.now(), new BigDecimal("300"), stock));
		stock.addQuotes(quotes);
		return stock;
	}
	
//	private List<Quote> createQuoteList() {
//		Stock stock = createAStock();
//		List<Quote> quoteList = new ArrayList<>();
//		quoteList.add(new Quote(LocalDate.now(), new BigDecimal("100"), stock));
//		quoteList.add(new Quote(LocalDate.now().plusDays(1), new BigDecimal("200"), stock));
//		return quoteList;
//	}
}