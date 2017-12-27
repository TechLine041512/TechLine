/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Products;
import java.util.ArrayList;
import java.util.List;
import models.ProductIndexModel;

/**
 *
 * @author nth15
 */
public class TechLineUtils {

    public static List<ProductIndexModel> buidProductIndexModel(List<Products> listProduct) {
        List<ProductIndexModel> listResult = new ArrayList<>();
        List<String> listImages = new ArrayList<>();
        for (Products item : listProduct) {
            ProductIndexModel model = buildProduct(item);
            listResult.add(model);
        }
        if (!listResult.isEmpty()) {
            return listResult;
        }
        return null;
    }

    public static ProductIndexModel buildProduct(Products item) {
        List<String> listImages = new ArrayList<>();
        ProductIndexModel model = new ProductIndexModel();
        model.setProductId(item.getProductId());
        model.setProductName(item.getProductName());
        model.setProductPrice(item.getProductPrice());
        for (String image : item.getProductImage().split(",")) {
            listImages.add(image);
        }
        model.setProductImage(listImages);
        model.setBrandName(item.getBrandId().getBrandName());
        model.setProductHeigth(item.getProductHeigth());
        model.setProductLength(item.getProductHeigth());
        model.setProductWidth(item.getProductWidth());
        model.setProductWeight(item.getProductWeight());
        model.setProductQuantity(item.getProductQuantity());
        model.setProductDesc(item.getProductDesc());
        model.setProductSummary(item.getProductSummary());
        model.setProductsCommentCollection(item.getProductsCommentCollection());
        double discountPrice = (item.getProductPrice() * item.getProductDiscount()) / 100;
        model.setProductDiscount(discountPrice);
        return model;
    }
}
