package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.model.Union_Info;

@Repository
public interface UnionInfoRepository extends JpaRepository<Union_Info, Long> {

	@Query(value = "SELECT * FROM employeesdb.union_info WHERE id IN (?1) ORDER BY id ASC",nativeQuery = true)
	public List<Union_Info> getSelectedUniton(List<String> listUnion);
}
