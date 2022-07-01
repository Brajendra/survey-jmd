package com.reliance.retail.nps.repository;

import com.reliance.retail.nps.domain.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {

}
