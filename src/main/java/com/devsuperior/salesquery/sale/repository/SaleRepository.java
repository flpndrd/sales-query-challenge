package com.devsuperior.salesquery.sale.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.salesquery.sale.dto.SaleReportDTO;
import com.devsuperior.salesquery.sale.dto.SaleSummaryDTO;
import com.devsuperior.salesquery.sale.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("""
			SELECT new com.devsuperior.salesquery.sale.dto.SaleReportDTO(
				sale.saleId,
				sale.date,
				sale.amount,
				seller.name)
			FROM Sale sale
			JOIN sale.seller seller
			WHERE sale.date BETWEEN :minDate AND :maxDate
			AND UPPER(seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))
			ORDER BY sale.date DESC
			""")
	Page<SaleReportDTO> listReport(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);

	@Query("""
			SELECT new com.devsuperior.salesquery.sale.dto.SaleSummaryDTO(
				seller.name,
				SUM(sale.amount))
			FROM Sale sale
			JOIN sale.seller seller
			WHERE sale.date BETWEEN :minDate AND :maxDate
			GROUP BY seller.name
			ORDER BY seller.name
			""")
	List<SaleSummaryDTO> listSummary(LocalDate minDate, LocalDate maxDate);

}
