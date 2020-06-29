package com.javi.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.javi.hibernate.demo.entity.Course;
import com.javi.hibernate.demo.entity.Instructor;
import com.javi.hibernate.demo.entity.InstructorDetail;
import com.javi.hibernate.demo.entity.Student;

public class FetchJoinDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			
			// start a transaction
			session.beginTransaction();
			
			//Option 2: Hibernate query with HQL
			
			// get the instructor from db
			int theId = 1;
			
			//When executed, will load instructor and courses all at once
			Query<Instructor> query =
					session.createQuery("select i from Instructor i " 
								+ "JOIN FETCH i.courses "
								+ "where i.id=:theInstructorId", 
							Instructor.class );
			
			// set parameter on query
			query.setParameter("theInstructorId", theId);
			
			//execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();//Load instructor and courses all at once
			
			System.out.println("luv2code: Instructor: " + tempInstructor);
			
			
			// commit transaction
			session.getTransaction().commit();
			
			//close the session
			session.close();
			
			System.out.println("\nluv2code: The session is now closed!\n");
			
		
			
			// get courses for the instructor
			
			System.out.println("luv2code: Courses: " + tempInstructor.getCourses());//Lazy fetch. Courses only loaded on demand
			
			System.out.println("luv2code: Done!");
			
		}
		
		finally {
			
			// add clean up code
			
			session.close();
			
			factory.close();
		}

	}

}
