package com.example.jpamanytoone.serviceRegion;

import com.example.jpamanytoone.model.Region;
import com.example.jpamanytoone.repositoryRegion.RepositoryRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceGetRegionerImpl implements ApiServiceGetRegioner {

    @Autowired
    RepositoryRegion repositoryRegion;
    private RestTemplate restTemplatere;
    String regionUrl = "https://api.dataforsyningen.dk/regioner";
    private void saveRegioner(List<Region> regioner) { regioner.forEach(reg -> repositoryRegion.save(reg));}

    public ApiServiceGetRegionerImpl (RestTemplate restTemplatere) {this.restTemplatere = restTemplatere;}

    @Override
    public List<Region> getRegion() {
        List<Region> lst = new ArrayList<>();
        ResponseEntity<List<Region>> regionResponse =
                restTemplatere.exchange(regionUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Region>>() {
                        });
        List<Region> regioner = regionResponse.getBody();
        saveRegioner(regioner);
        return regioner;
    }


}

