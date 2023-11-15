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

        nodeStack.push((VehicleTreeElement_C) this.getRoot());

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

    public <T extends VehicleConfigLoader_I> VehicleTree_C(T configLoader) {
        super(configLoader);
        this.setRoot(new VehicleTreeElement_C("", null, null, null));

        LinkedList<VehicleTreeElement_C> vehicles = configLoader.getVehicle_elements();

        for (VehicleTreeElement_C vehicle : vehicles){
            
            if (vehicle.getParent_string() == null || vehicle.getParent_string() == ""){
                vehicle.parent = this.getRoot();
                this.getRoot().children.add(vehicle);
                continue;
            }
            VehicleTreeElement_C parent = VehicleTreeElement_C.findByString(vehicles, vehicle.getParent_string());
            if (parent != null){
                vehicle.parent = parent;
                parent.children.add(vehicle);
                continue;
            }
            logger.warn("Failed to insert Element into the Tree");
        }
    }



    @Override
    public VehicleDatastructureElement_A getElementByName(String name) {
        return VehicleDatastructureElement_A.findByString(this.getTreeAsList(), name);
    }

}