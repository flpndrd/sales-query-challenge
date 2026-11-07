package com.devsuperior.salesquery.sale.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

@SpringBootTest
@AutoConfigureMockMvc
class SaleControllerIT {

	private final MockMvcTester mockMvcTester;

	@Autowired
	SaleControllerIT(MockMvcTester mockMvcTester) {
		this.mockMvcTester = mockMvcTester;
	}

	@Nested
	class ListReport {

		@Test
		void shouldReturnSalesOfMatchingSellersWhenPeriodAndNameAreInformed() {
			MvcTestResult mvcTestResult = mockMvcTester
					.get()
					.uri("/sales/report?minDate=2022-05-01&maxDate=2022-05-31&name=odinson")
					.exchange();

			assertThat(mvcTestResult).hasStatusOk();
			assertThat(mvcTestResult)
					.bodyJson()
					.extractingPath("$.content[*].id")
					.asArray()
					.containsExactly(125, 126, 128);
			assertThat(mvcTestResult)
					.bodyJson()
					.extractingPath("$.content[*].sellerName")
					.asArray()
					.containsExactly("Loki Odinson", "Thor Odinson", "Loki Odinson");
			assertThat(mvcTestResult).bodyJson().extractingPath("$.page.totalElements").isEqualTo(3);
		}

		@Test
		void shouldReturnBadRequestWhenDateIsInvalid() {
			MvcTestResult mvcTestResult = mockMvcTester.get().uri("/sales/report?minDate=2022-13-01").exchange();

			assertThat(mvcTestResult).hasStatus(HttpStatus.BAD_REQUEST);
		}

	}

	@Nested
	class ListSummary {

		@Test
		void shouldReturnTotalBySellerWhenPeriodIsInformed() {
			MvcTestResult mvcTestResult = mockMvcTester
					.get()
					.uri("/sales/summary?minDate=2022-01-01&maxDate=2022-06-30")
					.exchange();

			assertThat(mvcTestResult).hasStatusOk();
			assertThat(mvcTestResult)
					.bodyJson()
					.extractingPath("$[*].sellerName")
					.asArray()
					.containsExactly("Anakin", "Logan", "Loki Odinson", "Padme", "Thor Odinson");
			assertThat(mvcTestResult)
					.bodyJson()
					.extractingPath("$[*].total")
					.asArray()
					.containsExactly(110571.0, 83587.0, 150597.0, 135902.0, 144896.0);
		}

	}

}
