package com.rustam.Movie_Website.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("USER")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUser{
    private String username;
    private String iban;
    private LocalDate startDay;
}
