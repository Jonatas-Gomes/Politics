package br.com.compass.associate.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sex {
    Male("male"),
    Female("female");

    private final String value;
    Sex(String value){
        this.value = value;
    }
    @JsonCreator
    public static Sex fromValue(String value){

        for (Sex sex : Sex.values() ){
            if(sex.value.equalsIgnoreCase(value)){
                return sex;
            }
        }

        return null;
    }
}
