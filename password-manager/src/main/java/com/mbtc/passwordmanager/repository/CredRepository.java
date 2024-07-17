package com.mbtc.passwordmanager.repository;

import com.mbtc.passwordmanager.model.UrlCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredRepository extends CrudRepository<UrlCredential, Integer> {
    Optional<UrlCredential> findById(Integer id);

}
