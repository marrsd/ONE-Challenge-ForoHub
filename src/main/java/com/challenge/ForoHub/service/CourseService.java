package com.challenge.ForoHub.service;

import com.challenge.ForoHub.domain.Course;
import com.challenge.ForoHub.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) throws Exception {
        var response = courseRepository.save(course);
        return response;
    }

    public Page<Course> getAllCouses(Pageable pagination) {
        Page<Course> coursePage = courseRepository.findAll(pagination);
        return coursePage;
    }

    public Course getCourseById(Long id) throws Exception {
        var response = courseRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("El curso con id " + id + " no se encuentra registrado"));
        return response;
    }

    public Course updateCourse(Course course, Long id) throws Exception {
        var response = courseRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("El curso con id " + id + " no se encuentra registrado"));
        response.setName(course.getName());
        response.setCategory(course.getCategory());

        var data = courseRepository.save(response);

        return data;
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

}
