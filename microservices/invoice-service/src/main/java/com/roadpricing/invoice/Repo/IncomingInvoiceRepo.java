package com.roadpricing.invoice.Repo;

import com.roadpricing.invoice.Model.IncomingInvoiceMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomingInvoiceRepo extends MongoRepository<IncomingInvoiceMongo, String> {

    List<IncomingInvoiceMongo> findAllByRouteDBId(String routeId);
}
