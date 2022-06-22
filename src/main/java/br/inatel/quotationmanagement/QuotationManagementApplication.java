package br.inatel.quotationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableCaching
@SpringBootApplication
public class QuotationManagementApplication {

//	@Bean
//	public WebClient webClient(WebClient.Builder builder) {
//		return builder.baseUrl("http://localhost:8080")
//		.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//		.build();
//	};	
	
	public static void main(String[] args) {
		SpringApplication.run(QuotationManagementApplication.class, args);
	}

}
