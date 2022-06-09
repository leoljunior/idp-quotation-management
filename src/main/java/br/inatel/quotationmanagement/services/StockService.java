package br.inatel.quotationmanagement.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.quotationmanagement.dtos.ApiStockDTO;
import br.inatel.quotationmanagement.forms.StockForm;
import br.inatel.quotationmanagement.models.Quote;
import br.inatel.quotationmanagement.models.Stock;
import br.inatel.quotationmanagement.repositories.StockRepository;

@Service
public class StockService {

	private StockRepository stockRepository;
	private ApiStockService apiStockService;

	@Autowired
	public StockService(StockRepository stockRepository, ApiStockService apiStockService) {
		this.stockRepository = stockRepository;
		this.apiStockService = apiStockService;
	}

	public Stock create(Stock stock) {
		return stockRepository.save(stock);
	}

	public List<Stock> getAll() {
		return stockRepository.findAll();
	}

	public Stock getById(String stockId) {
		return stockRepository.findByStockId(stockId);
	}

	public Stock toEntity(StockForm stockForm) {
		Stock stock = stockRepository.findByStockId(stockForm.getStockId());
		stock = (!Objects.isNull(stock)) ? stock : (stock = new Stock(stockForm.getStockId()));
		List<Quote> quotes = convertQuoteMapToQuoteList(stockForm, stock);
		stock.addQuotes(quotes);
		return stock;
	}

	public List<Quote> convertQuoteMapToQuoteList(StockForm stockForm, Stock stock) {
		List<Quote> quotesList = new ArrayList<>();
		for (Map.Entry<String, String> quote : stockForm.getQuotes().entrySet()) {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate date = LocalDate.parse(quote.getKey(), dateFormatter);
			BigDecimal value = new BigDecimal(quote.getValue());
			value = value.setScale(2, RoundingMode.HALF_UP);
			quotesList.add(new Quote(date, value, stock));
		}
		return quotesList;
	}

	public boolean stockExistOnExtApi(String id) {
		List<ApiStockDTO> allStocksFromExtAPI = apiStockService.getAllStocksFromExtAPI();				
		for (ApiStockDTO stock : allStocksFromExtAPI) {			
			if (stock.getId().equalsIgnoreCase(id)) {
				return true;
			}
		}
		return false;
	}
}
