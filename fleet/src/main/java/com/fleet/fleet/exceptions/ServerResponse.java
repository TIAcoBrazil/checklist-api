package com.fleet.fleet.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ServerResponse {
    private String message;
    private List<String> details;
    private Integer status;
}
