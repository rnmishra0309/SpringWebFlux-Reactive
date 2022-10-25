package com.reactive.spring.repository.impl;

import com.reactive.spring.domain.Person;
import com.reactive.spring.repository.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {
	
	private Person mica = new Person(1, "Mica", "Bell", "m.bell@gmail.com");
	private Person arthur = new Person(2, "Arthur", "Red", "arthur@red.com");
	private Person john = new Person(3, "John", "Doe", "john.doe@email.com");
	private Person isabella = new Person(4, "Isabella", "Samuel", "i.samuel@gmail.com");

	public Mono<Person> getById(Integer id) {
		return Mono.just(arthur);
	}

	public Flux<Person> findAll() {
		return Flux.just(mica, arthur, john, isabella);
	}

}
