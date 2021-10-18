/**
 *
 */
package com.easyservice.service;

/**
 * @author TharunyaREDDY
 *
 */
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.easyservice.exception.ContractNotFoundException;
import com.easyservice.exception.MaintenanceNotFoundException;
import com.easyservice.model.Contractor;
import com.easyservice.model.Maintenance;
import com.easyservice.model.Priority;
import com.easyservice.model.Status;
import com.easyservice.repository.IContractorRepository;

@Service
public class ContractorServiceImpl implements IContractorService {

	RestTemplate restTemplate;

	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private static final String BASEURL = "http://localhost:8071/maintenance-service/maintenance";

	@Autowired
	IContractorRepository contractorRepository;

	// Contractor

	@Override
	public Contractor addContractor(Contractor contractor) {
		return contractorRepository.save(contractor);
	}

	@Override
	public String updateContractor(Contractor contractor) {
		contractorRepository.save(contractor);
		return "Contract Updated Successfully";
	}

	@Override
	public String deleteContractor(int contractId) throws ContractNotFoundException {
		Contractor contractor = contractorRepository.findById(contractId).get();
		if (contractor == null) {
			throw new ContractNotFoundException("Contract Id Not Found");
		}
		contractorRepository.deleteById(contractId);
		return "Contract Deleted Succesfully";
	}

	@Override
	public Contractor getByContractorName(String contractorName) throws ContractNotFoundException {

		Contractor contractorByName = contractorRepository.findByContractorName(contractorName);
		if (contractorByName == null) {
			throw new ContractNotFoundException("Contractor Name Not Found");
		}
		return contractorByName;
	}

	@Override
	public Contractor getByContractName(String contractName) throws ContractNotFoundException {

		Contractor contractByName = contractorRepository.findByContractName(contractName);
		if (contractByName == null) {
			throw new ContractNotFoundException("Contract Name Not Found");
		}
		return contractByName;
	}

	@Override
	public List<Contractor> getByStartDate(LocalDate startDate) throws ContractNotFoundException {

		List<Contractor> contractByStartDate = contractorRepository.findByStartDate(startDate);
		if (contractByStartDate.isEmpty()) {
			throw new ContractNotFoundException("Contract Not Found with given start date");
		}
		return contractByStartDate;

	}

	@Override
	public List<Contractor> getByEndDate(LocalDate endDate) throws ContractNotFoundException {

		List<Contractor> contractByEndDate = contractorRepository.findByEndDate(endDate);
		if (contractByEndDate.isEmpty()) {
			throw new ContractNotFoundException("Contract Not Found with given end date");
		}
		return contractByEndDate;

	}

	@Override
	public List<Contractor> getByStartDateAndEndDate(LocalDate startDate, LocalDate endDate)
			throws ContractNotFoundException {

		List<Contractor> contractByStartDateAndEndDate = contractorRepository.findByStartDateAndEndDate(startDate,
				endDate);
		if (contractByStartDateAndEndDate.isEmpty()) {
			throw new ContractNotFoundException("Contract Not Found with given dates");
		}
		return contractByStartDateAndEndDate;
	}

	@Override
	public List<Contractor> getAll() {

		List<Contractor> allContract = contractorRepository.findAll();
		if (allContract.isEmpty()) {
			throw new ContractNotFoundException("Contract Not Found with given dates");
		}
		return allContract;

	}

	// Maintenance

	@Override
	public Maintenance assignMaintenance(Maintenance maintenance, int contractId) {
		Contractor contractor = contractorRepository.findById(contractId).get();
		maintenance.setContractor(contractor);
		String url = BASEURL;
		restTemplate.put(url, maintenance);
		return maintenance;
	}

	@Override
	public void unAssignMaintenance(Maintenance maintenance) {

		String url = BASEURL;
		restTemplate.put(url, maintenance);
	}

	@Override
	public String deleteMaintenance(int maintenanceId) throws MaintenanceNotFoundException {
		Maintenance maintenance = getByMaintenanceId(maintenanceId);
		if (maintenance == null) {
			throw new MaintenanceNotFoundException("Maintenance Id Not Found");
		}
		String url = BASEURL + "/" + maintenanceId;
		restTemplate.delete(url, maintenanceId);
		return "Maintenance Deleted Succesfully";
	}

	@Override
	public Maintenance getByMaintenanceId(int maintenanceId) throws MaintenanceNotFoundException {
		String url = BASEURL + maintenanceId;
		Maintenance maintenance = restTemplate.getForEntity(url, Maintenance.class).getBody();
		if (maintenance == null) {
			throw new MaintenanceNotFoundException("Maintenance Id Not Found");
		}
		return maintenance;
	}

	@Override
	public List<Maintenance> getAllMaintenence() {
		String url = BASEURL;
		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		if (response.getBody().isEmpty()) {
			throw new MaintenanceNotFoundException("Maintenance Id Not Found");
		}
		return response.getBody();
	}

	@Override
	public Maintenance getByMaintenanceName(String maintenanceName) throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceName/" + maintenanceName;
		Maintenance maintenance = restTemplate.getForEntity(url, Maintenance.class).getBody();
		if (maintenance == null) {
			throw new MaintenanceNotFoundException("Maintenance Name Not Found");
		}
		return maintenance;
	}

	@Override
	public Maintenance getByMaintenanceManager(String maintenanceManager) throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceManager/" + maintenanceManager;
		Maintenance maintenance = restTemplate.getForEntity(url, Maintenance.class).getBody();
		if (maintenance == null) {
			throw new MaintenanceNotFoundException("Maintenance Manager Not Found");
		}
		return maintenance;
	}

	@Override
	public List<Maintenance> getByMaintenanceStartDate(LocalDate mStartDate) throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceStartDate/" + mStartDate;
		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		if (response.getBody().isEmpty()) {
			throw new MaintenanceNotFoundException("Maintenance Start date Not Found");
		}
		return response.getBody();
	}

	@Override
	public List<Maintenance> getByMaintenanceEndDate(LocalDate mEndDate) throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceEndDate/" + mEndDate;
		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		if (response.getBody().isEmpty()) {
			throw new MaintenanceNotFoundException("Maintenance Not Found");
		}
		return response.getBody();
	}

	@Override
	public List<Maintenance> getByMaintenanceStatus(Status mStatus) throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceStatus/" + mStatus;
		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		if (response.getBody().isEmpty()) {
			throw new MaintenanceNotFoundException("Maintenance Not Found");
		}
		return response.getBody();
	}

	@Override
	public List<Maintenance> getByMaintenancePriority(Priority mPriority) throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenancePriority/" + mPriority;
		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		if (response.getBody().isEmpty()) {
			throw new MaintenanceNotFoundException("Maintenance Not Found");
		}
		return response.getBody();
	}

	@Override
	public List<Maintenance> getByMaintenanceStartAndEndDate(LocalDate mStartDate, LocalDate mEndDate)
			throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceStartDate/" + mStartDate + "/maintenanceEndDate/" + mEndDate;
		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		if (response.getBody().isEmpty()) {
			throw new MaintenanceNotFoundException("Maintenance Not Found");
		}
		return response.getBody();
	}

	@Override
	public List<Maintenance> getByMaintenanceStatusAndPriority(Status mStatus, Priority mPriority)
			throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceStatus/" + mStatus + "/maintenancePriority/" + mPriority;
		ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
		if (response.getBody().isEmpty()) {
			throw new MaintenanceNotFoundException("Maintenance Not Found");
		}
		return response.getBody();
	}

	@Override
	public Maintenance getByMaintenanceNameAndStatus(String maintenanceName, Status mStatus)
			throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceName/" + maintenanceName + "/maintenanceStatus/" + mStatus;
		Maintenance maintenance = restTemplate.getForEntity(url, Maintenance.class).getBody();
		if (maintenance == null) {
			throw new MaintenanceNotFoundException("Maintenance Id Not Found");
		}
		return maintenance;
	}

	@Override
	public Maintenance getByMaintenanceNameAndPriority(String maintenanceName, Priority mPriority)
			throws MaintenanceNotFoundException {
		String url = BASEURL + "/maintenanceName/" + maintenanceName + "/maintenancePriority/" + mPriority;
		Maintenance maintenance = restTemplate.getForEntity(url, Maintenance.class).getBody();
		if (maintenance == null) {
			throw new MaintenanceNotFoundException("Maintenance Id Not Found");
		}
		return maintenance;
	}

}
