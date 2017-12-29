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
public class ProductsCommentFacade extends AbstractFacade<ProductsComment> implements ProductsCommentFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsCommentFacade() {
        super(ProductsComment.class);
    }

    @Override
    public List<ProductsComment> getListComment(String productID) {
        Query q = em.createQuery("SELECT p FROM ProductsComment p WHERE p.productId.productId = :productID");
        q.setParameter("productID", productID);
        return q.getResultList();
    }
    
            
}
