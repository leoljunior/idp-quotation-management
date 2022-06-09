package br.inatel.quotationmanagement.controllers;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/stockcache")
public class CacheController {

	@Transactional
	@DeleteMapping
	@CacheEvict(value = {"allStocksApi", "stockByIdApi"}, allEntries = true)
	public void CleaningCache() {
		log.info("Limpando o cache...");
	}
	
}
