package com.codewithmosh.store.mapper;

import com.codewithmosh.store.dtos.OrderItemProductDto;
import com.codewithmosh.store.dtos.product.ProductDto;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id" , target = "categoryId")
    ProductDto toDto(Product product);
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDto productDto);
    @Mapping(source = "categoryId", target = "category.id")
    void update(ProductDto source , @MappingTarget Product target);

    OrderItemProductDto toOrderItemProductDto(Product product);
}
