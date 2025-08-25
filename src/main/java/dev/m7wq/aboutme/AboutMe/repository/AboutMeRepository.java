package dev.m7wq.aboutme.AboutMe.repository;

import dev.m7wq.aboutme.AboutMe.model.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutMeRepository extends JpaRepository<AboutMe, String> {
}
