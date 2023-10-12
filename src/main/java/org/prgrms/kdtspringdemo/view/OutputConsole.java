package org.prgrms.kdtspringdemo.view;

import java.io.BufferedReader;

public class OutputConsole {
    private final String start_string = "> ";

    public void start() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create the program.");
        System.out.println("Type list to list all vouchers.");
        System.out.print(start_string);
    }
}