package br.inatel.quotationmanagement.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.quotationmanagement.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

	Stock findByStockId(String stockId);
	
	Stock findByStockIdAndQuotesDate(String stockId, LocalDate date);

}
