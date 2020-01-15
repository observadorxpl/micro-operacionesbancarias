package com.operacionbancario.app.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T>{
	public Flux<T> findAll();
	public Mono<T> finById(String id);
	public Mono<T> save(T t);
	public Mono<Void> delete(T t);
	public Mono<Void> deleteById(String id);
	}
