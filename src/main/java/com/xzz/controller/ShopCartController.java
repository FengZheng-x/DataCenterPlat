package com.xzz.controller;

import com.xzz.dto.ShopCartDTO;
import com.xzz.service.ShopCartService;
import com.xzz.vo.ShopCartVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {
    private ShopCartService shopCartService;

    public ShopCartController(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }

    @PutMapping("/put")
    public ResponseEntity<Void> put(@NotNull(message = "userId不能为空") Long userId,
                                    @NotNull(message = "skuId不能为空") Long skuId,
                                    @NotNull(message = "finalValue不能为空")
                                    @Min(value = 1, message = "finalValue不能小于{value}") Long finalValue) {
        shopCartService.put(userId, skuId, finalValue);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/shopCarts")
    public ResponseEntity<List<ShopCartVO>> shopCarts(@NotNull(message = "userId不能为空") Long userId,
                                                      @NotNull(message = "currentPage不能为空")
                                                      @Min(value = 1, message = "currentPage不能小于{value}") Integer currentPage) {
        List<ShopCartDTO> shopCartDTOs = shopCartService.shopCarts(userId, currentPage);
        return ResponseEntity.ok(ShopCartVO.of(shopCartDTOs));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> remove(@NotNull(message = "userId不能为空") Long userId,
                                       @NotEmpty(message = "skuIds不能为空") Long... skuIds) {
        shopCartService.remove(userId, skuIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear(@NotNull(message = "userId不能为空") Long userId) {
        shopCartService.clear(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Integer> into(@NotNull(message = "userId不能为空") Long userId) {
        return ResponseEntity.ok(shopCartService.into(userId));
    }
}
