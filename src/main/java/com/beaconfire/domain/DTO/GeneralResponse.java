package com.beaconfire.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralResponse<T> {

    private Status status;

    private String message;

    private T body;

    public enum Status{
        SUCCESS,
        FAILED
    }
}
