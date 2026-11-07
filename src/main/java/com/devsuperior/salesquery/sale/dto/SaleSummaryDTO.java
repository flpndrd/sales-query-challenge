package com.devsuperior.salesquery.sale.dto;

import java.math.BigDecimal;

public record SaleSummaryDTO(
		String sellerName,
		BigDecimal total) {}
