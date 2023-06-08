package com.adz.demo.boundaries.data.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.adz.demo.model.Item;

public interface PageableItemRepository extends PagingAndSortingRepository<Item, Long> {

	Optional<Item> findByName(String name);
	
}
