package com.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.POJO.Employee;
import com.Service.EmployeeService;
//import com.POJO.Department;
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/viewEmployee")
	public String findAll(Model theModel)
	{
	
		theModel.addAttribute("employee", employeeService.findAll());
		return "ViewEmployee";
	}
	
	@GetMapping("/edit")
	public String findById(@RequestParam("id") int theId,Model theModel)
	{
		Employee theEmployee=employeeService.findById(theId);
		theModel.addAttribute("employee",theEmployee );
		return "AddEmployee";
	}
	
	
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("employee") Employee theEmployee,Model theModel
			,@RequestParam("id") int theId,BindingResult theBindingResult)
	{
		System.out.println(theId);
		if(theId!=0)
		{
			Employee theemployee=employeeService.findById(theId);
			theemployee.setUsername(theemployee.getUsername());
			theemployee.setDepartment(theemployee.getDepartment());
			theemployee.setFull_name(theemployee.getFull_name());
			theemployee.setEmail(theemployee.getEmail());
			theemployee.setDesignation(theemployee.getDesignation());
			theemployee.setReporting(theemployee.getReporting());
			theemployee.setPhone_number(theemployee.getPhone_number());
			
		}
		System.out.println(theId);
		if(theBindingResult.hasErrors())
		{
			return "AddEmployee";
		}
		else
		{
		employeeService.save(theEmployee);
		
        return "redirect:/employee/viewEmployee";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int theId)
	{
		employeeService.deleteById(theId);
		return "redirect:/employee/viewEmployee";
	}
	
	@GetMapping("/add")
	public String addDepartment(Model theModel)
	{
		theModel.addAttribute("employee", new Employee());
		return "AddEmployee";
	}
	
	//For Employee
	@GetMapping("/editEmp")
	public String findByUsername(Model theModel,HttpServletRequest request)
	{
		String username=(String) request.getSession().getAttribute("username");
		Employee theEmployee=employeeService.findByUsername(username);
		System.out.println(theEmployee+" "+username);
		theModel.addAttribute("employee",theEmployee );
		//return "redirect:/employee/re-editEmp";
		return "AddEmployee";
	}
	
/*	@GetMapping("/editEmp")
	public String employeeEdit(Model theModel)
	{
		theModel.addAttribute("employee",new Employee());
		return "redirect:/employee/re-editEmp";
	}*/
}
