package br.inatel.quotationmanagement.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	private String stockId;
	
	@OneToMany(mappedBy = "stock", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<Quote> quotes = new ArrayList<>();

	public Stock(String stockId) {
		this.stockId = stockId;
	}
	public Stock(String stockId, List<Quote> quotes) {
		this.stockId = stockId;
		this.quotes = quotes;
	}
	public void addQuotes(List<Quote> quotes) {
		this.quotes.addAll(quotes);		
	}

	
	
	
}
