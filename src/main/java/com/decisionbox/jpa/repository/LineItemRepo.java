//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.jpa.repository;

import com.decisionbox.jpa.entity.TableData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepo extends CrudRepository<TableData, Integer> {
    TableData findByProcessInstanceId(String var1);
}
