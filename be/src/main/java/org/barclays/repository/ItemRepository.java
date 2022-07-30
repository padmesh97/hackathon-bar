package org.barclays.repository;


import org.barclays.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByNameContaining(String query);
}
