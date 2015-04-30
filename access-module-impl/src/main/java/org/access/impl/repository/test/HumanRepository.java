package org.access.impl.repository.test;

import java.util.UUID;

import org.access.impl.entity.test.Human;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepository extends JpaRepository<Human, UUID> {

}
