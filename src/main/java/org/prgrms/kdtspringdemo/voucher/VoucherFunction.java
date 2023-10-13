package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public enum VoucherFunction {
    CREATE("create", "바우처 등록", voucherController -> {
        try {
            voucherController.createVoucher();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),
    LIST_ALL_VOUCHERS("list", "바우처 목록", VoucherController::showAllVouchers),
    EXIT("exit", "프로그램을 종료합니다.", VoucherController::endProgram);
    private final String fun;
    private final String description;
    private final Consumer<VoucherController> voucherControllerConsumer;

    VoucherFunction(String fun, String description, Consumer<VoucherController> voucherControllerConsumer) {
        this.fun = fun;
        this.description = description;
        this.voucherControllerConsumer = voucherControllerConsumer;
    }

    public static VoucherFunction findByCode(String fun) {
        return Arrays.stream(values())
                .filter(option -> option.fun.equals(fun))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 명령어가 존재하지 않습니다."));
    }

    public void execute(VoucherController voucherController) {
        this.voucherControllerConsumer.accept(voucherController);
    }
}
