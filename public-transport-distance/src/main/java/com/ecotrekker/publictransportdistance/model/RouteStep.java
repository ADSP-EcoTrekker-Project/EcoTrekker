package com.ecotrekker.publictransportdistance.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Objects;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteStep {
    private String start;
    private String end;
    private String vehicle;
    private String line;
    private Double distance = null;

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof RouteStep) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, line);
    }
}
