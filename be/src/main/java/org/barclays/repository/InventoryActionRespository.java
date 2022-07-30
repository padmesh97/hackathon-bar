package org.barclays.repository;

import org.barclays.model.InventoryAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface InventoryActionRespository extends JpaRepository<InventoryAction,Long> {
}
