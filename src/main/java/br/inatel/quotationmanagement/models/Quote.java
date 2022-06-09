package br.inatel.quotationmanagement.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quote")
public class Quote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate date;
	
	private BigDecimal value;
	
	@ManyToOne
	@JoinColumn(name = "stock_id", referencedColumnName = "id")
	private Stock stock;

	public Quote(LocalDate date, BigDecimal value, Stock stock) {
		this.date = date;
		this.value = value;
		this.stock = stock;
	}
	
	
}
