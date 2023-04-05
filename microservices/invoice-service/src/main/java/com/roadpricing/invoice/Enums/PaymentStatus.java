package com.roadpricing.invoice.Enums;

public class PaymentStatus {

    enum Status {
        UNPAID("Unpaid"),
        PENDING("Pending"),
        PAID("Paid");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
