package com.hsf.laptopshop.repository;

import com.hsf.laptopshop.entity.InvoiceEntity;
import com.hsf.laptopshop.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    //Biểu đồ trong 1 tháng
    @Query("SELECT FUNCTION('MONTH', i.order.createdAt) as month, SUM(i.totalAmount) as totalRevenue " +
            "FROM InvoiceEntity i " +
            "WHERE i.order.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('MONTH', i.order.createdAt) " +
            "ORDER BY FUNCTION('MONTH', i.order.createdAt) ASC")
    List<RevenueByMonthProjection> findRevenueByMonth(LocalDateTime startDate, LocalDateTime endDate);

    //Biểu đồ trong 1 năm
    @Query("SELECT FUNCTION('YEAR', i.order.createdAt) as year, SUM(i.totalAmount) as totalRevenue " +
            "FROM InvoiceEntity i " +
            "WHERE i.status = 'PAID' " + // Chỉ tính đơn đã thanh toán
            "GROUP BY FUNCTION('YEAR', i.order.createdAt) " +
            "ORDER BY year ASC")
    List<RevenueByYearProjection> findRevenueByYear();

    Optional<InvoiceEntity> findByOrder(OrderEntity order);
}
