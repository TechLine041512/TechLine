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
public class OrderMasterFacade extends AbstractFacade<OrderMaster> implements OrderMasterFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderMasterFacade() {
        super(OrderMaster.class);
    }

    @Override
    public String newId() {
        javax.persistence.Query q = em.createQuery("SELECT o FROM OrderMaster o ORDER BY o.orderMId DESC");
        List<OrderMaster> list = q.setMaxResults(1).getResultList();
        if (list != null) {
            String lastId = list.get(0).getOrderMId().replace("ORD", "");
            int lastNum = Integer.parseInt(lastId) + 1;
            String newId = String.format("ORD" + "%03d", lastNum);
            return newId;
        }
        return null;
    }

    @Override
    public int countDoneOrder() {
        Query q = em.createNamedQuery("OrderMaster.findByOrderStatus");
        try {
            q.setParameter("orderStatus", "Done");
        } catch (Exception e) {
        }
        return q.getResultList().size();
    }
    
}
