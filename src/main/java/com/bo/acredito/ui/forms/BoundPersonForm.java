package com.bo.acredito.ui.forms;

//import com.bo.acredito.domain.CountryEnum;
//import com.bo.acredito.domain.IdTypeEnum;
//import com.bo.acredito.domain.Person;
//import com.bo.acredito.service.person.PersonService;
//import com.bo.acredito.web.JEE6VaadinServlet;
//import com.vaadin.addon.jpacontainer.JPAContainerItem;
//import com.vaadin.data.Item;
//import com.vaadin.data.fieldgroup.BeanFieldGroup;
//import com.vaadin.data.fieldgroup.FieldGroup;
//import com.vaadin.data.fieldgroup.PropertyId;
//import com.vaadin.data.util.BeanItem;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.ui.*;
//import com.vaadin.ui.themes.Reindeer;

import java.util.Arrays;

/**
 * Created by asejas on 2/1/14.
 */
public class BoundPersonForm {//extends FormLayout {

//    private TextField firstName = new TextField("Nombres: ");
//    private TextField lastName = new TextField("Apellidos: ");
//    private TextField salutation= new TextField("Saludo: ");
//    private ComboBox idType=new ComboBox("Tipo de identificación: ", Arrays.asList(IdTypeEnum.values()));
//    private TextField idNumber=new TextField("Identificación: ");
//    private ComboBox country=new ComboBox("País de nacimiento: ", Arrays.asList(CountryEnum.values()));
//    private TextField city=new TextField("Ciudad de nacimiento: ");
//    private DateField birthday=new DateField("Fecha de nacimiento: ");
//    private TextArea notes=new TextArea("Observaciones: ");
//    private Button saveButton;
//
//
//    private FieldGroup fieldGroup;
//
//    public BoundPersonForm(Item item) {
//        setSizeUndefined();
//        setMargin(true);
//        addComponent(firstName);
//        addComponent(lastName);
//        addComponent(salutation);
//        addComponent(idType);
//        addComponent(idNumber);
//        addComponent(country);
//        addComponent(city);
//        addComponent(birthday);
//        addComponent(notes);
//
//        fieldGroup = new BeanFieldGroup<Person>(Person.class);
//        if(item!=null){
//            JPAContainerItem<Person> jpaItem=((JPAContainerItem<Person>) item);
//            fieldGroup.setItemDataSource(new BeanItem<Person>(jpaItem.getEntity()));
//        }
//        else{
//            BeanItem<Person> beanItem=new BeanItem<Person>(new Person());
//            fieldGroup.setItemDataSource(beanItem);
//        }
//        fieldGroup.bindMemberFields(this);
//
//        saveButton = new Button("Save", new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                try {
//                    fieldGroup.commit();
//                } catch (FieldGroup.CommitException e) {
//                    e.printStackTrace();
//                }
//                PersonService personService=((JEE6VaadinServlet) VaadinServlet.getCurrent()).getPersonService();
//                Person person = ((BeanItem<Person>) fieldGroup.getItemDataSource()).getBean();
//                if(person.getId()==null){
//                    personService.savePerson(person);
//                }
//                else{
//                    personService.upatePerson(person);
//                }
//
//            }
//        });
//        saveButton.setStyleName(Reindeer.BUTTON_DEFAULT);
//        addComponent(saveButton);
//
//
//
//    }
}
