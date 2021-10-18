/**
 *
 */
package com.easyservice.controller;

/**
 * @author TharunyaREDDY
 *
 */
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyservice.exception.ContractNotFoundException;
import com.easyservice.model.Contractor;
import com.easyservice.model.Maintenance;
import com.easyservice.model.Priority;
import com.easyservice.model.Status;
import com.easyservice.service.IContractorService;

@RestController
@RequestMapping("/contractor-service")
public class ContractorController {

	@Autowired
	IContractorService contractorService;

	// Contractor

	// http://localhost:8070/contractor-service/contracts
	@PostMapping("/contracts")
	public Contractor addContractor(@RequestBody Contractor contractor) {
		return contractorService.addContractor(contractor);
	}

	// http://localhost:8070/contractor-service/contracts
	@PutMapping("/contracts")
	public void updateContractor(@RequestBody Contractor contractor) {
		contractorService.updateContractor(contractor);
	}

	// http://localhost:8070/contractor-service/contracts/1
	@DeleteMapping("/contracts/{contractId}")
	public void deleteContractor(@PathVariable("contractId") int contractId) throws ContractNotFoundException {
		contractorService.deleteContractor(contractId);
	}

	// http://localhost:8070/contractor-service/contracts
	@GetMapping("/contracts")
	public List<Contractor> getAll() {
		return contractorService.getAll();
	}

	// http://localhost:8070/contractor-service/contracts/Achreon
	@GetMapping("/contracts/{contractName}")
	public Contractor getByContractName(@PathVariable("contractName") String contractName)
			throws ContractNotFoundException {
		return contractorService.getByContractName(contractName);
	}

	// http://localhost:8070/contractor-service/contracts/contractorName/Ayan
	@GetMapping("/contracts/contractorName/{contractorName}")
	public Contractor getByContractorName(@PathVariable("contractorName") String contractorName) {
		return contractorService.getByContractorName(contractorName);
	}

	// http://localhost:8070/contractor-service/contracts/startdate/2021-10-12
	@GetMapping("/contracts/startdate/{startDate}")
	public List<Contractor> getByStartAndEndDate(@PathVariable("startDate") String startDate) {
		return contractorService.getByStartDate(LocalDate.parse(startDate));
	}

	// http://localhost:8070/contractor-service/contracts/enddate/2021-10-22
	@GetMapping("/contracts/enddate/{endDate}")
	public List<Contractor> getByEndDate(@PathVariable("endDate") String endDate) {
		return contractorService.getByEndDate(LocalDate.parse(endDate));
	}

	// http://localhost:8070/contractor-service/contracts/startdate/2021-10-12/enddate/2021-10-22
	@GetMapping("/contracts/startdate/{startDate}/enddate/{endDate}")
	public List<Contractor> getByStartAndEndDate(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		return contractorService.getByStartDateAndEndDate(LocalDate.parse(startDate), LocalDate.parse(endDate));
	}

	// Maintenance

	// http://localhost:8070/contractor-service/contracts/maintenance/1
	@PostMapping("/contracts/maintenance/{contractId}")
	ResponseEntity<Maintenance> addMaintenance(@RequestBody Maintenance maintenance,
			@PathVariable("contractId") int contractId) {
		Maintenance maintenance1 = contractorService.assignMaintenance(maintenance, contractId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Add Maintenance");
		headers.add("info", "Add");
		ResponseEntity<Maintenance> maintenanceResponse = new ResponseEntity<Maintenance>(maintenance, headers,
				HttpStatus.OK);
		return maintenanceResponse;
	}

	// http://localhost:8070/contractor-service/contracts/maintenance
	@PutMapping("/contracts/maintenance")
	ResponseEntity<Void> updateMaintenance(@RequestBody Maintenance maintenance) {
		contractorService.unAssignMaintenance(maintenance);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "update Maintenance");
		headers.add("info", "update");
		return ResponseEntity.ok().headers(headers).build();
	}

	// http://localhost:8070/contractor-service/contracts/maintenance/100
	@DeleteMapping("/contracts/maintenance/{maintenanceId}")
	ResponseEntity<Void> deleteMaintenance(@PathVariable("maintenanceId") int maintenanceId) {
		contractorService.deleteMaintenance(maintenanceId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Delete Maintenance");
		headers.add("info", "Delete");
		return ResponseEntity.ok().headers(headers).build();
	}

	// http://localhost:8070/contractor-service/contracts/maintenanceName/Cleaning
	@GetMapping("/contracts/maintenanceName/{maintenanceName}")
	ResponseEntity<Maintenance> getByMaintenanceName(@PathVariable("maintenanceName") String maintenanceName) {
		Maintenance maintenance = contractorService.getByMaintenanceName(maintenanceName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Name");
		headers.add("info", "Returning maintenance");
		ResponseEntity<Maintenance> maintenanceResponse = new ResponseEntity<Maintenance>(maintenance, headers,
				HttpStatus.OK);
		return maintenanceResponse;
	}

	// http://localhost:8070/contractor-service/contracts/maintenanceManager/Kavi
	@GetMapping("/contracts/maintenanceManager/{maintenanceManager}")
	ResponseEntity<Maintenance> getByMaintenanceManager(@PathVariable("maintenanceManager") String maintenanceManager) {
		Maintenance maintenance = contractorService.getByMaintenanceManager(maintenanceManager);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Manager");
		headers.add("info", "Returning maintenance");
		ResponseEntity<Maintenance> maintenanceResponse = new ResponseEntity<Maintenance>(maintenance, headers,
				HttpStatus.OK);
		return maintenanceResponse;
	}

	// http://localhost:8070/contractor-service/contracts/maintenanceStartDate/2021-10-12
	@GetMapping("/contracts/maintenanceStartDate/{mStartDate}")
	ResponseEntity<List<Maintenance>> getByMaintenanceStartDate(@PathVariable("mStartDate") String mStartDate) {
		List<Maintenance> maintenanceList = contractorService.getByMaintenanceStartDate(LocalDate.parse(mStartDate));
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Start date");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenanceList);
	}

	// http://localhost:8070/contractor-service/contracts/maintenanceEndDate/2021-10-20
	@GetMapping("/contracts/maintenanceEndDate/{mEndDate}")
	ResponseEntity<List<Maintenance>> getByMaintenanceEndDate(@PathVariable("mEndDate") String mEndDate) {
		List<Maintenance> maintenanceList = contractorService.getByMaintenanceEndDate(LocalDate.parse(mEndDate));
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By End date");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenanceList);
	}

	// http://localhost:8070/contractor-service/contracts/maintenanceStatus/INPROGRESS
	@GetMapping("/contracts/maintenanceStatus/{mStatus}")
	ResponseEntity<List<Maintenance>> getByMaintenanceStatus(@PathVariable("mStatus") Status mStatus) {
		List<Maintenance> maintenanceList = contractorService.getByMaintenanceStatus(mStatus);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Status");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenanceList);
	}

	// http://localhost:8070/contractor-service/contracts/maintenancePriority/HIGH
	@GetMapping("/contracts/maintenancePriority/{mPriority}")
	ResponseEntity<List<Maintenance>> getByMaintenancePriority(@PathVariable("mPriority") Priority mPriority) {
		List<Maintenance> maintenanceList = contractorService.getByMaintenancePriority(mPriority);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Priority");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenanceList);
	}

	// http://localhost:8070/contractor-service/contracts/maintenanceStartDate/2021-10-12/maintenanceEndDate/2021-10-22
	@GetMapping("/contracts/maintenanceStartDate/{mStartDate}/maintenanceEndDate/{mEndDate}")
	ResponseEntity<List<Maintenance>> getByMaintenanceStartAndEndDate(@PathVariable("mStartDate") String mStartDate,
			@PathVariable("mEndDate") String mEndDate) {
		List<Maintenance> maintenanceList = contractorService
				.getByMaintenanceStartAndEndDate(LocalDate.parse(mStartDate), LocalDate.parse(mEndDate));
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By dates");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenanceList);

	}

	// http://localhost:8070/contractor-service/contracts/maintenanceStatus/INPROGRESS/maintenancePriority/HIGH
	@GetMapping("/contracts/maintenanceStatus/{mStatus}/maintenancePriority/{mPriority}")
	ResponseEntity<List<Maintenance>> getByMaintenanceStatusAndPriority(@PathVariable("mStatus") Status mStatus,
			@PathVariable("mPriority") Priority mPriority) {

		List<Maintenance> maintenanceList = contractorService.getByMaintenanceStatusAndPriority(mStatus, mPriority);

		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Priority and status");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenanceList);

	}

	// http://localhost:8070/contractor-service/contracts/maintenanceName/Cleaning/maintenanceStatus/INPROGRESS
	@GetMapping("/contracts/maintenanceName/{maintenanceName}/maintenanceStatus/{mStatus}")
	ResponseEntity<Maintenance> getByMaintenanceNameAndStatus(@PathVariable("maintenanceName") String maintenanceName,
			@PathVariable("mStatus") Status mStatus) {

		Maintenance maintenance = contractorService.getByMaintenanceNameAndStatus(maintenanceName, mStatus);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Priority and status");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenance);
	}

	// http://localhost:8070/contractor-service/contracts/maintenanceName/Cleaning/maintenancePriority/HIGH
	@GetMapping("/contracts/maintenanceName/{maintenanceName}/maintenancePriority/{mPriority}")
	ResponseEntity<Maintenance> getByMaintenanceNameAndPriority(@PathVariable("maintenanceName") String maintenanceName,
			@PathVariable("mPriority") Priority mPriority) {
		Maintenance maintenance = contractorService.getByMaintenanceNameAndPriority(maintenanceName, mPriority);
		HttpHeaders headers = new HttpHeaders();
		headers.add("desc", "Get Maintenance By Priority and status");
		headers.add("info", "Returning maintenance");
		return ResponseEntity.accepted().headers(headers).body(maintenance);
	}

}
