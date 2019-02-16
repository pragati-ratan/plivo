package com.plivo.contactBook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plivo.contactBook.utils.Constants;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users_info")
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    private int id;

    @Column(name = "email_id", unique = true)
    @Email
    String emailId;

    @NotNull
    @Size(min = Constants.Constraints.NAME_MIN, max = Constants.Constraints.NAME_MAX)
    String name;

    @JsonIgnore
    @NotNull
    @Size(min = Constants.Constraints.PASSWORD_MIN, max = Constants.Constraints.PASSWORD_MAX)
    String password;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTimestamp;
}
