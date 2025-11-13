package com.hsf.laptopshop.repository;

import java.math.BigDecimal;

public interface RevenueByYearProjection {
    Integer getYear();
    BigDecimal getTotalRevenue();
}
