package com.devsuperior.salesquery.sale.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.salesquery.sale.dto.SaleReportDTO;
import com.devsuperior.salesquery.sale.dto.SaleSummaryDTO;
import com.devsuperior.salesquery.sale.repository.SaleRepository;

@Service
public class SaleService {

	private final SaleRepository saleRepository;

	private final Clock clock;

	public SaleService(SaleRepository saleRepository, Clock clock) {
		this.saleRepository = saleRepository;
		this.clock = clock;
	}

	@Transactional(readOnly = true)
	public Page<SaleReportDTO> listReport(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable) {
		LocalDate periodMaxDate = resolveMaxDate(maxDate);
		LocalDate periodMinDate = resolveMinDate(minDate, periodMaxDate);
		return saleRepository.listReport(periodMinDate, periodMaxDate, sellerName, pageable);
	}

	@Transactional(readOnly = true)
	public List<SaleSummaryDTO> listSummary(LocalDate minDate, LocalDate maxDate) {
		LocalDate periodMaxDate = resolveMaxDate(maxDate);
		LocalDate periodMinDate = resolveMinDate(minDate, periodMaxDate);
		return saleRepository.listSummary(periodMinDate, periodMaxDate);
	}

	private LocalDate resolveMaxDate(LocalDate maxDate) {
		return maxDate != null ? maxDate : LocalDate.now(clock);
	}

	private LocalDate resolveMinDate(LocalDate minDate, LocalDate periodMaxDate) {
		return minDate != null ? minDate : periodMaxDate.minusYears(1L);
	}

}
