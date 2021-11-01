package com.starwarsapi.repository;

import com.starwarsapi.model.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, String> {

    List<Rebelde> findAllByAcusacoesGreaterThanEqual(Integer acusacoes);

}
