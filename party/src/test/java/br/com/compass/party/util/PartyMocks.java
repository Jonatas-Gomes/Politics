package br.com.compass.party.util;

import br.com.compass.party.domain.dto.*;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.enums.PoliticalOffice;
import br.com.compass.party.domain.enums.Sex;
import br.com.compass.party.domain.model.Associate;
import br.com.compass.party.domain.model.Party;

import java.time.LocalDate;
import java.util.List;

public class PartyMocks {
    private static final String PARTY_NAME = "Partido Verde";
    private static final String ACRONYM = "PV";
    private static final LocalDate FOUNDATION_DATE = LocalDate.of(1988, 04, 27);
    private static final Ideology IDEOLOGY = Ideology.Center;
    private static final String ID_PARTY = "p416203";
    private static final Long ID_ASSOCIATE = 1L;
    private static final Sex SEX = Sex.Male;
    private static final String ASSOCIATE_NAME = "Ednaldo Pereira";
    private static final PoliticalOffice OFFICE = PoliticalOffice.President;
    private static final LocalDate BIRTHDAY = LocalDate.of(1988, 04, 27);;



    public static Party getParty(){
        return Party.builder().idParty(ID_PARTY)
                .partyName(PARTY_NAME)
                .acronym(ACRONYM)
                .foundationDate(FOUNDATION_DATE)
                .ideology(IDEOLOGY)
                .build();
    }
    public static Associate getAssociate(){
        return Associate.builder()
                .id(ID_ASSOCIATE)
                .sex(SEX)
                .birthday(BIRTHDAY)
                .fullName(ASSOCIATE_NAME).
                politicalOffice(OFFICE)
                .build();
    }
    public static PartyDTO getPartyDTO(){
        return PartyDTO.builder()
                .partyName("Partido Verde")
                .acronym("PV")
                .foundationDate(LocalDate.of(1988, 04, 27))
                .ideology(Ideology.Center)
                .build();
    }
    public static PartyResponse getPartyResponse(){
        return PartyResponse.builder()
                .idParty(ID_PARTY)
                .partyName(PARTY_NAME)
                .acronym(ACRONYM)
                .foundationDate(FOUNDATION_DATE).ideology(IDEOLOGY)
                .build();
    }
    public static PageableResponse getPageableResponse(){
        return PageableResponse.builder()
                .numberOfElements(1)
                .totalElements(1l)
                .totalPages(1)
                .parties(List.of(getParty()))
                .build();
    }
    public static AssociateResponse getAssociateResponse(){
        return AssociateResponse.builder()
                .id(ID_ASSOCIATE)
                .sex(SEX)
                .fullName(ASSOCIATE_NAME)
                .birthday(BIRTHDAY)
                .politicalOffice(OFFICE)
                .build();
    }

    public static Party getPartyWithoutID(){
        return Party.builder().idParty(ID_PARTY)
                .partyName(PARTY_NAME)
                .acronym(ACRONYM)
                .foundationDate(FOUNDATION_DATE)
                .ideology(IDEOLOGY)
                .build();
    }

    public static AssociationDTO getAssociationDTO() {
        return AssociationDTO.builder()
                .idAssociate(ID_ASSOCIATE)
                .idParty(ID_PARTY)
                .build();
    }
}
