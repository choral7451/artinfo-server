package com.artinfo.api.repository.user;

import com.artinfo.api.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
