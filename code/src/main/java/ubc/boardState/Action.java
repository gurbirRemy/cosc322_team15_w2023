package ubc.boardState;

import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

import java.util.*;

public class Action {
    private int prevX;
    private int prevY;
    private int newX;
    private int newY;
    private int arrowX;
    private int arrowY;


    public Action(Map<String, Object> actionMap) {
        ArrayList<Integer> prevPos = (ArrayList<Integer>) ((ArrayList<Integer>) actionMap.get(AmazonsGameMessage.QUEEN_POS_CURR)).clone();
        ArrayList<Integer> newPos = (ArrayList<Integer>) ((ArrayList<Integer>) actionMap.get(AmazonsGameMessage.QUEEN_POS_NEXT)).clone();
        ArrayList<Integer> arrowPos = (ArrayList<Integer>) ((ArrayList<Integer>) actionMap.get(AmazonsGameMessage.ARROW_POS)).clone();

        prevX = prevPos.get(0) - 1;
        prevY = prevPos.get(1) - 1;
        newX = newPos.get(0) - 1;
        newY = newPos.get(1) - 1;
        arrowX = arrowPos.get(0) - 1;
        arrowY = arrowPos.get(1) - 1;
    }
    public Action(Action action) {
        this.prevX = action.prevX;
        this.prevY = action.prevY;
        this.newX = action.newX;
        this.newY = action.newY;
        this.arrowX = action.arrowX;
        this.arrowY = action.arrowY;
    }

    public Action(int prevX, int prevY, int newX, int newY, int arrowX, int arrowY) {
        this.prevX = prevX;
        this.prevY = prevY;
        this.newX = newX;
        this.newY = newY;
        this.arrowX = arrowX;
        this.arrowY = arrowY;
    }
    

    public Action(ArrayList<Integer> prevPos, ArrayList<Integer> newPos, ArrayList<Integer> arrowPos) {
        this.prevX = prevPos.get(0) - 1;
        this.prevY = prevPos.get(1) - 1;
        this.newX = newPos.get(0) - 1;
        this.newY = newPos.get(1) - 1;
        this.arrowX = arrowPos.get(0) - 1;
        this.arrowY = arrowPos.get(1) - 1;
    }


	public String toString() {
        String[] xaxis = {"0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        return xaxis[prevX] + (prevY) + "->" + xaxis[newX] + (newY) + "(Arrow: " + xaxis[arrowX] + (arrowY) + ")";
    }
    
    public Map<String, Object> makeMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(AmazonsGameMessage.QUEEN_POS_CURR, new ArrayList<>(Arrays.asList(prevY, prevX)));
        map.put(AmazonsGameMessage.QUEEN_POS_NEXT, new ArrayList<>(Arrays.asList(newY, newX)));
        map.put(AmazonsGameMessage.ARROW_POS, new ArrayList<>(Arrays.asList(arrowY, arrowX)));
        return map;
    }
    
    //setters and getters

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public int getArrowX() {
        return arrowX;
    }

    public void setArrowX(int arrowX) {
        this.arrowX = arrowX;
    }

    public int getArrowY() {
        return arrowY;
    }

    public void setArrowY(int arrowY) {
        this.arrowY = arrowY;
    }



}
