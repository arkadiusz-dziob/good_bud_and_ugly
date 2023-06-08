package com.adz.demo.boundaries.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adz.demo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findByName(String name);
	
}
