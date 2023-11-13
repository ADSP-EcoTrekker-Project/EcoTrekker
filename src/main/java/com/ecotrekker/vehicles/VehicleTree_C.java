package com.ecotrekker.vehicles;

import java.util.LinkedList;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecotrekker.config.vehicles.VehicleConfigLoader_I;

public class VehicleTree_C extends VehicleDatastructure_A {

    private static Logger logger = LoggerFactory.getLogger(VehicleTree_C.class);

    public LinkedList<VehicleTreeElement_C> getTreeAsList(){
        LinkedList<VehicleTreeElement_C> results = new LinkedList<>();
        Stack<VehicleTreeElement_C> nodeStack = new Stack<>();

        nodeStack.push(this.getRoot());

        while(nodeStack.empty() == false){
            VehicleTreeElement_C currentE = nodeStack.pop();
            results.add(currentE);

            for (VehicleDatastructureElement_A vehicle : currentE.children){
                VehicleTreeElement_C castV = (VehicleTreeElement_C) vehicle;
                nodeStack.push(castV);
            }
        }

        return results;
    }
    
    public VehicleTree_C(VehicleConfigLoader_I configLoader) {
        super(configLoader);
        this.setRoot(new VehicleTreeElement_C(null, null, null, null));

        LinkedList<VehicleDatastructureElement_A> vehicles = configLoader.getVehicle_elements();

        for (VehicleDatastructureElement_A vehicle : vehicles){
            
            if (vehicle.getParent_string() == null || vehicle.getParent_string() == ""){
                vehicle.parent = this.getRoot();
                this.getRoot().children.add(vehicle);
                continue;
            }
            VehicleDatastructureElement_A parent = VehicleDatastructureElement_A.findByString(vehicles, vehicle.getParent_string());
            if (parent != null){
                vehicle.parent = parent;
                parent.children.add(vehicle);
                continue;
            }
            logger.warn("Failed to insert Element into the Tree");
        }
    }

}