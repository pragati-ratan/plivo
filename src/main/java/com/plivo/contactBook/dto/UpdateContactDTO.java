package com.plivo.contactBook.dto;

import com.plivo.contactBook.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateContactDTO {
    @NotNull
    Integer id;

    @Email
    String emailId;

    @Size(min = Constants.Constraints.NAME_MIN, max = Constants.Constraints.NAME_MAX)
    String name;

    @Size(min = Constants.Constraints.CONTACT_NUM, max = Constants.Constraints.CONTACT_NUM)
    String contactNumber;

    @Size(min = Constants.Constraints.ADDRESS_MIN, max = Constants.Constraints.ADDRESS_MAX)
    String address;

    String tag;
}
