package com.example.testproj.controllers;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MainController {

    @GetMapping("/")
    public String getMain() {
        return "main";
    }

    @GetMapping("/calculator")
    public String getCalc() {
        return "calculator";
    }

    @PostMapping("/calculator")
    public ModelAndView postCalc(@RequestParam("equation") String equation, Model model) {
        model.addAttribute("equation", equation);
        ModelAndView modelView = new ModelAndView("/calculator_result");

        Expression expression = new ExpressionBuilder(equation).build();
        double result = expression.evaluate();

        modelView.addObject("result", result);
        return modelView;
    }

    @GetMapping("/value_converter")
    public String getValueConv(@RequestParam(name = "value", defaultValue = "0") String value, Model model) {
        model.addAttribute("value", value);
        return "value_converter";
    }

    @PostMapping("/value_converter")
    public String postValueConv(@RequestParam(name = "value", defaultValue = "0") String value, @RequestParam("first") String first, @RequestParam("second") String second, RedirectAttributes redirectAttributes) {

        float num = Float.parseFloat(value);

        switch (first) {
            case "₽":
                switch (second) {
                    case "$":
                        num *= 0.01;
                        break;
                    case "€":
                        num *= 0.009;
                        break;
                }
                break;
            case "$":
                switch (second) {
                    case "₽":
                        num *= 97;
                        break;
                    case "€":
                        num *= 0.94;
                        break;
                }
                break;
            case "€":
                switch (second) {
                    case "₽":
                        num *= 102;
                        break;
                    case "$":
                        num *= 1.05;
                        break;
                }
                break;
        }
        redirectAttributes.addAttribute("value", num);
        return "redirect:/value_converter";
    }

}