package com.issougames.test_helios_first;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestStatisticRepository extends JpaRepository<RequestStatistic, UUID> {

	RequestStatistic findByInt1AndInt2AndRequestLimitAndStr1AndStr2(
			int int1, int int2, int requestLimit, String str1, String str2);

	@Query("SELECT r FROM RequestStatistic r ORDER BY r.hitCount DESC, r.int1 ASC, r.int2 ASC, r.requestLimit ASC, r.str1 ASC, r.str2 ASC")
	List<RequestStatistic> findTopRequests(Pageable pageable);
}
