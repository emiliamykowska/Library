package edu.bi.springdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloController {

    @GetMapping("/hello") //endpoint
    public String sayHello(@RequestParam(defaultValue = "World") String name){

        return "Hello " + name + "!";
    }

    @GetMapping("/add")
    public Integer addNumbers(@RequestParam(defaultValue = "0") Integer a, @RequestParam(defaultValue = "0") Integer b){
        return a + b;
    }
    //RequestPArams are visible
    //@PostMapping used for login f.e., everything should be in the param, not visible or everyone

    @GetMapping("generate-random")
    public Integer generateRandom(@RequestParam(defaultValue = "0") Integer a, @RequestParam(defaultValue = "100") Integer b){

        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }
       Random rand = new Random();

       return rand.nextInt(a, b + 1);
    }
}
