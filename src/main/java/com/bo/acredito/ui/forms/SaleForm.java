package com.bo.acredito.ui.forms;

import com.bo.acredito.domain.*;
import com.bo.acredito.service.SaleService;
import com.bo.acredito.util.Constants;
import com.bo.acredito.web.JEE6VaadinServlet;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.Calendar;

/**
 * Created by aralco on 2/2/14.
 */
public class SaleForm extends CustomComponent {

    //left
    private ComboBox customersComboBox;
    private ComboBox productsComboBox;
    private TextField productPriceTextField;
    private TextField discountedAmountTextField;
    private TextField totalTextField;
    private ComboBox saleTypeComboBox;
    private TextField initialPaymentTextField;
    private TextField residualPaymentTextField;
    private TextArea notesTextArea;
    private Label paymentPlanLabel;
    private TextField paymentQuotesTextField;
    private final Button viewPaymentPlanButton;
    private final CheckBox modifyPaymentPlanCheckBox;
    private final Table paymentTable;
    private final Button saveButton;
    private final Button cancelButton;
    private String [] paymentTableHeaders;
    //layouts
    private HorizontalLayout paymentPlanFormLayout;


    public SaleForm() {

        productPriceTextField = new TextField("Monto $us:");
        discountedAmountTextField = new TextField("Descuento $us:");
        totalTextField = new TextField("Total $us:");
        saleTypeComboBox = new ComboBox("Forma de pago:", Arrays.asList(SaleTypeEnum.values()));
        initialPaymentTextField = new TextField("Cuota inicial $us:");
        residualPaymentTextField = new TextField("Saldo a crédito $us:");
        notesTextArea = new TextArea("Observaciones: ");
        paymentPlanLabel = new Label("Total a pagar: ");
        paymentPlanFormLayout = new HorizontalLayout();
        paymentQuotesTextField = new TextField("Número de cuotas: ");
        modifyPaymentPlanCheckBox = new CheckBox("Modificar plan de pagos");
        paymentTable = new Table(null);
        viewPaymentPlanButton = new Button("Ver");
        saveButton   = new Button("Guardar");
        cancelButton   = new Button("Cancelar");
        paymentTableHeaders = new String[]{"Número", "Fecha", "Monto $us"};


        Panel salesPanel = new Panel("Registro de Venta");
        salesPanel.setSizeFull();

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);

        GridLayout gridLayout = new GridLayout(2,1);
        gridLayout.setSizeFull();

        final FormLayout leftFormLayout = new FormLayout();
        leftFormLayout.setSpacing(true);
        //Load customers
        final JPAContainer<Customer> customers = JPAContainerFactory.make(Customer.class, Constants.PERSISTENCE_UNIT);
        customersComboBox = new ComboBox("Cliente:", customers);
        customersComboBox.setItemCaptionPropertyId("codeName");
        customersComboBox.setImmediate(true);
        //Load products
        final JPAContainer<Product> products = JPAContainerFactory.make(Product.class, Constants.PERSISTENCE_UNIT);
        productsComboBox = new ComboBox("Producto:", products);
        productsComboBox.setItemCaptionPropertyId("codeName");
        productsComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                productPriceTextField.setReadOnly(false);
                String price = products.getItem(productsComboBox.getValue()).getEntity().getPrice().toString();
                productPriceTextField.setValue(price);
                productPriceTextField.setReadOnly(true);
                totalTextField.setReadOnly(false);
                totalTextField.setValue(price);
            }
        });
        productsComboBox.setImmediate(true);
        discountedAmountTextField.setValue("0.00");
        discountedAmountTextField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
                totalTextField.setReadOnly(false);
                if (productPriceTextField.getValue() != null) {
                    BigDecimal st = new BigDecimal(productPriceTextField.getValue());
                    BigDecimal da = new BigDecimal(textChangeEvent.getText());
                    totalTextField.setValue(st.subtract(da).toString());
                    totalTextField.setReadOnly(true);
                }
            }
        });
        discountedAmountTextField.setImmediate(true);
        leftFormLayout.addComponents(customersComboBox, productsComboBox, productPriceTextField, discountedAmountTextField, totalTextField, saleTypeComboBox, initialPaymentTextField, residualPaymentTextField, notesTextArea);
        initialPaymentTextField.setVisible(false);
        residualPaymentTextField.setVisible(false);
        saleTypeComboBox.setNullSelectionAllowed(false);
        saleTypeComboBox.setValue(SaleTypeEnum.CASH);
        saleTypeComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (saleTypeComboBox.getValue().equals(SaleTypeEnum.CREDIT)) {
                    initialPaymentTextField.setVisible(true);
                    residualPaymentTextField.setVisible(true);
                    paymentPlanLabel.setValue("Plan de pagos $us");
                    modifyPaymentPlanCheckBox.setVisible(true);
                    paymentPlanFormLayout.setVisible(true);
                } else {
                    initialPaymentTextField.setVisible(false);
                    residualPaymentTextField.setVisible(false);
                    paymentPlanLabel.setValue("Total a pagar $us");
                    modifyPaymentPlanCheckBox.setVisible(false);
                    paymentPlanFormLayout.setVisible(false);
                }
            }
        });
        saleTypeComboBox.setImmediate(true);
        //leftFormLayout.setSizeFull();
        gridLayout.addComponent(leftFormLayout,0,0);



        FormLayout rightFormLayout = new FormLayout();
        rightFormLayout.setSpacing(true);
        final JPAContainer container = JPAContainerFactory.makeBatchable(Payment.class, Constants.PERSISTENCE_UNIT);

        //paymentTable = new Table(null, container);
        paymentTable.setContainerDataSource(container);
        Payment p1 = new Payment();
        p1.setPaymentNumber(1);
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Calendar.getInstance().getTime();
        p1.setDueDate(date);
        p1.setAmountDue(Double.valueOf(100));
        container.addEntity(p1);
        Payment p2 = new Payment();
        p2.setPaymentNumber(2);
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date = Calendar.getInstance().getTime();
        p2.setDueDate(date);
        p2.setAmountDue(Double.valueOf(1000));
        container.addEntity(p2);
        final List<Payment> paymentList = new ArrayList<Payment>(2);
        paymentList.add(p1);
        paymentList.add(p2);

        paymentTable.setSelectable(true);
        paymentTable.setVisibleColumns("paymentNumber", "dueDate", "amountDue");
        paymentTable.setImmediate(true);
        paymentTable.setColumnHeaders(paymentTableHeaders);
        paymentTable.setFooterVisible(true);
        paymentTable.setColumnFooter("dueDate", "Total $us");
        Double totalAmount = 0.00;
        paymentTable.setColumnFooter("amountDue", String.valueOf(totalAmount));

        modifyPaymentPlanCheckBox.addValueChangeListener(
                new Property.ValueChangeListener() {
                    public void valueChange(Property.ValueChangeEvent event) {
                        paymentTable.setEditable(((Boolean) event.getProperty()
                                .getValue()).booleanValue());
                    }
                });
        modifyPaymentPlanCheckBox.setImmediate(true);


        rightFormLayout.addComponent(paymentPlanLabel);
        FormLayout paymentPlanFormLayout1 = new FormLayout(paymentQuotesTextField);
        paymentPlanFormLayout.addComponents(paymentPlanFormLayout1, viewPaymentPlanButton);
        paymentPlanFormLayout.setSpacing(true);
        rightFormLayout.setSpacing(true);
        rightFormLayout.addComponent(paymentPlanFormLayout);
        rightFormLayout.addComponent(modifyPaymentPlanCheckBox);
        rightFormLayout.addComponent(paymentTable);
        //rightFormLayout.setSizeFull();
        gridLayout.addComponent(rightFormLayout,1,0);


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        saveButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                //container.commit();
                SaleService saleService=((JEE6VaadinServlet) VaadinServlet.getCurrent()).getSaleService();
                //saleService.savePayments(paymentList);
                final Sale sale = new Sale();
                sale.setCode(Long.valueOf(1));
                sale.setDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
                sale.setProductPrice(Double.valueOf(productPriceTextField.getValue()));
                sale.setDiscountedAmount(Double.valueOf(discountedAmountTextField.getValue()));
                sale.setTotal(Double.valueOf(totalTextField.getValue()));
                sale.setSaleType((SaleTypeEnum) saleTypeComboBox.getValue());
                sale.setInitialPayment(Double.valueOf(initialPaymentTextField.getValue()));
                sale.setResidualPayment(Double.valueOf(residualPaymentTextField.getValue()));
                sale.setPaymentQuotes(Integer.valueOf(paymentQuotesTextField.getValue()));
                sale.setNotes(notesTextArea.getValue());
                JPAContainer<Employee> employeeJPAContainer= JPAContainerFactory.make(Employee.class, Constants.PERSISTENCE_UNIT);
                EntityItem<Employee> entityItem = employeeJPAContainer.getItem(Long.valueOf(1));
                sale.setEmployee(entityItem.getEntity());
                sale.setCustomer(customers.getItem(customersComboBox.getValue()).getEntity());
                sale.setProduct(products.getItem(productsComboBox.getValue()).getEntity());
                saleService.saveSale(sale);
                Notification.show("GUARDADO", "Venta registrada con éxito", Notification.Type.HUMANIZED_MESSAGE);
            }
        });
        horizontalLayout.addComponents(saveButton, cancelButton);
        horizontalLayout.setSpacing(true);

//        verticalLayout.addComponent(gridLayout);
//        verticalLayout.addComponent(horizontalLayout);
//
//        salesPanel.setContent(verticalLayout);

//        setCompositionRoot(salesPanel);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.addComponents(gridLayout, horizontalLayout);
        salesPanel.setContent(formLayout);

        mainLayout.addComponent(salesPanel);

        setCompositionRoot(mainLayout);
        //setSizeFull();
    }
}