package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for creating a new fee.
 * This class encapsulates the necessary information for creating a fee
 * and is used to transfer data between different application layers.
 */
public class CreateFeeDto {

    /**
     * The ID of the apartment associated with the fee.
     * Must be a non-null value.
     */
    @NotNull(message = "Apartment ID cannot be null.")
    private Long apartmentId;

    /**
     * The amount of the fee.
     * Must be a non-null positive value.
     */
    @NotNull(message = "Fee amount cannot be null.")
    @Positive(message = "Fee amount must be a positive value.")
    private BigDecimal amount;

    /**
     * The date when the fee was paid.
     * Must be a non-null value.
     */
    @NotNull(message = "Payment date cannot be null.")
    private LocalDate paymentDate;

    /**
     * Default constructor for CreateFeeDto.
     */
    public CreateFeeDto() {}

    /**
     * Parameterized constructor for CreateFeeDto.
     *
     * @param apartmentId  the ID of the associated apartment.
     * @param amount       the fee amount.
     * @param paymentDate  the payment date.
     */
    public CreateFeeDto(Long apartmentId, BigDecimal amount, LocalDate paymentDate) {
        this.apartmentId = apartmentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters

    /**
     * Gets the ID of the associated apartment.
     *
     * @return the apartment ID.
     */
    public Long getApartmentId() {
        return apartmentId;
    }

    /**
     * Sets the ID of the associated apartment.
     *
     * @param apartmentId the apartment ID.
     */
    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    /**
     * Gets the fee amount.
     *
     * @return the fee amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the fee amount.
     *
     * @param amount the fee amount.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the payment date of the fee.
     *
     * @return the payment date.
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the payment date of the fee.
     *
     * @param paymentDate the payment date.
     */
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}

