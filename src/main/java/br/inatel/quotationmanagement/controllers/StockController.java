package br.inatel.quotationmanagement.controllers;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.dtos.StockDTO;
import br.inatel.quotationmanagement.exceptions.StockNotFoundException;
import br.inatel.quotationmanagement.forms.StockForm;
import br.inatel.quotationmanagement.models.Stock;
import br.inatel.quotationmanagement.services.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

	private StockService stockService;
//	private QuoteRepository quoteRepository;
//	private ApiStockService apiStockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}

	@Transactional
	@PostMapping
	public ResponseEntity<?> createANewStockQuote(@RequestBody @Valid StockForm stockForm,
			UriComponentsBuilder uriBuilder) {

		if (!stockService.stockExistOnExtApi(stockForm.getStockId())) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body("Stock de ID: (" + stockForm.getStockId() + ") não cadastrado na API externa");
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock de ID: (" + stockForm.getStockId() + ") não cadastrado na API externa");
		}
		Stock stock = stockService.toEntity(stockForm);
		stockService.create(stock);
		URI uri = uriBuilder.path("/stock/{id}").buildAndExpand(stock.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockDTO(stock));
	}

	@GetMapping
	public ResponseEntity<List<StockDTO>> getAllStockQuotes() {
		List<Stock> stockList = stockService.getAll();
		List<StockDTO> convertToStocDTOList = StockDTO.convertToStockDTOList(stockList);
		return ResponseEntity.status(HttpStatus.OK).body(convertToStocDTOList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getStockQuoteById(@PathVariable(name = "id") String id) {
		Stock stock = stockService.getById(id);
		if (stock == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Stock de ID: (" + id + ") não encontrado");
		}
		return ResponseEntity.status(HttpStatus.OK).body(new StockDTO(stock));
	}

//	@PostMapping
//	public ResponseEntity<ApiStockDTO> createAStock(@RequestBody ApiStockDTO apiStockDTO) {
//		return ResponseEntity.status(HttpStatus.CREATED).body(apiStockService.createANewStockOnExtAPI(apiStockDTO));
//	}

}
