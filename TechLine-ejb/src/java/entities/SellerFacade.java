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

@Stateless
public class SellerFacade extends AbstractFacade<Seller> implements SellerFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SellerFacade() {
        super(Seller.class);
    }

    @Override
    public List<Seller> showAll() {
        javax.persistence.Query q = em.createQuery("SELECT s FROM Seller s");
        List<Seller> list = q.getResultList();
        if (list != null ) {
            return list;
        }
        return null;
    }
    
    
}
