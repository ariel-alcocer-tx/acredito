package com.bo.acredito.ui.lists;

import com.bo.acredito.domain.Supplier;
import com.bo.acredito.ui.forms.SupplierForm;
import com.bo.acredito.ui.util.ListTableUtil;
import com.bo.acredito.ui.util.ReportsUtil;
import com.bo.acredito.util.Constants;
import com.bo.acredito.util.RefreshableTabComponent;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import org.tepi.filtertable.paged.PagedFilterTable;

/**
 * Created by asejas on 29/03/15.
 */
public class SupplierList extends RefreshableTabComponent {
    PagedFilterTable<IndexedContainer> filterTable;
    private String[] tablePropertyIds={"code", "firstName", "lastName", "companyName", "nit"};
    private String[] tableHeaders={"Código", "Nombre", "Apellido", "Empresa", "Nit"};
    private JPAContainer container;
    private Button addButton;
    private Button generateReportButton;

    public SupplierList() {

        paintComponent();
    }

    @Override
    public void paintComponent() {
        container = JPAContainerFactory.make(Supplier.class, Constants.PERSISTENCE_UNIT);
        filterTable=ListTableUtil.buildPagedTable(tablePropertyIds, tableHeaders, filterTable, container);
        filterTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                Supplier selectedSupplier = ((JPAContainerItem<Supplier>)event.getItem()).getEntity();
                SupplierForm supplierForm = new SupplierForm("Editar proveedor", selectedSupplier.getId(), container);
                getUI().addWindow(supplierForm);
            }
        });
        addButton = new Button("Registrar nuevo proveedor",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        SupplierForm supplierForm=new SupplierForm("Nuevo proveedor", null, container);
                        getUI().addWindow(supplierForm);
                    }
                }
        );
        addButton.setDescription("Crea un nuevo proveedor");
        final AbstractOrderedLayout mainLayout=ListTableUtil.createListLayout("Lista de proveedores", addButton, filterTable);
        generateReportButton=new Button("Generar reporte");

        new ReportsUtil().prepareForPdfReport(container.getEntityProvider().getEntityManager(),
                "/reports/HelloWorldReport.jrxml",
                "ReporteProveedores",
                generateReportButton);

        mainLayout.addComponent(generateReportButton);
        setCompositionRoot(
                mainLayout
        );
    }

}
