package org.barclays.controller;

import org.barclays.errors.ErrorCodes;
import org.barclays.errors.ErrorHandling;
import org.barclays.model.Cart;
import org.barclays.model.Item;
import org.barclays.model.User;
import org.barclays.repository.CartRepository;
import org.barclays.repository.ItemRepository;
import org.barclays.repository.UserRepository;
import org.barclays.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public LinkedHashMap getCart(HttpServletRequest httpReq, HttpServletResponse httpResp) {
        LinkedHashMap resp = new LinkedHashMap();

        if (!Utils.checkAuthenticated(httpReq)) return Utils.generateInvalidLoginError(httpResp);

        User loggedInUser = Utils.getLoggedInUser(httpReq,userRepository);
        List<Cart> userCartItems = cartRepository.findByUser(loggedInUser);

        if(userCartItems != null && userCartItems.size()>0) {
            ArrayList<LinkedHashMap> cartList = new ArrayList();

            for (Cart userCartItem : userCartItems) {
                LinkedHashMap userCartResult = new LinkedHashMap();
                userCartResult.put("item_id",userCartItem.getItem().getId());
                userCartResult.put("item_name",userCartItem.getItem().getName());
                userCartResult.put("quantity",userCartItem.getQuantity());

                cartList.add(userCartResult);
            }
            resp.put("status","success");
            resp.put("cart",cartList);
        }
        else{
            resp.put("error", ErrorHandling.getError(ErrorCodes.CART_EMPTY));
            return resp;
        }

        return resp;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public LinkedHashMap addCart(@RequestBody LinkedHashMap reqBody, HttpServletRequest httpReq, HttpServletResponse httpResp) {
        LinkedHashMap resp = new LinkedHashMap();

        if(!Utils.checkAuthenticated(httpReq)) return Utils.generateInvalidLoginError(httpResp);

        if(!reqBody.containsKey("itemId")){
            resp.put("error", ErrorHandling.getError(ErrorCodes.CART_MISSING_DETAILS));
            httpResp.setStatus(500);
            return resp;
        }

        Long itemId = Long.parseLong(reqBody.get("itemId").toString());
        Optional<Item> item = itemRepository.findById(itemId);

        if(item.equals(Optional.empty())){
            resp.put("error", ErrorHandling.getError(ErrorCodes.CART_INVALID_PRODUCT));
            return resp;
        }
        else{
            Cart cart = null;
            User loggedInUser = Utils.getLoggedInUser(httpReq,userRepository);
            cart = cartRepository.findByUserAndItem(loggedInUser,item.get());

            if(cart == null)
                cart = new Cart();

            cart.setItem(item.get());
            cart.setUser(loggedInUser);
            cart.setQuantity(cart.getQuantity()+1);


            try {
                cartRepository.save(cart);
                resp.put("status", "success");
            }
            catch (Exception e){
                resp.put("error",ErrorHandling.getError(ErrorCodes.UNKNOWN_ERROR));
            }
        }

        return resp;

    }

    @RequestMapping(method = RequestMethod.POST,value = "/remove")
    public LinkedHashMap removeCart(@RequestBody LinkedHashMap reqBody, HttpServletRequest httpReq, HttpServletResponse httpResp) {
        LinkedHashMap resp = new LinkedHashMap();

        if (!Utils.checkAuthenticated(httpReq)) return Utils.generateInvalidLoginError(httpResp);

        if (!reqBody.containsKey("itemId")) {
            resp.put("error", ErrorHandling.getError(ErrorCodes.CART_MISSING_DETAILS));
            httpResp.setStatus(500);
            return resp;
        }

        Long itemId = Long.parseLong(reqBody.get("itemId").toString());
        Optional<Item> item = itemRepository.findById(itemId);

        if (item.equals(Optional.empty())) {
            resp.put("error", ErrorHandling.getError(ErrorCodes.CART_INVALID_PRODUCT));
            return resp;
        } else {
            Cart cart = cartRepository.findByUserAndItem(Utils.getLoggedInUser(httpReq,userRepository),item.get());

            if(cart == null){
                resp.put("error", ErrorHandling.getError(ErrorCodes.CART_EMPTY));
                return resp;
            }
            else{
                int quantity = cart.getQuantity();
                if(quantity==1)
                    cartRepository.delete(cart);
                else {
                    cart.setQuantity(cart.getQuantity()-1);
                    cartRepository.save(cart);
                }
                resp.put("status","success");
            }
        }

        return resp;
    }

}
