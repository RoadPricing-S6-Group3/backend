package com.roadpricing.invoice.Repo;

import com.roadpricing.invoice.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findById(Long id);
    List<Invoice> findAll();
}
