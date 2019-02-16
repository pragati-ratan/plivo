package com.plivo.contactBook.models;

import com.plivo.contactBook.utils.Constants;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "contacts_info")
public class Contacts implements Serializable {

    @Id
    @SequenceGenerator(name = "contact_seq", sequenceName = "contacts_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq")
    private int id;

    @Column(name = "email_id", unique = true)
    @Email
    String emailId;

    @NotNull
    @Size(min = Constants.Constraints.NAME_MIN, max = Constants.Constraints.NAME_MAX)
    String name;

    @NotNull
    @Size(min = Constants.Constraints.CONTACT_NUM, max = Constants.Constraints.CONTACT_NUM)
    @Column(name = "contact_number")
    String contactNumber;

    @Size(min = Constants.Constraints.ADDRESS_MIN, max = Constants.Constraints.ADDRESS_MAX)
    String address;

    @Size(max = Constants.Constraints.ADDRESS_MAX)
    String tag;
}
