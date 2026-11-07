package com.devsuperior.salesquery.sale.service;

import static org.mockito.BDDMockito.then;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.devsuperior.salesquery.sale.repository.SaleRepository;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

	private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2026-10-31T12:00:00Z"), ZoneOffset.UTC);

	private static final LocalDate TODAY = LocalDate.now(FIXED_CLOCK);

	private static final LocalDate ONE_YEAR_BEFORE_TODAY = LocalDate.of(2025, 10, 31);

	@Mock
	private SaleRepository saleRepository;

	private SaleService saleService;

	@BeforeEach
	void setUp() {
		saleService = new SaleService(saleRepository, FIXED_CLOCK);
	}

	@Nested
	class ListReport {

		private static final Pageable PAGEABLE = PageRequest.of(0, 20);

		@Test
		void shouldUseOneYearUntilTodayWhenNoDateIsInformed() {
			saleService.listReport(null, null, "", PAGEABLE);

			then(saleRepository).should().listReport(ONE_YEAR_BEFORE_TODAY, TODAY, "", PAGEABLE);
		}

		@Test
		void shouldUseOneYearBeforeMaxDateWhenMinDateIsMissing() {
			saleService.listReport(null, LocalDate.of(2022, 6, 30), "odinson", PAGEABLE);

			then(saleRepository)
					.should()
					.listReport(LocalDate.of(2021, 6, 30), LocalDate.of(2022, 6, 30), "odinson", PAGEABLE);
		}

		@Test
		void shouldUseTodayAsMaxDateWhenMaxDateIsMissing() {
			saleService.listReport(LocalDate.of(2026, 5, 1), null, "", PAGEABLE);

			then(saleRepository).should().listReport(LocalDate.of(2026, 5, 1), TODAY, "", PAGEABLE);
		}

		@Test
		void shouldUseInformedDatesWhenBothAreInformed() {
			saleService.listReport(LocalDate.of(2022, 5, 1), LocalDate.of(2022, 5, 31), "odinson", PAGEABLE);

			then(saleRepository)
					.should()
					.listReport(LocalDate.of(2022, 5, 1), LocalDate.of(2022, 5, 31), "odinson", PAGEABLE);
		}

	}

	@Nested
	class ListSummary {

		@Test
		void shouldUseOneYearUntilTodayWhenNoDateIsInformed() {
			saleService.listSummary(null, null);

			then(saleRepository).should().listSummary(ONE_YEAR_BEFORE_TODAY, TODAY);
		}

		@Test
		void shouldUseInformedDatesWhenBothAreInformed() {
			saleService.listSummary(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 6, 30));

			then(saleRepository).should().listSummary(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 6, 30));
		}

	}

}
