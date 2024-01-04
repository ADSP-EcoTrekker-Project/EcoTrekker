package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import java.util.LinkedList;
import java.util.Stack;

import com.ecotrekker.vehicleconsumption.parser.VehicleConfigLoader;
import com.ecotrekker.vehicleconsumption.parser.AbstractVehicleDatastructureElement;
import com.ecotrekker.vehicleconsumption.parser.AbstractVehicleDatastructure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IVehicleTree <T extends VehicleConfigLoader<IVehicleTreeElement>> extends AbstractVehicleDatastructure<IVehicleTreeElement> {

    public LinkedList<IVehicleTreeElement> asList(){
        LinkedList<IVehicleTreeElement> results = new LinkedList<>();
        Stack<IVehicleTreeElement> nodeStack = new Stack<>();

        nodeStack.push((IVehicleTreeElement) this.getRoot());

        while(nodeStack.empty() == false){
            IVehicleTreeElement currentE = nodeStack.pop();

            if (currentE.getName() != "") results.add(currentE);
            
            if (currentE.getChildren() != null) {
                for (AbstractVehicleDatastructureElement vehicle : currentE.getChildren()){
                    IVehicleTreeElement castV = (IVehicleTreeElement) vehicle;
                    nodeStack.push(castV);
                }
            }
        }

        return results;
    }

    private String asPrettyString(){
        String result = "";
        Stack<IVehicleTreeElement> nodeStack = new Stack<>();

        nodeStack.push((IVehicleTreeElement) this.getRoot());

        while(nodeStack.empty() == false){
            IVehicleTreeElement currentE = nodeStack.pop();

            if (currentE.getName() != ""){
                int parents = currentE.numParents() - 1;
                result += "|";
                for (int i = 0; i < parents; i++) { result += "\t"; }
                result += currentE.getName();
                result += "\n";
            }
            
            for (AbstractVehicleDatastructureElement vehicle : currentE.getChildren()){
                IVehicleTreeElement castV = (IVehicleTreeElement) vehicle;
                nodeStack.push(castV);
            }
        }

        return result;
    }

    public IVehicleTree(T configLoader) {
        super(configLoader);
        this.setRoot(new IVehicleTreeElement("", null, null, null));

        LinkedList<IVehicleTreeElement> vehicles = configLoader.getVehicles();

        for (IVehicleTreeElement vehicle : vehicles){
            
            if (vehicle.getParentName() == null || vehicle.getParentName() == ""){
                vehicle.setParent(this.getRoot());
                this.getRoot().getChildren().add(vehicle);
                continue;
            }
            IVehicleTreeElement parent = IVehicleTreeElement.findByString(vehicles, vehicle.getParentName());
            if (parent != null){
                vehicle.setParent(parent);
                parent.getChildren().add(vehicle);
                continue;
            }
            log.warn("Failed to insert Element into the Tree");
        }
    }

    @Override
    public IVehicleTreeElement getElementByName(String name) {
        return AbstractVehicleDatastructureElement.findByString(this.asList(), name);
    }

    @Override
    public String toString() {
        return asPrettyString();
    }

}
