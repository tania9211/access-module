package org.access.impl.repository.test;

import java.util.UUID;

import org.access.impl.entity.test.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository  extends JpaRepository<Animal, UUID> {
}
