package com.operationbanking.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.operationbanking.app.models.Atm;
@Repository
public interface IAtmRepository extends ReactiveMongoRepository<Atm, String>{

}
