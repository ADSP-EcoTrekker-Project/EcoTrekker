package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import java.util.LinkedList;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecotrekker.vehicleconsumption.parser.VehicleConfigLoader_I;
import com.ecotrekker.vehicleconsumption.parser.VehicleDatastructureElement_A;
import com.ecotrekker.vehicleconsumption.parser.VehicleDatastructure_A;


public class VehicleTree_C extends VehicleDatastructure_A<VehicleTreeElement_C> {

    private static Logger logger = LoggerFactory.getLogger(VehicleTree_C.class);

    public LinkedList<VehicleTreeElement_C> asList(){
        LinkedList<VehicleTreeElement_C> results = new LinkedList<>();
        Stack<VehicleTreeElement_C> nodeStack = new Stack<>();

        nodeStack.push((VehicleTreeElement_C) this.getRoot());

        while(nodeStack.empty() == false){
            VehicleTreeElement_C currentE = nodeStack.pop();

            if (currentE.getName() != "") results.add(currentE);
            
            for (VehicleDatastructureElement_A vehicle : currentE.getChildren()){
                VehicleTreeElement_C castV = (VehicleTreeElement_C) vehicle;
                nodeStack.push(castV);
            }
        }

        return results;
    }

    private String asPrettyString(){
        String result = "";
        Stack<VehicleTreeElement_C> nodeStack = new Stack<>();

        nodeStack.push((VehicleTreeElement_C) this.getRoot());

        while(nodeStack.empty() == false){
            VehicleTreeElement_C currentE = nodeStack.pop();

            if (currentE.getName() != ""){
                int parents = currentE.numParents() - 1;
                result += "|";
                for (int i = 0; i < parents; i++) { result += "\t"; }
                result += currentE.getName();
                result += "\n";
            }
            
            for (VehicleDatastructureElement_A vehicle : currentE.getChildren()){
                VehicleTreeElement_C castV = (VehicleTreeElement_C) vehicle;
                nodeStack.push(castV);
            }
        }

        return result;
    }

    public <T extends VehicleConfigLoader_I> VehicleTree_C(T configLoader) {
        super(configLoader);
        this.setRoot(new VehicleTreeElement_C("", null, null, null));

        LinkedList<VehicleTreeElement_C> vehicles = configLoader.getVehicle_elements();

        for (VehicleTreeElement_C vehicle : vehicles){
            
            if (vehicle.getParent_string() == null || vehicle.getParent_string() == ""){
                vehicle.setParent(this.getRoot());
                this.getRoot().getChildren().add(vehicle);
                continue;
            }
            VehicleTreeElement_C parent = VehicleTreeElement_C.findByString(vehicles, vehicle.getParent_string());
            if (parent != null){
                vehicle.setParent(parent);
                parent.getChildren().add(vehicle);
                continue;
            }
            logger.warn("Failed to insert Element into the Tree");
        }
    }

    @Override
    public VehicleTreeElement_C getElementByName(String name) {
        return VehicleDatastructureElement_A.findByString(this.asList(), name);
    }

    @Override
    public String toString() {
        return asPrettyString();
    }

}
