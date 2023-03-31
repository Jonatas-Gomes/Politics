package br.com.compass.associate.framework.adapters.out.partyClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ms-party")
public interface PartyClient {

}
