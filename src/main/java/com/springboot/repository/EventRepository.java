package com.springboot.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.springboot.model.Recurent_Event;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Recurent_Event, Long> {  
	@Query("SELECT p FROM Recurent_Event p WHERE p.union_id = ?1")
	List<Recurent_Event> findByUnion(String id);
	
	@Query("SELECT p FROM Recurent_Event p WHERE "
			+"CONCAT(p.name, p.pattern, p.shift_mode, p.start_date, p.end_date)"
			+"LIKE %?1%")
	public Page<Recurent_Event> findAll(String keyword, Pageable pageable);
		
//	
//	@Query("SELECT p FROM Recurent_Event p WHERE p.union_id IN :unionIDs")
//	//query.setParameter("inclList", unionIDS);
//	public List<Recurent_Event> findByUnion(String unionIDs);
//	
	
	@Query(value = "SELECT * FROM employeesdb.recurent_event WHERE union_id IN (?1)", nativeQuery = true)
	List<Recurent_Event> findByUnionIdANDMonth(List<String> unionId);
	
	

}


