package org.barclays.repository;

import org.barclays.model.Cart;
import org.barclays.model.Item;
import org.barclays.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findByUser(User user);

    Cart findByUserAndItem(User user, Item item);
}
