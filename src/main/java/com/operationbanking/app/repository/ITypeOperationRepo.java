package com.operationbanking.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.operationbanking.app.models.TypeOperation;

public interface ITypeOperationRepo extends ReactiveMongoRepository<TypeOperation, String>{

}
