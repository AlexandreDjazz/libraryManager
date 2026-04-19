package com.example.librarymanager.hold.infrastructure.persistence;

import com.example.librarymanager.hold.domain.HoldStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface SpringJpaHoldRepository extends JpaRepository<JpaHold, String> {
    List<JpaHold> findByWorkIdAndStatusOrderByQueuePositionAsc(String workId, HoldStatus status);
    List<JpaHold> findByStatus(HoldStatus status);

    @Query("""
            SELECT h FROM JpaHold h WHERE h.workId = :workId AND h.userId = :userId
            AND h.status NOT IN ('PICKED_UP', 'EXPIRED', 'CANCELLED')
            """)
    Optional<JpaHold> findActiveByWorkIdAndUserId(@Param("workId") String workId,
                                                   @Param("userId") String userId);

    @Query("""
            SELECT COUNT(h) FROM JpaHold h WHERE h.workId = :workId
            AND h.status NOT IN ('PICKED_UP', 'EXPIRED', 'CANCELLED')
            """)
    long countActiveByWorkId(@Param("workId") String workId);
}
