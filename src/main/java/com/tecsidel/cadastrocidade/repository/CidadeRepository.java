package com.tecsidel.cadastrocidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsidel.cadastrocidade.model.Cidade;
import com.tecsidel.cadastrocidade.model.UF;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	List<Cidade> findByUf(UF uf);
	List<Cidade> findByNomeContainingIgnoreCase(String nome);
	List<Cidade> findByUfAndNomeContainingIgnoreCase(UF uf, String name);
	
}
