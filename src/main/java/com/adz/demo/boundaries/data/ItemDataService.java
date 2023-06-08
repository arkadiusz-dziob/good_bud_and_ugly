package com.adz.demo.boundaries.data;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.adz.demo.boundaries.data.repositories.ItemRepository;
import com.adz.demo.model.Item;

@Component
public class ItemDataService {
	
	private final ItemRepository itemRepository;

	public ItemDataService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public Optional<Item> create(Item item) {
		if (item.getItemId() != null && !itemRepository.existsById(item.getItemId())) {
			return Optional.ofNullable(itemRepository.save(item));
		}
		return Optional.empty();
	}

	public Optional<Item> getById(Long itemId) {
		return itemRepository.findById(itemId);
	}

	@Transactional
	public Optional<Item> update(Item item) {
		if (itemRepository.existsById(item.getItemId())) {
			return Optional.ofNullable(itemRepository.save(item));
		} else {
			return Optional.empty();
		}
	}

	public void deleteById(Long itemId) {
		itemRepository.deleteById(itemId); 
	}

	public Optional<Item> getByName(String name) {
		return itemRepository.findByName(name); 
	}

	public List<Item> create(List<Item> items) {
		return itemRepository.saveAll(items);
	}

}
