package com.sio.models;

import java.util.ArrayList;

public class Target {
    private String hash;
    private String codeName;
    private String name;
    private ArrayList<Position> positions;

    public Target() {
        this.positions = new ArrayList<>();
    }

    public Target(String hash, String codeName, String name) {
        this.hash = hash;
        this.codeName = codeName;
        this.name = name;
        this.positions = new ArrayList<>();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public Position getLastPosition() {
        return this.positions.getLast();
    }

}
