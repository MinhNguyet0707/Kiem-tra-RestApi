package com.example.testapi.controller;


import com.example.testapi.model.Courses;
import com.example.testapi.service.CourseService;
import com.example.testapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;


    @GetMapping("/v1/courses")
    public ResponseEntity<List<Courses>> getAllCourses(
            @RequestParam(name = "type", required = false) String typeValue,
            @RequestParam(name = "name", required = false) String nameValue,
            @RequestParam(name = "topic", required = false) String topicValue) {

        List<Courses> courses = courseService.getAllCourses(typeValue, nameValue, topicValue);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = {"/v1/courses/{id}", "/v1/admin/courses/{id}"})
    public ResponseEntity<Courses> getCourseById(@PathVariable Long id) throws Exception {
        Courses courses = courseService.getCourseById(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("v1/admin/courses")
    public ResponseEntity<Page<Courses>> getCourses(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        Page<Courses> courses = courseService.getCourses(page, pageSize);
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/v1/admin/courses/{id}")
    public ResponseEntity<Courses> updateCourse(@PathVariable Long id, @RequestBody Courses course) {
        Courses updatedCourse = null;
        try {
            updatedCourse = courseService.updateCourse(id, course);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/v1/admin/courses/{id}")
    public void deleteCourse(@PathVariable Long id) throws Exception {
        courseService.deleteCourseById(id);
    }

    @PostMapping("/v1/admin/courses")
    public ResponseEntity<Courses> createCourse(@RequestBody Courses course) {

        Courses savedCourse = courseService.createCourse(course);
        return ResponseEntity.ok(savedCourse);
    }
}
