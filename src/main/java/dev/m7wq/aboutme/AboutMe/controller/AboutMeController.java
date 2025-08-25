package dev.m7wq.aboutme.AboutMe.controller;

import dev.m7wq.aboutme.AboutMe.model.AboutMe;
import dev.m7wq.aboutme.AboutMe.model.Field;
import dev.m7wq.aboutme.AboutMe.builder.HtmlBuilder;
import dev.m7wq.aboutme.AboutMe.service.AboutMeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class AboutMeController {

    @Autowired
    private AboutMeService service;

    @GetMapping("/")
    public String menu(){
        return "index";
    }

    @GetMapping("/form")
    public String form(){
        return "form";
    }

    @GetMapping("/editor/remover")
    public String remover(){
        return "remover";
    }

    @PostMapping("/editor/remover")
    public ResponseEntity<String> remove(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(name = "field-head") String fieldHead

    ){

        AboutMe aboutMe = service.get(username);

        if (aboutMe == null){

            HtmlBuilder builder = new HtmlBuilder()
                    .h(2, "There's no AboutMe for the user " + username).br().br().appendBackButton();

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(builder.toString());
        }

        if (!aboutMe.getPassword().equals(password)){
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(new HtmlBuilder().h(2, "The entered password is invalid").br().appendBackButton().toString());
        }

        if (aboutMe.getFields().stream().noneMatch(field -> field.getHead().equals(fieldHead))){
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(new HtmlBuilder().h(2, "There was no field found for this head: %s".formatted(fieldHead)).br().appendBackButton().toString());
        }

        aboutMe.getFields().removeIf(field -> field.getHead().equals(fieldHead));
        service.set(aboutMe);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(new HtmlBuilder().h(2, "The field has removed successfully!").br().appendBackButton().toString());
    }

    @PostMapping("/editor")
    public ResponseEntity<String> append(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String head,
            @RequestParam String description

    ){

        AboutMe aboutMe = service.get(username);

        if (aboutMe == null){

            HtmlBuilder builder = new HtmlBuilder()
                    .h(2, "There's no AboutMe for the user " + username).br().br().appendBackButton();

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(builder.toString());
        }

        if (!aboutMe.getPassword().equals(password)){
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(new HtmlBuilder().h(2, "The entered password is invalid").br().appendBackButton().toString());
        }

        aboutMe.getFields().add(
                Field.builder()
                        .head(head)
                        .description(description)
                        .build()
        );

        service.set(aboutMe);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(new HtmlBuilder().h(2, "The field has been appended successfully!").br().appendBackButton().toString());
    }

    @GetMapping("/editor")
    public String editor(){
        return "editor";
    }

    @GetMapping("/search")
    public String search(@RequestParam String username){
        return "redirect:/aboutme/" + username;
    }

    @GetMapping("/aboutme/{username}")
    public ResponseEntity<String> view(@PathVariable String username){
        AboutMe aboutMe = service.get(username);
        if (aboutMe == null){
            HtmlBuilder builder = new HtmlBuilder()
                    .h(2, "There's no AboutMe for the user " + username);
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(builder.toString());
        }

        HtmlBuilder builder = new HtmlBuilder();

        builder.h(1, "%s About Me".formatted(username)).br();

        for (Field field : aboutMe.getFields()){
            builder.b().span(field.getHead()).br()
                    .pre(field.getDescription());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(builder.toString());
    }

    @PostMapping("insert")
    public ResponseEntity<String> insert(
            @RequestParam String username,
            @RequestParam String password
    ){

        if (service.isExisted(username)) {
            HtmlBuilder builder = new HtmlBuilder()
                    .h(2, "This username is already been taken!")
                    .span("Username: " + username)
                    .appendBackButton();

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(builder.toString());
        }

        AboutMe aboutMe = AboutMe.builder()
                .password(password)
                .username(username)
                .fields(new ArrayList<>())
                .build();

        service.set(aboutMe);

        HtmlBuilder builder = new HtmlBuilder()
                .h(2, "Data Inserted!")
                .span("Username: " + username)
                .appendBackButton();

        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(builder.toString());
    }
}
