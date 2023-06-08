package com.adz.demo.boundaries.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adz.demo.boundaries.data.ItemDataService;
import com.adz.demo.model.Item;

@RestController
@RequestMapping("api/item")
public class ItemController {

	private final ItemDataService itemDataService;

	public ItemController(ItemDataService itemDataService) {
		this.itemDataService = itemDataService;
	}

	@PostMapping("create")
	public ResponseEntity<Item> create(@RequestBody Item item) {
		Optional<Item> createdItem = itemDataService.create(item);
		if (createdItem.isPresent()) {
			return ResponseEntity.of(createdItem);
		} else {
			return ResponseEntity.badRequest().body(item);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<Item> getById(@PathVariable("id") Long itemId) {
		Optional<Item> item = itemDataService.getById(itemId);
		return ResponseEntity.of(item);
	}

	@GetMapping("name/{name}")
	public ResponseEntity<Item> getItemByName(@PathVariable("name") String name) {
		Optional<Item> item = itemDataService.getByName(name);
		return ResponseEntity.of(item);
	}

	@PutMapping("{id}")
	public ResponseEntity<Item> updateItem(@RequestBody Item item) {
		Optional<Item> updatedItem = itemDataService.update(item);
		return ResponseEntity.of(updatedItem);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable("id") Long itemId) {
		itemDataService.deleteById(itemId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("many")
	public ResponseEntity<List<Item>> insertCustomers(@RequestBody List<Item> items) {
		List<Item> created = itemDataService.create(items);
		return ResponseEntity.ok(created);
	}

	@GetMapping(path = "/items/page")
	Page<Item> loadCharactersPage(Pageable pageable) {
		return itemDataService.findAllPage(pageable);
	}
}
