package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IVehicleTreeElementTest {

    private IVehicleTreeElement parentElement;
    private IVehicleTreeElement childElement1;
    private IVehicleTreeElement childElement2;

    @BeforeEach
    void setUp() {
        parentElement = new IVehicleTreeElement("Parent", 50.0, 10.0, null);
        childElement1 = new IVehicleTreeElement("Child1", 40.0, 8.0, "Parent");
        childElement2 = new IVehicleTreeElement("Child2", 60.0, 12.0, "Parent");

        parentElement.getChildren().add(childElement1);
        parentElement.getChildren().add(childElement2);
        childElement1.setParent(parentElement);
        childElement2.setParent(parentElement);
    }

    @Test
    void testNumParents() {
        assertEquals(0, parentElement.numParents(), "Number of parents for root should be 0");

        assertEquals(1, childElement1.numParents(), "Number of parents for childElement1 should be 1");
        assertEquals(1, childElement2.numParents(), "Number of parents for childElement2 should be 1");
    }

    @Test
    void testParentChildRelationship() {
        assertEquals(parentElement, childElement1.getParent(), "Parent of childElement1 should be parentElement");
        assertEquals(parentElement, childElement2.getParent(), "Parent of childElement2 should be parentElement");

        assertTrue(parentElement.getChildren().contains(childElement1), "parentElement should contain childElement1");
        assertTrue(parentElement.getChildren().contains(childElement2), "parentElement should contain childElement2");
    }

    @Test
    void testConstructor() {
        assertEquals("Parent", parentElement.getName(), "Name should be set correctly in constructor");
        assertNull(parentElement.getParent(), "Parent should be null for root element");
    }
}
