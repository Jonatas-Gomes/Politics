package br.com.compass.associate.util;

import br.com.compass.associate.domain.dto.*;
import br.com.compass.associate.domain.enums.Ideology;
import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.enums.Sex;
import br.com.compass.associate.domain.model.Associate;
import br.com.compass.associate.domain.model.Party;

import java.time.LocalDate;
import java.util.List;

public class AssociateMocks {

    private static final LocalDate DATE = LocalDate.of(1999, 10, 27);
    private static final Long ID = 1l;
    private static final String ID_PARTY = "p416203";

    public static AssociateDTO getAssociateDTO(){
        return AssociateDTO
                .builder()
                .fullName("Ednaldo Pereira")
                .sex(Sex.Male)
                .birthday(DATE)
                .politicalOffice(PoliticalOffice.President)
                .build();
    }

    public static AssociateResponse getAssociateResponse(){
        return AssociateResponse.builder()
                .birthday(DATE)
                .id(ID)
                .fullName("Ednaldo Pereira")
                .sex(Sex.Male)
                .politicalOffice(PoliticalOffice.President)
                .build();
    }

    public static Associate getAssociate(){
        return Associate.builder()
                .sex(Sex.Male)
                .id(ID)
                .fullName("Ednaldo Pereira")
                .birthday(DATE)
                .politicalOffice(PoliticalOffice.President)
                .build();

    }

    public static Associate getAssociateWithParty(){
        return Associate.builder()
                .sex(Sex.Male)
                .id(ID)
                .fullName("Ednaldo Pereira")
                .birthday(DATE)
                .politicalOffice(PoliticalOffice.President)
                .party(getParty())
                .build();

    }

    public static PageableResponse getPageableResponse() {
        return PageableResponse.builder()
                .totalPages(1)
                .totalElements(1l)
                .numberOfElements(1)
                .associates(List.of(getAssociate()))
                .build();
    }

    public static Party getParty(){
        return Party.builder()
                .idParty(ID_PARTY)
                .partyName("Partido Verde")
                .acronym("PV")
                .foundationDate(DATE)
                .ideology(Ideology.Center)
                .build();
    }

    public static PartyDTO getPartyDTO(){
        return PartyDTO.builder()
                .idParty(ID_PARTY)
                .partyName("Partido Verde")
                .acronym("PV")
                .build();
    }
    public static PageablePartyResponse getPageablePartyResponse() {
        return PageablePartyResponse.builder()
                .numberOfElements(1)
                .totalElements(1l)
                .numberOfElements(1).parties(List.of(getPartyDTO()))
                .build();
    }
    public static AssociationDTO getAssociationDTO() {
        return AssociationDTO.builder()
                .idAssociate(ID)
                .idParty(ID_PARTY)
                .build();
    }
}
