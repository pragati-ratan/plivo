package com.plivo.contactBook.dto;

import com.plivo.contactBook.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    Object data;
    String message = "";
    Integer status = Constants.StatusCodes.SUCCESS;

    public ResponseDTO(Object data) {
        this.data = data;
    }
}