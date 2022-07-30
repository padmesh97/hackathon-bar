package org.barclays.controller;


import org.barclays.model.Item;
import org.barclays.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(method = RequestMethod.GET)
    public LinkedHashMap getItems(@RequestParam(value="query") String query, HttpServletRequest httpReq, HttpServletResponse httpResp) {
        LinkedHashMap resp = new LinkedHashMap();

        if(query == null || query.isEmpty()){
            List<Item> items = itemRepository.findAll();
            resp.put("status","success");
            resp.put("items",items);
        }
        else{
            List<Item> items = itemRepository.findByNameContaining(query);
            resp.put("status","success");
            resp.put("items",items);
        }

        return resp;
    }
}
