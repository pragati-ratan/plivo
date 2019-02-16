package com.plivo.contactBook.cache.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.validation.constraints.NotNull;


@Data
@RedisHash(value = "userCache", timeToLive = 7200L)
public class CurrentUser {

    @Id
    @Indexed
    private String username;

    @NotNull
    private Integer id;

    @NotNull
    private String role;

}
