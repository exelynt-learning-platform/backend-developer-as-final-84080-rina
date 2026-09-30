package com.example.booking.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.booking.entity.ReservationStatus;
public record ReservationRequest(@NotNull Long resourceId, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime,
                                 @NotNull @DecimalMin(value="0.01") BigDecimal price, ReservationStatus status) {}
