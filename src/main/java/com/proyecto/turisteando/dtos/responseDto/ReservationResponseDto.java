package com.proyecto.turisteando.dtos.responseDto;

import com.proyecto.turisteando.dtos.IDto;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class ReservationResponseDto implements IDto {

    private Long id;
    private LocalDateTime createdAt;
    private boolean status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int peopleCount;
    private Long touristPlanId;
}
