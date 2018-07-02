package com.task.dao;

import com.task.domain.entity.ReverseString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA default H2 DAO
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@Repository
public interface ReverseStringDao extends JpaRepository<ReverseString, Long> {
}
