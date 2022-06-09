package br.inatel.quotationmanagement.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.quotationmanagement.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

	Stock findByStockId(String stockId);

}
