package edu.bi.springdemo.repository;

import edu.bi.springdemo.entity.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Loan, Integer> {
}
