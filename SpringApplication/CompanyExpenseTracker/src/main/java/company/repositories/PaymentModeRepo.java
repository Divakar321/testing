package company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import company.entities.PaymentMode;

public interface PaymentModeRepo extends JpaRepository<PaymentMode, String> {

}
