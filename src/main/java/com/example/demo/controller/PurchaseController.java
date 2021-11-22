package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.PurchaseItem;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseItemRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.validator.InventoryValidator;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private PurchaseItemRepository purchaseItemRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private InventoryValidator inventoryValidator;
	
	@RequestMapping("/")
	public String index(Model model, 
						@RequestParam(name="supplier_id", required = false) Long supplier_id) {
		
		Purchase purchase = new Purchase();
		
		if(supplier_id == null) {
			model.addAttribute("purchases", purchaseRepository.findAll());
		} else {
			Supplier supplier = supplierRepository.findById(supplier_id).get();
			purchase.setSupplier(supplier);
			model.addAttribute("purchases", purchaseRepository.findBySupplier(supplier));
		}
		model.addAttribute("purchase", purchase);
		model.addAttribute("purchases", supplierRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "purchase";
	}
	
	@RequestMapping("/add")
	public String add(Purchase purchase) {
		purchaseRepository.save(purchase);
		return "redirect:/purchase/";
	}
	
	@RequestMapping("/edit/{oid}")
	public String edit(Model model, @PathVariable("pid") Long pid) {
		Purchase purchase = purchaseRepository.findById(pid).get();
		model.addAttribute("purchase", purchase);
		model.addAttribute("suppliers", supplierRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "purchase-update";
	}
	
	@RequestMapping("/update/{pid}")
	public String update(Purchase purchase, @PathVariable("pid") Long pid) {
		purchase.setId(pid); // 重要 ! 不加入會變成新增
		purchaseRepository.save(purchase);
		return "redirect:/purchase/";
	}
	
	@RequestMapping("/delete/{pid}")
	public String delete(@PathVariable("pid") Long pid) {
		purchaseRepository.deleteById(pid);
		return "redirect:/purchase/";
	}
	
}