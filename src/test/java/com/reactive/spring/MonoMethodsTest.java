package com.reactive.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.reactive.spring.domain.Person;
import com.reactive.spring.repository.impl.PersonRepositoryImpl;

import reactor.core.publisher.Mono;

class MonoMethodsTest {
	
	PersonRepositoryImpl personRepositoryImpl;

	@BeforeEach
	void setUp() throws Exception {
		personRepositoryImpl = new PersonRepositoryImpl();
	}

	@Test
	void testGetByIdBlock() {
		Mono<Person> monoPerson = personRepositoryImpl.getById(1);
		Person person = monoPerson.block();
		System.out.println(person);
	}
	
	@Test
	void testGetByIdSubscribe() {
		Mono<Person> monoPerson = personRepositoryImpl.getById(1);
		monoPerson.subscribe(person -> {
			System.out.println(person);
		});
	}
	
	@Test
	void testGetByIdMapAndSubscribe() {
		Mono<Person> monoPerson = personRepositoryImpl.getById(1);
		monoPerson.map(person -> {
			System.out.println("Person: " + person);
			return person.getFirstName();
		}).subscribe(firstName -> {
			System.out.println("First Name: " + firstName);
		});
	}

}
