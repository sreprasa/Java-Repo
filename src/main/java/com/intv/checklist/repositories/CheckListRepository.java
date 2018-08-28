package com.intv.checklist.repositories;

import com.intv.checklist.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends JpaRepository<Checklist,Long> {

}
