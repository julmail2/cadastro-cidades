package com.tecsidel.cadastrocidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tecsidel.cadastrocidade.model.UF;

@Repository
public interface UFRepository extends JpaRepository<UF, Integer> {
	
	List<UF> findByOrderBySiglaAsc();
}
