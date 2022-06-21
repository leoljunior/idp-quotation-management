package br.inatel.quotationmanagement.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import br.inatel.quotationmanagement.models.Quote;
import br.inatel.quotationmanagement.models.Stock;

class StockDTOTest {

	private StockDTO stockDTO;	
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.stockDTO = new StockDTO();
	}
	
	@Test
	void deveriaConverterUmaListaParaUmMap() {
		Stock stock = createAStock();
		
		Map<String, String> result = stockDTO.listToMap(stock);
		
		assertEquals(HashMap.class, result.getClass());
	}
	
	@Test
	void deveriaConverterUmaListaDeStockQuoteEmUmaListaDeStockQuoteDTO() {
		Stock stock = createAStock();
		List<Stock> stockList = Arrays.asList(stock, stock, stock);
		
		List<StockDTO> result = StockDTO.convertToStockDTOList(stockList);
		
		assertEquals(ArrayList.class, result.getClass());
		assertTrue(result.get(0) instanceof StockDTO);
		assertEquals(3, result.size());
	}

	private Stock createAStock() {
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(1L, LocalDate.now(), new BigDecimal("300"), null));
		quotes.add(new Quote(2L, LocalDate.now().plusDays(1), new BigDecimal("400"), null));
		quotes.add(new Quote(3L, LocalDate.now().plusDays(2), new BigDecimal("500"), null));
		Stock stock = new Stock("gfvarefg", "petr4", quotes);
		return stock;
	}
}
