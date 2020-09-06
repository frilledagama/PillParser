package com.mh2020.android.pillparser;

//class to define Perscription Objects

class Perscription {
    private int qty;
    private String medicationName;
    private String dosage;
    private String instructions;
    //maybe a drawable id for a photo?
    //tbh i think the first letter of the pill
    //would be better
    //mb add expiration date too ZZZZZZZZ


    //empty construstor for firebase
    Perscription(){

    }

    Perscription(int qty, String medicationName, String dosage, String instructions) {
        this.qty = qty;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    Perscription(String medicationName, String dosage, String instructions){
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    //setters
    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    //getters
    public int getQty() {
        return qty;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString(){
        return (this.getMedicationName() + "\n" + this.dosage);
    }
}
