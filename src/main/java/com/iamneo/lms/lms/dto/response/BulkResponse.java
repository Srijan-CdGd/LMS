package com.iamneo.lms.lms.dto.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkResponse<T> {
    private T data;
    private Map<Integer, String> errors;
}
