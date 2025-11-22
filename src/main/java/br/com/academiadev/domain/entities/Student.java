package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.SubscriptionPlan;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private SubscriptionPlan subscriptionPlan;
    private List<Enrollment> enrollments = new ArrayList<>();
    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
    }

    public boolean canEnroll() {
        if (subscriptionPlan == SubscriptionPlan.PREMIUM) {
            return true;
        }

        long activeCourses = enrollments.stream()
                .filter(e -> e.getCourse().isActive()) 
                .count();
                
        return activeCourses < 3;
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }
    
    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
}