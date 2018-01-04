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
public class CategoriesFacade extends AbstractFacade<Categories> implements CategoriesFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriesFacade() {
        super(Categories.class);
    }

    @Override
    public String newId() {
        javax.persistence.Query q = em.createQuery("SELECT c FROM Categories c ORDER BY c.categoryId DESC");
        List<Categories> list = q.setMaxResults(1).getResultList();
        if (list != null) {
            String lastId = list.get(0).getCategoryId().replace("CAT", "");
            int lastNum = Integer.parseInt(lastId) + 1;
            String newId = String.format("CAT" + "%03d", lastNum);
            return newId;
        }
        return null;
    }
    
}
