
package com.test.ecommerce.repository;

import com.test.ecommerce.entity.ContactMech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMechRepository extends JpaRepository<ContactMech, Integer> {
}