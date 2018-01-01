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
        String ProductID = "";
        Query q = em.createNamedQuery("Products.findAll");
        List<Products> list = q.getResultList();
        int lastNumb = Integer.parseInt(list.get(list.size() - 1).getProductId().substring(0, 5));
        if (lastNumb < 9) {
            ProductID = "PRO00" + (lastNumb + 1);
        } else if (lastNumb < 98) {
            ProductID = "PRO0" + (lastNumb + 1);
        } else {
            ProductID = "PRO" + (lastNumb + 1);
        }
        return ProductID;
    }

    @Override
    public List<Products> getListProductBySeller(String seller) {
        Query q = em.createQuery("SELECT p FROM Products p WHERE p.userId.userId = :userId");
        q.setParameter("userId", seller);
        return q.getResultList();
    }
}
