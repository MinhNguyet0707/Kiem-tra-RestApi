package com.example.testapi.repository;


import com.example.testapi.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {

    List<Courses> findByTypeAndNameContainingIgnoreCaseAndTopicsContainingIgnoreCase(
            String type, String name, String topic);
}
