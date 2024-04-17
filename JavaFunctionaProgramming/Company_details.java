/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FunctionalProgrammingCustomclass;

/**
 *
 * @author Dell
 */
public class Company_details {
    
    private String cik_Code;
    private String company_name;
    private String form_type;
    private String ld_doc_id;
    private String filer_type;
    private String Accounting;

    public Company_details(String cik_Code, String company_name, String form_type, String ld_doc_id, String filer_type, String Accounting) {
        this.cik_Code = cik_Code;
        this.company_name = company_name;
        this.form_type = form_type;
        this.ld_doc_id = ld_doc_id;
        this.filer_type = filer_type;
        this.Accounting = Accounting;
    }

    public String getCik_Code() {
        return cik_Code;
    }

    public void setCik_Code(String cik_Code) {
        this.cik_Code = cik_Code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }

    public String getLd_doc_id() {
        return ld_doc_id;
    }

    public void setLd_doc_id(String ld_doc_id) {
        this.ld_doc_id = ld_doc_id;
    }

    public String getFiler_type() {
        return filer_type;
    }

    public void setFiler_type(String filer_type) {
        this.filer_type = filer_type;
    }

    public String getAccounting() {
        return Accounting;
    }

    public void setAccounting(String Accounting) {
        this.Accounting = Accounting;
    }

    @Override
    public String toString() {
        return  cik_Code + " : " + company_name + " : " + form_type + " : " + ld_doc_id + " : " + filer_type + " : " + Accounting ;
    }
    
    
    
}
