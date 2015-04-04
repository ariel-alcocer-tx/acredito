package com.bo.acredito;

import com.bo.acredito.domain.Employee;
import com.bo.acredito.ui.forms.LoginForm;
import com.bo.acredito.ui.forms.SaleForm;
import com.bo.acredito.ui.lists.CustomerList;
import com.bo.acredito.ui.lists.ProductList;
import com.bo.acredito.util.Constants;
import com.bo.acredito.util.RefreshableTabComponent;
import com.bo.acredito.web.JEE6VaadinServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
    public static final String CUSTOMERS = "Clientes";
    public static final String PRODUCTS = "Productos";
    public static final String SALES = "Ventas";
    public static final String CHARGES = "Cobranzas";
    public static final String EXPENSES = "Egresos";
    public static final String SUPPORT = "Soporte";
    public static final String ADMIN = "Administración";

    private Employee employee;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.bo.acredito.AppWidgetSet")
    @PersistenceContext(name="persistence/em",unitName= Constants.PERSISTENCE_UNIT)
    public static class Servlet extends JEE6VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final com.bo.acredito.ui.forms.LoginForm loginForm = new LoginForm();
        setContent(loginForm);
    }

    public void buildUI()  {
        System.out.println("Welcome user:"+employee.getUsername());
        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        //contentLayout.setMargin(true);
        setContent(contentLayout);

        //Tabs Container
        TabSheet tabSheetMain = new TabSheet();
        tabSheetMain.setImmediate(true);
        tabSheetMain.setWidth("100%");
        tabSheetMain.setHeight("100%");

        //Customers
        CustomerList customerList=new CustomerList();
        tabSheetMain.addTab(customerList, CUSTOMERS, null);

        //Products
        ProductList productList = new ProductList();
        tabSheetMain.addTab(productList, PRODUCTS, null);


        //Sales
        SaleForm salesForm = new SaleForm();
        tabSheetMain.addTab(salesForm, SALES, null);

        //Charges
        Label chargesLabel = new Label(CHARGES+"... Not Yet Implemented");
        tabSheetMain.addTab(chargesLabel, CHARGES, null);

        //Expenses
        Label expensesLabel = new Label(EXPENSES+"... Not Yet Implemented");
        tabSheetMain.addTab(expensesLabel, EXPENSES, null);

        //Support
        Label supportLabel = new Label(SUPPORT+"... Not Yet Implemented");
        tabSheetMain.addTab(supportLabel, SUPPORT, null);

        //Admin
        Label adminLabel = new Label(ADMIN+"... Not Yet Implemented");
        tabSheetMain.addTab(adminLabel, ADMIN, null);

        contentLayout.addComponent(tabSheetMain);
        tabSheetMain.addSelectedTabChangeListener(
                new TabSheet.SelectedTabChangeListener() {
                    public void selectedTabChange(TabSheet.SelectedTabChangeEvent event){
                        TabSheet tabsheet = event.getTabSheet();
                        RefreshableTabComponent tab = (RefreshableTabComponent) tabsheet.getSelectedTab();
                        System.out.println("******************** "+tabsheet.getTab(tab).getCaption());
                        tab.paintComponent();
                    }
                });

    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
