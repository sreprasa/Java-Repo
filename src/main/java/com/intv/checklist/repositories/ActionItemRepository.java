package com.intv.checklist.repositories;

import com.intv.checklist.domain.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem,Long> {
}
