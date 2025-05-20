package com.cookMaster.cookMaster_back.repository;

import com.cookMaster.cookMaster_back.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
