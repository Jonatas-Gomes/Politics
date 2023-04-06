package br.com.compass.party.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Ideology {
    Right("right"),
    Center("center"),
    Left("left");

    private final String value;

    Ideology(String value){
        this.value = value;
    }
    @JsonCreator
    public static Ideology fromValue(String value){
        for(Ideology ideology : Ideology.values()){
            if(ideology.value.equalsIgnoreCase(value)){
                return ideology;
            }
        }
        
        return null;
    }
}
