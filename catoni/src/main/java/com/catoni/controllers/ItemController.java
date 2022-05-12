package com.catoni.controllers;

import com.catoni.models.Item;
import com.catoni.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "/classify-item", method = RequestMethod.GET, produces = "application/json")
    public Item getQuestions(@RequestParam(required = true) String id, @RequestParam(required = true) String name,
                             @RequestParam(required = true) double cost, @RequestParam(required = true) double salePrice) {

        Item newItem = new Item(Long.parseLong(id), name, cost, salePrice);

        System.out.println("Item request received for: " + newItem);

        Item i2 = itemService.getClassifiedItem(newItem);
        System.out.println(i2);
        return i2;
    }
}
