package dev.m7wq.aboutme.AboutMe.service;

import dev.m7wq.aboutme.AboutMe.model.AboutMe;
import dev.m7wq.aboutme.AboutMe.repository.AboutMeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AboutMeService {

    @Autowired
    private AboutMeRepository repository;

    public AboutMe get(String username){
        return repository.findById(username).orElse(null);
    }

    @Transactional
    public void set(AboutMe aboutMe){
        repository.save(aboutMe);
    }

    public boolean isExisted(String username){
        return repository.existsById(username);
    }


}
