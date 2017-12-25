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

/**
 *
 * @author nth15
 */
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
    
    
}
