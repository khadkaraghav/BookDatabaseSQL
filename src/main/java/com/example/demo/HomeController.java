package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    BookRepository bookRepository;      // autowired - no need to create object of this and use in methods


    @RequestMapping("/")
    public String booklist(Model model){    // main homepage

        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }



    @GetMapping("/add")                              // adding the attributes and putting values to h2 database
    public String addBookForm(Model model){

        model.addAttribute("book", new Book());
        return "bookform";
    }


    @PostMapping("/process")                        // Validation operation
    public String processForm(@Valid Book book, BindingResult result/*, @RequestParam("select") String isAdult*/){

        /*if(isAdult.equalsIgnoreCase("yes")){
            book.setAdult(true);
        }
        else {
            book.setAdult(false);
        }*/

        if (result.hasErrors()){
            return "bookform";
        }

        bookRepository.save(book);
        return "redirect:/";
    }


    @RequestMapping("/detail/{id}")                             // display details
    public String showBook(@PathVariable("id") long id, Model model){

        model.addAttribute("book", bookRepository.findById(id).get());
        return "show";
    }


    @RequestMapping("/update/{id}")                             // update
    public String updateCourse(@PathVariable("id") long id, Model model){

        model.addAttribute("book", bookRepository.findById(id));
        return "bookform";
    }


    @RequestMapping("/delete/{id}")                             // update
    public String delCourse(@PathVariable("id") long id){

        bookRepository.deleteById(id);
        return "redirect:/";
    }
}