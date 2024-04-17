/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FunctionalProgrammingCustomclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Dell
 */
public class AnalyticsTrial {

    public static void main(String[] args) {

      List<Company_details> company_details_list=  List.of(
                new Company_details("4962", "AMERICAN EXPRESS CO", "10-k", "124", "LARGE ACCELERATED FILER", "US GAAP"),
              new Company_details("4962", "AMERICAN EXPRESS CO", "10-k", "124", "LARGE ACCELERATED FILER", "US GAAP"),
                new Company_details("12927", "BOEING CO", "10-k", "125", "LARGE ACCELERATED FILER", "US GAAP"),
                new Company_details("18230", "CATERPILLAR INC", "10-k", "126", "LARGE ACCELERATED FILER", "US GAAP"),
                new Company_details("19617", "JPMORGAN CHASE & CO", "10-k", "127", "LARGE ACCELERATED FILER", "US GAAP"),
                new Company_details("21344", "COCA COLA CO", "10-k", "128", "LARGE ACCELERATED FILER", "US GAAP"));

        System.out.println(
      company_details_list.stream()
              .collect(Collectors.groupingBy(Company_details::getForm_type, 
                      Collectors.counting())));
       
        
        Predicate<Company_details> isDuplicateName = cmp ->
                company_details_list.stream()                        
                        .filter(emp -> emp.getLd_doc_id().equals(cmp.getLd_doc_id()))
                        .count() > 1;
        
        
        
        System.out.println();
        
        company_details_list.stream()      
                .distinct()
              .filter(isDuplicateName.negate()).collect(Collectors.toList())
                .forEach(System.out::println);
        
//        System.out.println(
//      company_details_list.stream()              
//              .filter(isDuplicateName.negate()).collect(Collectors.toList()));
//              // Collectors.groupingBy(Company_details::getForm_type, 
//              //        Collectors.counting()
        
        
        
//        List<Employee> filteredEmployees = employees.stream()
//                .filter(emp -> emp.getDepartment().equals(departmentToFilter))
//                .collect(Collectors.collectingAndThen(
//                        Collectors.toCollection(() -> new ArrayList<>()),
//                        list -> list.stream()
//                                .filter(emp -> list.stream()
//                                        .filter(innerEmp -> innerEmp.getName().equals(emp.getName()))
//                                        .count() == 1)
//                                .collect(Collectors.toList())
//                ));
    }

}
