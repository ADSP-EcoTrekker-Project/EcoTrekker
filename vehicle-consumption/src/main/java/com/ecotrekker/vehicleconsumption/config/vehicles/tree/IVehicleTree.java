package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import java.util.NoSuchElementException;

import com.ecotrekker.vehicleconsumption.parser.AbstractVehicleDatastructureElement;
import com.ecotrekker.vehicleconsumption.parser.AbstractVehicleDatastructure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IVehicleTree extends AbstractVehicleDatastructure<IVehicleTreeElement> {

    private Map<String, IVehicleTreeElement> vehicleTreeMap;

    public Map<String, IVehicleTreeElement> asMap() {
        if (vehicleTreeMap != null) return vehicleTreeMap;

        Map<String, IVehicleTreeElement> results = new HashMap<>();
        
        Stack<IVehicleTreeElement> nodeStack = new Stack<>();

        nodeStack.push((IVehicleTreeElement) this.getRoot());

        while(nodeStack.empty() == false){
            IVehicleTreeElement currentE = nodeStack.pop();

            if (currentE.getName() != "") results.put(currentE.getName(), currentE);
            
            if (currentE.getChildren() != null) {
                for (AbstractVehicleDatastructureElement vehicle : currentE.getChildren()){
                    IVehicleTreeElement castV = (IVehicleTreeElement) vehicle;
                    nodeStack.push(castV);
                }
            }
        }

        vehicleTreeMap = results;
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

    public IVehicleTree() {
        setRoot(new IVehicleTreeElement("", null, null, null));
        vehicleTreeMap = new HashMap<>();
    }

    @Override
    public void addElement(IVehicleTreeElement element) {
        try {
            IVehicleTreeElement parent = getElement(element.getParentName());
            parent.getChildren().add(element);
            element.setParent(parent);
        } catch (NoSuchElementException e) {
            element.setParent(getRoot());
        } finally {
            vehicleTreeMap.put(element.getName(), element);
        }
    }

    @Override
    public void removeElement(IVehicleTreeElement element, boolean removeChildren) {
        try {
            List<IVehicleTreeElement> children = element.getChildren();
            IVehicleTreeElement parent = element.getParent();
            if (removeChildren) {
                parent.getChildren().remove(element);
                element.setParent(null);
            } else {
                parent.getChildren().remove(element);
                parent.getChildren().addAll(children);
                element.setParent(null);
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    @Override
    public void removeElement(IVehicleTreeElement element) {
        removeElement(element, true);
    }

    @Override
    public IVehicleTreeElement getElement(String name) throws NoSuchElementException {
        IVehicleTreeElement result = asMap().get(name);
        if (result == null) { throw new NoSuchElementException("Could not find element"); }
        return result;
    }

    @Override
    public String toString() {
        return asPrettyString();
    }

}
