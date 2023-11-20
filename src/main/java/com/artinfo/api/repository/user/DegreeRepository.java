package com.artinfo.api.repository.user;

import com.artinfo.api.domain.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DegreeRepository extends JpaRepository<Degree, Long> {
}
