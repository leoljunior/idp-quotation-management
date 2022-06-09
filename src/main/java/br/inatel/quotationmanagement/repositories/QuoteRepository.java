package br.inatel.quotationmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.quotationmanagement.models.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

}
