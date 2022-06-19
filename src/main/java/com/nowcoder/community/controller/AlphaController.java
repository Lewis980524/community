package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Printer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.*;

@Controller
@RequestMapping("/alpha")

//实际项目中，可能是controller调用service，service再调用dao（数据库）

//localhost端口等配置在application.properties中

public class AlphaController {

    @Autowired  //通过注入，使controller依赖于service
    private AlphaService alphaService;


    @RequestMapping("/hello")
    @ResponseBody
    public String sayhello(){
        return "Hello Ruby!";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){ //请求对象与响应对象
        //request是处理来自浏览器向服务器发出的请求的对象
        System.out.println(request.getMethod()); //获取请求方式
        System.out.println(request.getServletPath());  //获取请求的路径
        Enumeration<String> enumeration = request.getHeaderNames(); //获取所有的请求行的key（请求行是key-value结构）
        //getHeaderNames得到一个迭代器
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));//getParameter获得请求体，即传入的参数

        //response是处理从浏览器返回的响应的对象
        response.setContentType("text/html;charset=utf-8");//设置返回数据的类型：返回一个网页类型的文本，且支持中文
        //返回网页，即返回response里封装的流
        try (
                PrintWriter writer = response.getWriter(); //获得一个输出流，然后通过对这个流操作，继而对response产生信息输出的效果
                ){//java新语法，在try的（）里写语句，java自动在后面加finally

            writer.write("<h1>牛客网</h1>");//向网页输出“牛客网”
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //GET请求
    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET) //只接受GET方法
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /students/(id)
    @RequestMapping(path = "/students/(id)", method = RequestMethod.GET) //只接受GET方法
    @ResponseBody
    public String getStudents(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    //post
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", "30");
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        //类似上面方法，使用Model作为参数时，DispatcherServlet可以自动获取model的链接，
        //然后通过返回的string来传递这个model到网页
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view"; //这个demoview就是返回给DispatcherServlet，然后DS按model来修改demoview
    }

    //响应JSON数据（异步请求）
    //异步请求：比如注册账号，显示昵称被占用，此时和服务器有交互，但当前网页不刷新（服务器返回的结果
    //不是一个html，只是一个单纯的判断（是或否））

    //Java对象 -> JSON字符串 -> JS对象

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody //如果不加，会被以为是要返回一个html
    public Map<String, Object> getEmp(){
        Map<String, Object> emp =  new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", "20");
        emp.put("salary", "8000.00");
        return emp;  //DispatcherServlet在检测到ResponseBody并发现要返回Map类型后，
                    //会自动将emp转成JSON，再发给浏览器
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getEmps(){
        List<Map> list = new ArrayList<>();

        Map<String, Object> emp =  new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", "20");
        emp.put("salary", "8000.00");
        list.add(emp);

        emp =  new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", "20");
        emp.put("salary", "8000.00");
        list.add(emp);

        emp =  new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", "20");
        emp.put("salary", "8000.00");
        list.add(emp);

        return list;
    }


}
