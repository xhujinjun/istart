package com.istart.framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.istart.framework.domain.DicType;

public interface DicTypeRepository  extends JpaRepository<DicType,Long>,JpaSpecificationExecutor<DicType>{

}
