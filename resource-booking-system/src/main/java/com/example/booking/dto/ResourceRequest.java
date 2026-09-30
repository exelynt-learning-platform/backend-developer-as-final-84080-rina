package com.example.booking.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ResourceRequest(@NotBlank @Size(max=120) String name, @Size(max=1000) String description,
                              @NotNull @DecimalMin(value="0.01") BigDecimal price, boolean available) {}
