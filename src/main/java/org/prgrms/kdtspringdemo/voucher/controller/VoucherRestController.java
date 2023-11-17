package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.dto.VoucherRequestDto;
import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    ResponseEntity<List<VoucherViewDto>> showAllVouchers() {
        List<VoucherViewDto> vouchers = voucherService.getVoucherViewDtoList();
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<VoucherViewDto> create(@ModelAttribute VoucherRequestDto voucherRequestDto) {
        VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(voucherRequestDto.getVoucherPolicy());
        long amount = voucherRequestDto.getAmount();
        VoucherViewDto voucher = voucherService.createVoucher(voucherTypeFunction, amount);
        return new ResponseEntity<>(voucher, HttpStatus.CREATED);
    }

    @GetMapping("/{voucherId}")
    ResponseEntity<VoucherViewDto> findById(@PathVariable UUID voucherId) {
        VoucherViewDto voucher = voucherService.findById(voucherId);
        return new ResponseEntity<>(voucher, HttpStatus.OK);
    }

    @GetMapping("/vouchers/policy")
    ResponseEntity<List<VoucherViewDto>> findByPolicy(@RequestParam String policy) {
        List<VoucherViewDto> vouchers = voucherService.findByPolicy(policy);
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @DeleteMapping("/{voucherId}")
    void deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
    }

}
