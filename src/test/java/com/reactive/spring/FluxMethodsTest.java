package com.reactive.spring;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.reactive.spring.domain.Person;
import com.reactive.spring.repository.impl.PersonRepositoryImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class FluxMethodsTest {
	
	PersonRepositoryImpl personRepositoryImpl;

	@BeforeEach
	void setUp() throws Exception {
		personRepositoryImpl = new PersonRepositoryImpl();
	}
	
	/**
	 * The blockFirst() will give the first element of the flux.
	 */
	@Test
	@Disabled
	void testFindAllBlock() {
		System.out.println("Inside FindAllBlock");
		Flux<Person> personFlux = personRepositoryImpl.findAll();
		System.out.println(personFlux.blockFirst());
	}
	
	@Test
	@Disabled
	void testFindAllSubscribe() {
		System.out.println("Inside findAllSubscribe");
		Flux<Person> personFlux = personRepositoryImpl.findAll();
		personFlux.subscribe(person -> {
			System.out.println(person);
		});
	}
	
	/**
	 * The collectList() will return a List<T> inside a Mono class.
	 */
	@Test
	@Disabled
	void testFindAllListMono() {
		System.out.println("Inside findAllSubscribeListMono");
		Flux<Person> personFlux = personRepositoryImpl.findAll();
		Mono<List<Person>> personMonoList = personFlux.collectList();
		personMonoList.subscribe(list -> {
			list.forEach(person -> {
				System.out.println(person);
			});
		});
	}
	
	/**
	 * Here the filter() accepts a conditional statement so that the stream of data when passes through the
	 * filter, it filters out the required data to further processing.
	 * 
	 * The next() will return the next element after filteration and if no elements are there, it returns null.
	 */
	@Test
	@Disabled
	void testFindPersonByNameUsingFluxFilter() {
		System.out.println("Inside findAllFilterFlux");
		Flux<Person> personFlux = personRepositoryImpl.findAll();
		Mono<Person> personMono = personFlux.filter(person -> person.getFirstName() == "Isabella").next();
		personMono.subscribe(person -> {
			System.out.println(" Filtered Person: " + person);
		});
	}
	
	@Test
	@Disabled
	void testFindPersonByIdUsingFilterWithNoElementFound() {
		System.out.println("Inside findAllFilterFlux - No Element");
		Flux<Person> personFlux = personRepositoryImpl.findAll();
		Mono<Person> personMono = personFlux.filter(person -> person.getFirstName() == "sabella").next();
		personMono.subscribe(person -> {
			System.out.println(" Filtered Person: " + person);
		});
	}
	
	/**
	 * The single() expects atleast one element to be returned. Else will throw NoSuchElementFoundException.
	 */
	@Test
	@Disabled
	void testFindPersonByIdUsingFilterWithNoElementFoundReturnsException() {
		System.out.println("Inside findAllFilterFlux - No Element With Exception");
		Flux<Person> personFlux = personRepositoryImpl.findAll();
		Mono<Person> personMono = personFlux.filter(person -> person.getFirstName() == "sabella").single();
		personMono.subscribe(person -> {
			System.out.println(" Filtered Person: " + person);
		});
	}
	
	/**
	 * The doOnError() handles the error that we want to handle but still it will stop the execution of the program
	 * In order to continue the execution of stream data, we need to return something on error. This is handled by
	 * onErrorReturn().
	 */
	@Test
	void testFindPersonByIdUsingFilterWithNoElementFoundReturnsExceptionWithHandler() {
		System.out.println("Inside findAllFilterFlux - No Element With Exception With Error Handler");
		Flux<Person> personFlux = personRepositoryImpl.findAll();
		Mono<Person> personMono = personFlux.filter(person -> person.getFirstName() == "sabella").single();
		personMono.doOnError(throwable -> {
			System.out.println("Error Thrown.");
		}).onErrorReturn(Person.builder().id(12).build()).subscribe(person -> {
			System.out.println(" Filtered Person: " + person);
		});
	}

}
