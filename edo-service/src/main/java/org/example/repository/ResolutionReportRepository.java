/**
 * Репозиторий для работы с сущностью Report.
 */

package org.example.repository;

import org.example.entity.ResolutionReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResolutionReportRepository extends JpaRepository<ResolutionReport, Long> {
}
