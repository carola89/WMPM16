package smartHomeManagement.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import smartHomeManagement.models.ColdWaterConsumption;

public interface ColdWaterRepository extends CrudRepository<ColdWaterConsumption, Long> {
	List<ColdWaterConsumption> findBySmartMeterNr(int smartMeterNr);
}
