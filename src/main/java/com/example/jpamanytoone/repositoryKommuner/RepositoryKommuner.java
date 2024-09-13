package com.example.jpamanytoone.repositoryKommuner;

import com.example.jpamanytoone.model.Kommune;
import com.example.jpamanytoone.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryKommuner extends JpaRepository<Kommune, String> {



}
