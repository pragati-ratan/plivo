package com.plivo.contactBook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllResponseDTO<T> {
    @NonNull
    List<T> data;
    @NonNull
    long count;
}
