/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nth15
 */
public class TopProductReport {
    
    public List<Map<String,Object>> report(List<TopProductStaticModel> listProducts){
        try {
            List<Map<String,Object>> rows = new ArrayList<>();
            for (TopProductStaticModel t : listProducts) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", t.getId());
                row.put("name", t.getName());
                row.put("sold", t.getSold());
                row.put("orderIds", t.getOrderId());
                row.put("seller", t.getSeller());
                rows.add(row);
            }
            return rows;
        } catch (Exception e) {
            return null;
        }
    }
}
