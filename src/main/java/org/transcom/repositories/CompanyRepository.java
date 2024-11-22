package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    
}
