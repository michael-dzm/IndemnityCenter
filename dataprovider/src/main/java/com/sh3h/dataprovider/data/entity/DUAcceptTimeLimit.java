package com.sh3h.dataprovider.data.entity;

/**
 * Created by dengzhimin on 2017/7/28.
 */

public class DUAcceptTimeLimit {

    private long strengthStepTwo;

    private long strengthStepTwoToThree;

    private long strengthStepThree;

    private long airtightStepTwo;

    private long airtightStepTwoToThree;

    private long airtightStepThree;

    public DUAcceptTimeLimit(){}

    public DUAcceptTimeLimit(long strengthStepTwo, long strengthStepTwoToThree, long strengthStepThree, long airtightStepTwo,
                             long airtightStepTwoToThree, long airtightStepThree) {
        this.strengthStepTwo = strengthStepTwo;
        this.strengthStepTwoToThree = strengthStepTwoToThree;
        this.strengthStepThree = strengthStepThree;
        this.airtightStepTwo = airtightStepTwo;
        this.airtightStepTwoToThree = airtightStepTwoToThree;
        this.airtightStepThree = airtightStepThree;
    }

    public long getStrengthStepTwo() {
        return strengthStepTwo;
    }

    public void setStrengthStepTwo(long strengthStepTwo) {
        this.strengthStepTwo = strengthStepTwo;
    }

    public long getStrengthStepTwoToThree() {
        return strengthStepTwoToThree;
    }

    public void setStrengthStepTwoToThree(long strengthStepTwoToThree) {
        this.strengthStepTwoToThree = strengthStepTwoToThree;
    }

    public long getStrengthStepThree() {
        return strengthStepThree;
    }

    public void setStrengthStepThree(long strengthStepThree) {
        this.strengthStepThree = strengthStepThree;
    }

    public long getAirtightStepTwo() {
        return airtightStepTwo;
    }

    public void setAirtightStepTwo(long airtightStepTwo) {
        this.airtightStepTwo = airtightStepTwo;
    }

    public long getAirtightStepTwoToThree() {
        return airtightStepTwoToThree;
    }

    public void setAirtightStepTwoToThree(long airtightStepTwoToThree) {
        this.airtightStepTwoToThree = airtightStepTwoToThree;
    }

    public long getAirtightStepThree() {
        return airtightStepThree;
    }

    public void setAirtightStepThree(long airtightStepThree) {
        this.airtightStepThree = airtightStepThree;
    }
}
