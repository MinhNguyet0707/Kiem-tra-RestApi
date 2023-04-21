package com.example.testapi.service;


import com.example.testapi.model.Courses;
import com.example.testapi.model.User;
import com.example.testapi.repository.CourseRepository;
import com.example.testapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;


    public List<Courses> getAllCourses(String typeValue, String nameValue, String topicValue) {
        if(!StringUtils.isEmpty(typeValue) || !StringUtils.isEmpty(nameValue) || !StringUtils.isEmpty(topicValue)) {
            return courseRepository.findByTypeAndNameContainingIgnoreCaseAndTopicsContainingIgnoreCase(typeValue, nameValue, topicValue);
        } else {
            return courseRepository.findAll();
        }
    }

    public Courses getCourseById(Long id) throws Exception {
        Optional<Courses> optionalCourse = courseRepository.findById(id);

        if(!optionalCourse.isPresent()){
            throw new Exception(String.format("Không tìm thấy User với id = %s", id));
        }

        return optionalCourse.get();
    }


    public Page<Courses> getCourses(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
       return courseRepository.findAll(pageable);
    }

    public Courses updateCourse(Long id, Courses course) throws Exception {
        Optional<Courses> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Courses existingCourse = optionalCourse.get();
            existingCourse.setName(course.getName());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setType(course.getType());
            existingCourse.setTopics(course.getTopics());
            existingCourse.setThumbnail(course.getThumbnail());
            existingCourse.setUser(course.getUser());
            return courseRepository.save(existingCourse);
        } else {
            throw new Exception(String.format("Không tìm thấy User với id = %s", id));

        }
    }


    public void deleteCourseById(Long id) throws Exception {
        Optional<Courses> optionalCourse = courseRepository.findById(id);
        if(!optionalCourse.isPresent()){
            throw new Exception(String.format("Không tìm thấy User với id = %s", id));
        } else {
            courseRepository.deleteById(id);
        }
    }


    public Courses createCourse(Courses course) {
        Optional<User> optionalUser = userRepository.findById((course.getUser().getId()));
        if(!optionalUser.isPresent()){
            userRepository.save(course.getUser());
        }
        Courses courses = courseRepository.save(course);
        return courses;
    }
}
