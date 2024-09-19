package eu.skypotion.mongo.shop.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.mongo.shop.model.ShopCategory;

@Collection("shop_categories")
public interface ShopCategoryRepository extends Repository<ShopCategory, String> {
}
