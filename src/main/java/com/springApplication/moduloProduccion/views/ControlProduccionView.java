package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.models.EstadosProduccion;
import com.springApplication.moduloProduccion.models.Orden;
import com.springApplication.moduloProduccion.models.Proceso;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.Duration;
import java.time.LocalDateTime;

@Route(value = "controlProduccion", layout = MainLayout.class)
@PageTitle(value = "Control de producción")
public class ControlProduccionView extends VerticalLayout {

    private FormLayout panelOrdenes;
    private FormLayout panelAlta;
    private VerticalLayout panelCompleto;
    private HorizontalLayout panelBotonesCRUD;
    private VerticalLayout panelTabla;
    private TextField campoEncargado;
    private TextField campoEstatusOrden;
    private ComboBox<Orden> numeroOrden;
    private Button botonConsulta;
    private TextField campoNombre;
    private ComboBox<EstadosProduccion> selectorEstatus;
    private DateTimePicker fechaYHora;
    private Button[] botonesConsultaMaterialesPlanes;
    private Button[] botonesCRUD;
    private Tab crudTab;
    private Tab consultaTab;
    private Tabs menu;
    private Grid<Proceso> tablaControl;


    public ControlProduccionView(){

        initComponents();

    }

    private void initComponents(){

        crudTab = new Tab("CRUD Tab");
        consultaTab = new Tab("Consultas");
        menu = new Tabs();
        menu.add(crudTab, consultaTab);
        menu.addSelectedChangeListener(event -> setContentOnTab(event.getSelectedTab()));
        panelOrdenes = new FormLayout();
        panelOrdenes.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
        campoEncargado = new TextField("Encargado");
        numeroOrden = new ComboBox<>("Número de orden");
        campoEstatusOrden = new TextField("Estatus de la orden");
        botonConsulta = new Button("Consultar orden");
        panelOrdenes.add(campoEncargado, numeroOrden, campoEstatusOrden, botonConsulta);

        panelAlta = new FormLayout();
        panelAlta.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 5));
        campoNombre = new TextField("Nombre");
        selectorEstatus = new ComboBox<>("Estatus");
        selectorEstatus.setItems(EstadosProduccion.values());
        fechaYHora = new DateTimePicker();
        fechaYHora.setLabel("Fecha y hora");
        fechaYHora.setStep(Duration.ofMinutes(1));
        fechaYHora.setValue(LocalDateTime.now());
        botonesConsultaMaterialesPlanes = new Button[] {

                new Button("Consulta Materiales"),
                new Button("Consulta Planes Fabricación")

        };

        botonesCRUD = new Button[] {

                new Button("Guardar"),
                new Button("Cancelar"),
                new Button("PDF")

        };
        panelAlta.add(campoNombre, selectorEstatus, fechaYHora);
        panelAlta.add(botonesConsultaMaterialesPlanes);

        tablaControl = new Grid<>();
        panelTabla = new VerticalLayout();
        panelBotonesCRUD = new HorizontalLayout();
        panelBotonesCRUD.add(botonesCRUD);


        panelCompleto = new VerticalLayout();
        panelCompleto.add("Consulta");
        panelCompleto.add(panelOrdenes);
        panelCompleto.add("Alta");
        panelCompleto.add(panelAlta);
        panelCompleto.add(panelBotonesCRUD);

        add(menu);
        add(panelCompleto);
        add(panelTabla);


    }

    private void setContentOnTab(Tab tab) {

        panelCompleto.removeAll();
        panelTabla.removeAll();

        if(tab.equals(crudTab)){

            panelCompleto.add("Consulta");
            panelCompleto.add(panelOrdenes);
            panelCompleto.add("Alta");
            panelCompleto.add(panelAlta);
            panelCompleto.add(panelBotonesCRUD);


        }else if(tab.equals(consultaTab)){

            panelTabla.add(tablaControl);

        }

    }


}
