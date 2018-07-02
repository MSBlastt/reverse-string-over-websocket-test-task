package com.task.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ReverseStringResponse data transfer object.
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReverseStringResponse {

    private String lastString;

    private List<String> allStrings;

}
