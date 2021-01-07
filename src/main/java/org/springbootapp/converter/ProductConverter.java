//package org.springbootapp.converter;
//
//import org.springbootapp.dto.ProductDTO;
//import org.springbootapp.entity.ProductEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductConverter {
//	public ProductEntity toEntity(ProductDTO productDTO) {
//		ProductEntity productEntity = new ProductEntity();
//
//		productEntity.setProduct_name(productDTO.getProduct_name());
//		productEntity.setProduct_image(productDTO.getProduct_image());
//		productEntity.setProduct_price(productDTO.getProduct_price());
//		productEntity.setProduct_description(productDTO.getProduct_description());
//		productEntity.setProduct_evalute(productDTO.getProduct_evalute());
//
//		return productEntity;
//	}
//	
//	public ProductDTO toDTO(ProductEntity productEntity) {
//		ProductDTO productDTO = new ProductDTO();
//		
//		productDTO.setProduct_name(productEntity.getProduct_name());
//		productDTO.setProduct_image(productEntity.getProduct_image());
//		productDTO.setProduct_price(productEntity.getProduct_price());
//		productDTO.setProduct_description(productEntity.getProduct_description());
//		productDTO.setProduct_evalute(productEntity.getProduct_evalute());
//		
//		return productDTO;
//	}
//}
