package com.issougames.test_helios_first;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;

@Entity
@Table(name = "request_statistics", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"int1", "int2", "request_limit", "str1", "str2"})
})
public class RequestStatistic {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private int int1;

	@Column(nullable = false)
	private int int2;

	@Column(name = "request_limit", nullable = false)
	private int requestLimit;

	@Column(nullable = false)
	private String str1;

	@Column(nullable = false)
	private String str2;

	@Column(name = "hit_count", nullable = false)
	private long hitCount;

	protected RequestStatistic() {
	}

	public RequestStatistic(int int1, int int2, int requestLimit, String str1, String str2) {
		this.int1 = int1;
		this.int2 = int2;
		this.requestLimit = requestLimit;
		this.str1 = str1;
		this.str2 = str2;
		this.hitCount = 0;
	}

	public UUID getId() { return id; }
	public int getInt1() { return int1; }
	public int getInt2() { return int2; }
	public int getRequestLimit() { return requestLimit; }
	public String getStr1() { return str1; }
	public String getStr2() { return str2; }
	public long getHitCount() { return hitCount; }

	public void incrementCount() {
		this.hitCount++;
	}
}
