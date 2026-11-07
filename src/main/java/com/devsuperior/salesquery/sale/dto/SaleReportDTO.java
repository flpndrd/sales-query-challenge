package com.devsuperior.salesquery.sale.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SaleReportDTO(
		@JsonProperty("id")
		Long saleId,
		LocalDate date,
		BigDecimal amount,
		String sellerName) {}
