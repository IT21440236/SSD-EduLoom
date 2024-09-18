package com.ds.edustack.notification.dto;

import com.ds.edustack.notification.enums.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponseDto {
    private OtpStatus status;
    private String message;
}
