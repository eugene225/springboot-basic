package org.prgrms.kdtspringdemo.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("local")
class MemoryVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
    static class Config {
    }
    @Autowired
    MemoryVoucherRepository memoryVoucherRepository;

    @Test
    @DisplayName("바우처 등록")
    void insert() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, "fixedAmount");
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 25, "percentDiscount");

        //when
        Voucher insertFixedAmountVoucher = memoryVoucherRepository.insert(fixedAmountVoucher);
        Voucher insertPercentDiscountVoucher = memoryVoucherRepository.insert(percentDiscountVoucher);

        //then
        assertThat(fixedAmountVoucher, samePropertyValuesAs(insertFixedAmountVoucher));
        assertThat(percentDiscountVoucher, samePropertyValuesAs(insertPercentDiscountVoucher));
    }

    @Test
    @DisplayName("바우처 등록 실패")
    void insertError() {
        try {
            //given
            Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 200, "percentDiscount");
            //when
            Voucher insertPercentDiscountVoucher = memoryVoucherRepository.insert(percentDiscountVoucher);
        } catch (RuntimeException e) {
            //then
            assertThat(e, instanceOf(RuntimeException.class));
        }
    }

    @Test
    @DisplayName("voucherId로 바우처 검색")
    void findById() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 20, "percentDiscount");
        memoryVoucherRepository.insert(voucher);

        //when
        Voucher findVoucher = memoryVoucherRepository.findById(voucher.getVoucherId()).get();

        //then
        assertThat(voucher, samePropertyValuesAs(findVoucher));
    }

    @Test
    @DisplayName("모든 바우처 조회")
    void getAllVouchers() {
        //given
        memoryVoucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 100, "fixedAmount"));
        memoryVoucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 2000, "fixedAmount"));
        memoryVoucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 5, "percentDiscount"));

        //when
        Map<UUID, Voucher> allVouchers = memoryVoucherRepository.findAll().get();

        //then
        assertThat(allVouchers.size(), is(3));
    }
}