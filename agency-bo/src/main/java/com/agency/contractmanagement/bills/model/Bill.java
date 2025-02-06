package com.agency.contractmanagement.bills.model;

import com.agency.common.BaseEntity;
import com.agency.dto.bill.BillStatus;
import com.agency.dto.bill.BillType;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Bill extends BaseEntity<Long> {

    private String billNumber;
    private LocalDate paymentDate;
    private BigDecimal grossAmount;
    private BigDecimal netAmount;
    private BillStatus status;

    public abstract BillType getBillType();
}
