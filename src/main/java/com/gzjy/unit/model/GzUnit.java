package com.gzjy.unit.model;

public class GzUnit {
    private String unitName;

    private String unitDesc;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc == null ? null : unitDesc.trim();
    }
}