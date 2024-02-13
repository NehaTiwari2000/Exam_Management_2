package org.exam.repo;

import org.exam.document.Cateory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICateoryRepo extends JpaRepository<Cateory,Integer> {
}
