package br.inatel.quotationmanagement.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.models.Stock;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDTO {

	private String id;
	
	private String stockId;
	
	private Map<String, String> quotes = new HashMap<>();
	
	public StockDTO(Stock stock) {
		this.id = stock.getId().toString();
		this.stockId = stock.getStockId();		
		this.quotes = listToMap(stock);
	}
	
	public Map<String, String> listToMap(Stock stock) {
		Map<String, String> quoteMap = new HashMap<>();
		stock.getQuotes()
				.stream()
				.forEach(q -> quoteMap.put(q.getDate().toString(), q.getValue().toString()));
		return quoteMap;
	}
	
	public static List<StockDTO> convertToStockDTOList(List<Stock> stockList) {
		return stockList.stream().map(StockDTO::new).collect(Collectors.toList());
	}
}
