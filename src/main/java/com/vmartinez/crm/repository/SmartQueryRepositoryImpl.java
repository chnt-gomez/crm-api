package com.vmartinez.crm.repository;

import com.vmartinez.crm.dto.TransactionQuery;
import com.vmartinez.crm.entity.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class SmartQueryRepositoryImpl  implements SmartQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transaction> executeQuery(TransactionQuery query) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> root = criteriaQuery.from(Transaction.class);

        List<Predicate> predicates = new ArrayList<>();

        if (query.getCustomerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("customerId"), query.getCustomerId()));
        }

        if (query.getType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), query.getType()));
        }

        if (query.getTimestampStart() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), query.getTimestampStart()));
        }

        if (query.getTimestampEnd() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), query.getTimestampEnd()));
        }

        if (query.getValue() != null && !query.getValue().isBlank()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("value")), "%" + query.getValue().toLowerCase() + "%"));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("timestamp")));

        return entityManager.createQuery(criteriaQuery).getResultList();

    }
}
