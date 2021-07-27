package com.manageuniversity.config;

import com.manageuniversity.mapper.ClassMapper;
import com.manageuniversity.mapper.ClassMapperImpl;
import com.manageuniversity.mapper.CourseMapper;
import com.manageuniversity.mapper.CourseMapperImpl;
import com.manageuniversity.mapper.EventMapper;
import com.manageuniversity.mapper.EventMapperImpl;
import com.manageuniversity.mapper.ExamMapper;
import com.manageuniversity.mapper.ExamMapperImpl;
import com.manageuniversity.mapper.ExamResultMapper;
import com.manageuniversity.mapper.ExamResultMapperImpl;
import com.manageuniversity.mapper.PlanMapper;
import com.manageuniversity.mapper.PlanMapperImpl;
import com.manageuniversity.mapper.RegistrationMapper;
import com.manageuniversity.mapper.RegistrationMapperImpl;
import com.manageuniversity.mapper.StudentMapper;
import com.manageuniversity.mapper.StudentMapperImpl;
import com.manageuniversity.mapper.TeacherMapper;
import com.manageuniversity.mapper.TeacherMapperImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
	@Bean
	public ClassMapper classMapper() {
		return new ClassMapperImpl();
	}

	@Bean
	public CourseMapper courseMapper(){
		return new CourseMapperImpl();
	}
	@Bean
	public EventMapper eventMapper(){
		return new EventMapperImpl();
	}
	@Bean
	public ExamMapper examMapper(){
		return new ExamMapperImpl();
	}
	@Bean
	public ExamResultMapper examResultMapper(){
		return new ExamResultMapperImpl();
	}

	@Bean
	public PlanMapper planMapper(){
		return new PlanMapperImpl();
	}

	@Bean
	public RegistrationMapper registrationMapper(){
		return new RegistrationMapperImpl();
	}

	@Bean
	public StudentMapper studentMapper(){
		return new StudentMapperImpl();
	}

	@Bean
	public TeacherMapper teacherMapper(){
		return new TeacherMapperImpl();
	}



}
