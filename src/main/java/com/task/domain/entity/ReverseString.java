package com.task.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * ReverseString database entity.
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@Entity
@Data
public class ReverseString {

    @Id
    @GeneratedValue
    private Long id;
    private String value;
}
