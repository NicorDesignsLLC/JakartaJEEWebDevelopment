package com.nicordesigns.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController
{
    @RequestMapping("/")
    public View index()
    {
        return new RedirectView("/charity-springmvc/list", true, false);
    }
}
