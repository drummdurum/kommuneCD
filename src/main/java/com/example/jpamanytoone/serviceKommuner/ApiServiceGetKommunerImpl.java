package com.example.jpamanytoone.serviceKommuner;

import com.example.jpamanytoone.model.Kommune;
import com.example.jpamanytoone.model.Region;
import com.example.jpamanytoone.repositoryKommuner.RepositoryKommuner;
import com.example.jpamanytoone.repositoryRegion.RepositoryRegion;
import com.example.jpamanytoone.serviceRegion.ApiServiceGetRegioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceGetKommunerImpl implements ApiServiceGetKommuner {

    @Autowired
    RepositoryKommuner repositoryKommuner;

    private RestTemplate restTemplatere;
    String kommunesUrl = "https://api.dataforsyningen.dk/kommuner";
    private void saveKommuner(List<Kommune> kommunes) { kommunes.forEach(reg -> repositoryKommuner.save(reg));}

    public ApiServiceGetKommunerImpl  (RestTemplate restTemplatere) {this.restTemplatere = restTemplatere;}

    @Override
    public List<Kommune> getKomuner() {
        List<Kommune> lst = new ArrayList<>();
        ResponseEntity<List<Kommune>> kommuneResponse =
                restTemplatere.exchange(kommunesUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Kommune>>() {
                        });
        List<Kommune> kommune = kommuneResponse.getBody();
        saveKommuner(kommune);
        return kommune;
    }
}
