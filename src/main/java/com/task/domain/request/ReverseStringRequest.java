package com.task.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ReverseStringRequest data transfer object.
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReverseStringRequest {

    @NotNull
    @Size(min = 1)
    private String value;

}
