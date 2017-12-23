/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author nth15
 */
@Local
public interface OrderMasterFacadeLocal {

    void create(OrderMaster orderMaster);

    void edit(OrderMaster orderMaster);

    void remove(OrderMaster orderMaster);

    OrderMaster find(Object id);

    List<OrderMaster> findAll();

    List<OrderMaster> findRange(int[] range);

    int count();
    
}
