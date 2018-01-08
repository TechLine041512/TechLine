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
public class BrandsFacade extends AbstractFacade<Brands> implements BrandsFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BrandsFacade() {
        super(Brands.class);
    }

    @Override
    public String newBrandId() {
        javax.persistence.Query q = em.createQuery("SELECT b FROM Brands b ORDER BY b.brandId DESC");
        List<Brands> list = q.setMaxResults(1).getResultList();
        if (list != null) {
            String lastBrand = list.get(0).getBrandId().replace("BRN", "");
            int lastNum = Integer.parseInt(lastBrand) + 1;
            String newId = String.format("BRN"+"%03d", lastNum);
            return newId;
        }
        return null;
    }

    @Override
    public List<Brands> showAll() {
        javax.persistence.Query q = em.createQuery("SELECT b FROM Brands b");
        List<Brands> list = q.getResultList();
        if (list != null) {
            return list;
        }
        return null;
    }
    
    
    
}
