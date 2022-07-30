package org.barclays.repository;

import org.barclays.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ShopRepository extends JpaRepository<Shop,Long> {
}
