package br.com.compass.associate.domain.enums;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum PoliticalOffice {
    Alderman("alderman"),
    Mayor("mayor"),
    State_Deputy("state deputy"),
    Federal_Deputy("federal deputy"),
    Senator("senator"),
    Governor("governor"),
    President("president"),
    None("none");

    @Override
    public String toString() {
        return "Alderman | Mayor | State_Deputy | Federal_Deputy | Senator | Governor | President | None";
    }

    private final String value;

    PoliticalOffice(String value){
        this.value = value;
    }
    @JsonCreator
    public static PoliticalOffice fromValue(String value){

        for (PoliticalOffice politicalOffice : PoliticalOffice.values()){
            if(politicalOffice.value.equalsIgnoreCase(value)){
                return politicalOffice;
            }
        }

        return null;
    }
}
