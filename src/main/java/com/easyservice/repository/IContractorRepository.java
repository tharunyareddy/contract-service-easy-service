/**
 *
 */
package com.easyservice.repository;

/**
 * @author TharunyaREDDY
 *
 */
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.easyservice.model.Contractor;

@Repository
public interface IContractorRepository extends JpaRepository<Contractor, Integer> {

	// contractor

	Contractor findByContractName(String contractName);

	Contractor findByContractorName(String contractorName);

	List<Contractor> findByStartDate(LocalDate startDate);

	List<Contractor> findByEndDate(LocalDate endDate);

	@Query("from Contractor c where c.startDate=?1 and c.endDate=?2")
	List<Contractor> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

}
