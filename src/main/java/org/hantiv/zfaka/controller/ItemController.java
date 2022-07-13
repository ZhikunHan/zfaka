package org.hantiv.zfaka.controller;

import org.hantiv.zfaka.entity.Item;
import org.hantiv.zfaka.response.ResponseModel;
import org.hantiv.zfaka.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 10:53 2022/7/13
 * @Description:
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(origins = "{server.addresss}", allowedHeaders = "*", allowCredentials = "true")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getItemList() {
        List<Item> items = itemService.findItemOnPromotion();
        return new ResponseModel(items);
    }
    @RequestMapping(path = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getItemDetail(@PathVariable("id") int id) {
        Item item = itemService.findItemInCache(id);
        return new ResponseModel(item);
    }
}
