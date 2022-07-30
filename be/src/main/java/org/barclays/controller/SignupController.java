package org.barclays.controller;

import org.barclays.errors.ErrorCodes;
import org.barclays.model.Item;
import org.barclays.model.Shop;
import org.barclays.repository.ItemRepository;
import org.barclays.repository.ShopRepository;
import org.barclays.repository.UserRepository;
import org.barclays.utils.PasswordManager;
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

import org.barclays.errors.ErrorHandling;
import org.barclays.model.User;

@RestController
@RequestMapping(value = "/signup")
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Object signupAction(@RequestBody LinkedHashMap reqBody, HttpServletRequest httpReq, HttpServletResponse httpResp) {
        LinkedHashMap resp = new LinkedHashMap();

        if (reqBody.containsKey("type")) {
            if (!reqBody.get("type").equals("store") && !reqBody.get("type").equals("customer")) {
                resp.put("error", ErrorHandling.getError(ErrorCodes.SIGNUP_TYPE_ERROR));
                httpResp.setStatus(500);
                return resp;
            }
            if (!reqBody.containsKey("name") || !reqBody.containsKey("email") || !reqBody.containsKey("password")) {
                resp.put("error", ErrorHandling.getError(ErrorCodes.SIGNUP_MISSING_DETAILS));
                httpResp.setStatus(500);
                return resp;
            }
        } else {
            resp.put("error", ErrorHandling.getError(ErrorCodes.SIGNUP_MISSING_DETAILS));
            httpResp.setStatus(500);
            return resp;
        }

        User user = new User();

        user.setType(reqBody.get("type").toString());
        user.setName(reqBody.get("name").toString());
        user.setEmail(reqBody.get("email").toString());
        user.setPassword(PasswordManager.hashPassword(reqBody.get("password").toString()));
        user.setLatitude(reqBody.get("latitude") != null?reqBody.get("latitude").toString():null);
        user.setLongitude(reqBody.get("longitude") != null?reqBody.get("longitude").toString():null);

        try{
            userRepository.save(user);
            resp.put("status", "success");
        }
        catch(Exception e){
            resp.put("error", ErrorHandling.getError(ErrorCodes.UNKNOWN_ERROR));
            httpResp.setStatus(500);
        }

        return resp;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/populate/shops")
    public Object populateShops(@RequestBody LinkedHashMap reqBody, HttpServletRequest httpReq, HttpServletResponse httpResp) {
        LinkedHashMap resp = new LinkedHashMap();
        ArrayList<LinkedHashMap> shopSuccess = new ArrayList();

        if (reqBody.containsKey("Bangalore Outlet Details")) {
            List<LinkedHashMap> shops = (ArrayList<LinkedHashMap>) reqBody.get("Bangalore Outlet Details");
            for (LinkedHashMap shp : shops) {
                Shop shop = new Shop();
                shop.setName(shp.get("Store_Name").toString());
                shop.setArea(shp.get("Area").toString());
                shop.setPincode(shp.get("Pincode").toString());
                shop.setLatitude(Double.parseDouble(shp.get("Latitude").toString()));
                shop.setLongitude(Double.parseDouble(shp.get("Longitude").toString()));

                LinkedHashMap shopStatus = new LinkedHashMap();
                shopStatus.put("name", shp.get("Store_Name").toString());
                try {
                    shopRepository.save(shop);
                    shopStatus.put("status", "success");
                } catch (Exception e) {
                    shopStatus.put("status", "failed");
                }

                shopSuccess.add(shopStatus);
            }
        }

        resp.put("result", shopSuccess);

        return resp;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/populate/items")
    public Object populateItems(@RequestBody LinkedHashMap reqBody, HttpServletRequest httpReq, HttpServletResponse httpResp) {

        LinkedHashMap resp = new LinkedHashMap();
        ArrayList<LinkedHashMap> itemSuccess = new ArrayList();

        if (reqBody.containsKey("Data")) {
            List<LinkedHashMap> items = (ArrayList<LinkedHashMap>) reqBody.get("Data");
            for (LinkedHashMap itm : items) {
                Item item = new Item();
                item.setName(itm.get("name").toString());
                item.setMrp(Double.parseDouble(itm.get("mrp").toString()));
                item.setDisocuntPercentage(Integer.parseInt(itm.get("discountPercent").toString()));
                item.setDiscountedSellingPrice(Integer.parseInt(itm.get("discountedSellingPrice").toString()));
                item.setOutOfStock(itm.get("outOfStock").toString().equals("FALSE") ? false : true);
                item.setAvailableQuantity(Long.parseLong(itm.get("availableQuantity").toString()));
                item.setQuantity(Long.parseLong(itm.get("quantity").toString()));
                item.setWeightInGms(Double.parseDouble(itm.get("weightInGms").toString()));

                LinkedHashMap itemStatus = new LinkedHashMap();
                itemStatus.put("name", itm.get("name").toString());
                try {
                    itemRepository.save(item);
                    itemStatus.put("status", "success");
                } catch (Exception e) {
                    itemStatus.put("status", "failed");
                }

                itemSuccess.add(itemStatus);
            }
        }

        resp.put("result", itemSuccess);

        return resp;
    }

}
