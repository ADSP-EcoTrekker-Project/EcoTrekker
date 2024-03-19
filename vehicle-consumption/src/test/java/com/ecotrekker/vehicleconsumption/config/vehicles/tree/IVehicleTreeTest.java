package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class IVehicleTreeTest {

    private IVehicleTree vehicleTree;

    @BeforeEach
    void setUp() {
        vehicleTree = new IVehicleTree();
    }

    @Test
    void testAddElementAndGetElement() {
        IVehicleTreeElement element1 = new IVehicleTreeElement("Element1", 50.0, 10.0, "");
        IVehicleTreeElement element2 = new IVehicleTreeElement("Element2", 60.0, 12.0, "Element1");

        vehicleTree.addElement(element1);
        vehicleTree.addElement(element2);

        assertEquals(element1, vehicleTree.getElement("Element1"), "Added element1 should be retrieved correctly");
        assertEquals(element2, vehicleTree.getElement("Element2"), "Added element2 should be retrieved correctly");
    }

    @Test
    void testGetElementNotFound() {
        assertThrows(NoSuchElementException.class, () -> vehicleTree.getElement("NonExistentElement"), "Getting non-existent element should throw NoSuchElementException");
    }

}
