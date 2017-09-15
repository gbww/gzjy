package com.gzjy.contract.model;

public enum ContractStatus{

    READY(0),
    APPROVING(1),
    UPDATING(2),
    OVER(3);

    private int num;

    ContractStatus(int num){
        this.num = num;
    }

    public int getValue(){
        return num;
    }

}