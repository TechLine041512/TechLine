/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductsEditHistoryFacade extends AbstractFacade<ProductsEditHistory> implements ProductsEditHistoryFacadeLocal {
    @PersistenceContext(unitName = "TechLine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsEditHistoryFacade() {
        super(ProductsEditHistory.class);
    }
    
}
