package com.devsuperior.salesquery.sale.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.salesquery.sale.dto.SaleReportDTO;
import com.devsuperior.salesquery.sale.dto.SaleSummaryDTO;
import com.devsuperior.salesquery.sale.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

	private final SaleService saleService;

	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}

	@GetMapping("/report")
	ResponseEntity<Page<SaleReportDTO>> listReport(
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate minDate,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate maxDate,
			@RequestParam(name = "name", defaultValue = "") String sellerName,
			Pageable pageable) {
		Page<SaleReportDTO> saleReportPage = saleService.listReport(minDate, maxDate, sellerName, pageable);
		return ResponseEntity.ok(saleReportPage);
	}

	@GetMapping("/summary")
	ResponseEntity<List<SaleSummaryDTO>> listSummary(
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate minDate,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate maxDate) {
		List<SaleSummaryDTO> saleSummaries = saleService.listSummary(minDate, maxDate);
		return ResponseEntity.ok(saleSummaries);
	}

}
