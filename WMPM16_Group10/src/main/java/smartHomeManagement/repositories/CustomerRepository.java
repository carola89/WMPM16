package smartHomeManagement.repositories;

import org.springframework.data.repository.CrudRepository;
import smartHomeManagement.models.Customer;

import java.util.List;

/**
 * Created by Martina on 12.05.2016.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByName(String name);
}
