/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class ProductsFacade extends AbstractFacade<Products> implements ProductsFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }
    
    @Override
    public List<Products> getListProductByDatePost() {
        try { 
            Query q = em.createQuery("SELECT p FROM Products p ORDER BY p.datePosted DESC");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Products> getListProductByDiscount() {
        try { 
            Query q = em.createQuery("SELECT p FROM Products p ORDER BY p.productDiscount DESC");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Products> getListProductsByName(String productName) {
        Query q = em.createQuery("SELECT p FROM Products p WHERE p.productName like :productName");
        q.setParameter("productName", "%"+productName+"%");
        return q.getResultList();
    }
    
    @Override
    public List<Products> getListProductSeller() {
        Query q = em.createQuery("SELECT p FROM Products p WHERE p.userId <> :userId");
        q.setParameter("userId", null);
        return q.getResultList();
    }
    
    @Override
    public String newProductID() {
        Query q = em.createQuery("SELECT p FROM Products p ORDER BY p.productId DESC");
        List<Products> p = q.setMaxResults(1).getResultList();
        if (p != null) {
            String lastProductID = p.get(0).getProductId().replace("PRO", "");
            int lastNumb = Integer.parseInt(lastProductID) + 1;
            String productID = String.format("PRO"+"%03d", lastNumb);
            return productID;
        }
        return null;
    }

    @Override
    public List<Products> getListProductBySeller(String seller) {
        Query q = em.createQuery("SELECT p FROM Products p WHERE p.userId.userId = :userId");
        q.setParameter("userId", seller);
        return q.getResultList();
    }

    @Override
    public long countSoldProduct() {
        javax.persistence.Query q = em.createQuery("SELECT COUNT(p) FROM Products p WHERE p.orderDetailsCollection IS NOT EMPTY");
        long count = (long) q.getSingleResult();
        return count;
    }
    
    
}
