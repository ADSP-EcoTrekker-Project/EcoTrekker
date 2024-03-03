package com.ecotrekker.publictransportdistance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<S,T> {
    private S first;
    private T second;
}
