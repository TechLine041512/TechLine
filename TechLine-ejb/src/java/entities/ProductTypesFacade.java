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
public class ProductTypesFacade extends AbstractFacade<ProductTypes> implements ProductTypesFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductTypesFacade() {
        super(ProductTypes.class);
    }

    @Override
    public String newId() {
        javax.persistence.Query q = em.createQuery("SELECT p FROM ProductTypes p ORDER BY p.typeId DESC");
        List<ProductTypes> list = q.setMaxResults(1).getResultList();
        if (list != null) {
            String lastId = list.get(0).getTypeId().replace("PTY", "");
            int lastNum = Integer.parseInt(lastId) + 1;
            String newId = String.format("PTY"+"%03d", lastNum);
            return newId;
        }
        return null;
    }

    @Override
    public List<ProductTypes> showAll() {
        javax.persistence.Query q = em.createQuery("SELECT p FROM ProductTypes p");
        List<ProductTypes> list = q.getResultList();
        if (list != null ) {
            return list;
        }
        return null;
    }
    
    
}
