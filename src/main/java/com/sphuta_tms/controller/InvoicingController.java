package com.sphuta_tms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvoicingController {

    @GetMapping("/invoicing-form")
    public String invoicingForm() {
        return "invoicing-form"; // maps to resources/templates/invoicing-form.html
    }
}
