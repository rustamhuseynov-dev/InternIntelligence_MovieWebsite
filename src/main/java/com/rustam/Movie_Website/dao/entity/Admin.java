package com.rustam.Movie_Website.dao.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("ADMIN")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseUser {
    private String username;
}
