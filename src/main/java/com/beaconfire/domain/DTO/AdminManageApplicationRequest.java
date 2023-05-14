package com.beaconfire.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminManageApplicationRequest {

    @NotBlank(message = "feedback must not be blank")
    String feedback;

    @Pattern(regexp = "^(reject|approve)$", message = "operation must be either 'reject' or 'approve'")
    String operation;
}
