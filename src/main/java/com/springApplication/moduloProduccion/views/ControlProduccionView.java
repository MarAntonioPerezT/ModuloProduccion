package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.models.EstadoOrden;
import com.springApplication.moduloProduccion.models.Orden;
import com.springApplication.moduloProduccion.models.Procesos;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.Duration;
import java.time.LocalDateTime;

@Route(value = "controlProduccion", layout = MainLayout.class)
@PageTitle(value = "Control de producción")
@RolesAllowed("ADMIN")

public class ControlProduccionView extends VerticalLayout {

    private HorizontalLayout layoutOrden;
    private HorizontalLayout layoutProcesos;
    private HorizontalLayout layoutBtn;
    private TextField encargado;
    private TextField estOrden;
    private TextField estatusOrden;

    private TextField nombre;
    private ComboBox<String> estatus;
    private TextField resp;
    private DateTimePicker fechaHora;
    private ComboBox<String> noOrden;

    private Button consultarOrden;
    private Button consultarMats;
    private Button consultarPlanos;
    private Button save;
    private Button cancel;
    private Button createPDF;

    public ControlProduccionView(){
        
        initComponents();
    }

    private void initComponents() {

        setLayoutBtn();
        setLayoutDatos();
        setLayoutProcesos();
        add(getLayoutBtn(), getLayoutDatos(), getLayoutProcesos());
    }

    private void setLayoutBtn() {
        layoutBtn = new HorizontalLayout();
        save = new Button("Guardar", new Icon(VaadinIcon.FOLDER));
        cancel = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE));
        createPDF = new Button("Generar PDF", new Icon(VaadinIcon.FILE_TEXT));
        layoutBtn.add(save, cancel, createPDF);
    }

    private void setLayoutDatos() {

        layoutOrden = new HorizontalLayout();
        encargado = new TextField("Encargado");
        noOrden = new ComboBox<>("Número de orden");
        estatusOrden = new TextField("Estatus de la orden");
        consultarOrden = new Button("Consultar orden");
        layoutOrden.setAlignItems(Alignment.END);
        layoutOrden.add(encargado, noOrden, estatusOrden, consultarOrden);
    }

    private void setLayoutProcesos() {

        layoutProcesos = new HorizontalLayout();
        nombre = new TextField("Nombre");
        nombre.setWidth("125px");
        estatus = new ComboBox<>("Estatus");
        estatus.setWidth("150px");
        fechaHora = new DateTimePicker("Fecha y Hora");
        fechaHora.setAutoOpen(true);
        fechaHora.setValue(LocalDateTime.now());
        fechaHora.setReadOnly(true);
        consultarMats = new Button("Ver Materiales");
        consultarPlanos = new Button("Ver Planos");
        resp = new TextField("Responsable");
        layoutProcesos.setAlignItems(Alignment.END);
        layoutProcesos.add(nombre, estatus, fechaHora, resp, consultarMats, consultarPlanos);
    }

    private HorizontalLayout getLayoutBtn() {
        return layoutBtn;
    }

    private HorizontalLayout getLayoutDatos() {
        return layoutOrden;
    }

    private HorizontalLayout getLayoutProcesos() {
        return layoutProcesos;
    }


}
