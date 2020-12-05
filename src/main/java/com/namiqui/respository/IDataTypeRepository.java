package com.namiqui.respository;

import com.namiqui.models.DataType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDataTypeRepository extends JpaRepository<DataType, Integer> {

    DataType findByName(String name);
}
