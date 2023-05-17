package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.models.*;
import com.springApplication.moduloProduccion.services.*;
import com.springApplication.moduloProduccion.util.exceptions.ClientesNotFoundException;
import com.springApplication.moduloProduccion.util.exceptions.EstadosOrdenNotFoundException;
import com.springApplication.moduloProduccion.util.exceptions.ProcesosNotFoundException;
import com.springApplication.moduloProduccion.util.exceptions.ProductosNotFoundException;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@PageTitle("Órdenes de Producción")
@Route(value = "ordenesProduccion", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OrdenesProduccionView extends VerticalLayout {
    private TextField numeroProduccion;
    private TextField estado;
    private TextField usuario;
    private ComboBox<String> cliente;
    private DatePicker fechaEstimadaInicio;
    private DatePicker fechaEstimadaFinal;
    private DatePicker fechaRealInicio;
    private DatePicker fechaRealFinal;
    private Button[] botonesCRUD;
    private Button[] botonesOperativos;
    private Tab ordenesTab;
    private Tab consultarOrdenesTab;
    private Tabs menu;
    private Grid<Orden> tablaOrdenes;
    private FormLayout panelOrdenes;
    private HorizontalLayout panelBotonesOperativos;
    private HorizontalLayout panelBotonesCrud;
    private VerticalLayout panelTabla;
    private FormLayout panelProductos;
    @Autowired
    private ClientesService clientesService;
    @Autowired
    private EstadoOrdenService estadoOrdenService;
    @Autowired
    private OrdenService ordenService;
    @Autowired
    private ProductosService productosService;
    @Autowired
    private ProcesoService procesoService;
    @Autowired
    private DetalleOrdenService detalleOrdenService;
    @Autowired
    private PersonaClienteService personaClienteService;
    @Autowired
    private PersonasFisicasClientesService personasFisicasClientesService;
    @Autowired
    private PersonasMoralesClientesService personasMoralesClientesService;
    @Autowired
    private EstadoProcesoService estadoProcesoService;


    public OrdenesProduccionView(ClientesService clientesService, EstadoOrdenService estadoOrdenService, OrdenService ordenService,
                                 ProductosService productosService, ProcesoService procesoService, DetalleOrdenService detalleOrdenService,
                                 PersonaClienteService personaClienteService, PersonasFisicasClientesService personasFisicasClientesService,
                                 PersonasMoralesClientesService personasMoralesClientesService, EstadoProcesoService estadoProcesoService){

        this.clientesService = clientesService;
        this.estadoOrdenService = estadoOrdenService;
        this.ordenService = ordenService;
        this.productosService = productosService;
        this.procesoService = procesoService;
        this.detalleOrdenService = detalleOrdenService;
        this.personaClienteService = personaClienteService;
        this.personasFisicasClientesService = personasFisicasClientesService;
        this.personasMoralesClientesService = personasMoralesClientesService;
        this.estadoProcesoService = estadoProcesoService;

        initComponents();

    }

    private void initComponents() {

        setMenu();
        setPanelBotonesCrud();
        setPanelOrdenes();
        setPanelProductos();
        setPanelBotonesOperativos();
        setPanelTabla();
        add(getMenu());
        add(getPanelBotonesCrud());
        add(getPanelOrdenes());
        add(getPanelProductos());
        add(getPanelBotonesOperativos());
        desactivarComponentes(this.getChildren().toList());
    }

    private void setMenu(){

        menu = new Tabs();
        ordenesTab = new Tab("Órdenes de producción");
        consultarOrdenesTab = new Tab("Consultar órdenes");
        menu.add(ordenesTab, consultarOrdenesTab);
        menu.addSelectedChangeListener(new MenuListener());

    }

    private Tabs getMenu(){

        return menu;

    }

    private void setContentOnTabs(Tab tabs){

        remove(getPanelBotonesCrud(), getPanelOrdenes(), getPanelProductos(), getPanelBotonesOperativos(), getPanelTabla());

        if (tabs.equals(ordenesTab)) {

            add(getPanelBotonesCrud(), getPanelOrdenes(), getPanelProductos(), getPanelBotonesOperativos());

        } else if (tabs.equals(consultarOrdenesTab)) {

            add(getPanelTabla());

        }

    }

    private void setPanelBotonesCrud(){

        panelBotonesCrud = new HorizontalLayout();
        botonesCRUD = new Button[]{
                new Button("Añadir orden"),
                new Button("Añadir producto"),
                new Button("Eliminar Producto"),
        };

        botonesCRUD[0].addClickListener(new AnadirOrdenListener());
        botonesCRUD[1].addClickListener(new AnadirProductoListener());
        botonesCRUD[2].addClickListener(new BorrarProductoListener());
        panelBotonesCrud.add(botonesCRUD);

    }

    private HorizontalLayout getPanelBotonesCrud(){

        return panelBotonesCrud;

    }

    private void setPanelBotonesOperativos(){

        panelBotonesOperativos = new HorizontalLayout();
        botonesOperativos = new Button[]{
                new Button("Guardar"),
                new Button("Limpiar"),
                new Button("Generar PDF")
        };
        botonesOperativos[0].addClickListener(new GuardarOrdenListener());
        botonesOperativos[1].addClickListener(new BotonLimpiarListener());
        panelBotonesOperativos.add(botonesOperativos);

    }

    public HorizontalLayout getPanelBotonesOperativos() {
        return panelBotonesOperativos;
    }

    private void desactivarComponentes(List<Component> components){

        for (Component listaComponentes: components){

            if (listaComponentes instanceof HorizontalLayout && listaComponentes.equals(getPanelBotonesCrud())){

                listaComponentes.getElement().getChild(0).setEnabled(true);
                listaComponentes.getElement().getChild(1).setEnabled(false);
                listaComponentes.getElement().getChild(2).setEnabled(false);

            } else if (listaComponentes instanceof HorizontalLayout && listaComponentes.equals(getPanelBotonesOperativos())){

                ((HorizontalLayout) listaComponentes).setEnabled(false);

            } else if (listaComponentes instanceof FormLayout && listaComponentes.equals(getPanelOrdenes())){

                for (Component listaComponentesPanelOrdenes: listaComponentes.getChildren().toList()){

                    if (listaComponentesPanelOrdenes instanceof TextField){

                        ((TextField) listaComponentesPanelOrdenes).setEnabled(false);
                        ((TextField) listaComponentesPanelOrdenes).setValue("");

                    } else if (listaComponentesPanelOrdenes instanceof DatePicker){

                        ((DatePicker) listaComponentesPanelOrdenes).setEnabled(false);

                    } else if (listaComponentesPanelOrdenes instanceof ComboBox<?>){

                        ((ComboBox<?>) listaComponentesPanelOrdenes).setEnabled(false);
                    }
                }
            } else if (listaComponentes instanceof FormLayout && listaComponentes.equals(getPanelProductos())) {

                ((FormLayout) listaComponentes).setEnabled(false);

                if (getPanelProductos().getElement().getChildCount() > 4){

                    for (int i = getPanelProductos().getElement().getChildCount() - 1; i >= 4 ; i--){

                        getPanelProductos().getElement().removeChild(i);

                    }
                }
            }
        }
        limpiarComponentes();
    }

    private void activarComponentes(List<Component> components){

        getPanelProductos().setEnabled(true);
        for (Component listaComponentes: components) {

            if (listaComponentes instanceof HorizontalLayout && listaComponentes.equals(getPanelBotonesCrud())) {

                listaComponentes.getElement().getChild(0).setEnabled(false);
                listaComponentes.getElement().getChild(1).setEnabled(true);
                listaComponentes.getElement().getChild(2).setEnabled(true);

            } else if (listaComponentes instanceof HorizontalLayout && listaComponentes.equals(getPanelBotonesOperativos())) {

                ((HorizontalLayout) listaComponentes).setEnabled(true);

            } else if (listaComponentes instanceof FormLayout && listaComponentes.equals(getPanelOrdenes())) {

                for (Component listaComponentesPanelOrdenes : listaComponentes.getChildren().toList()) {

                    if (listaComponentesPanelOrdenes instanceof TextField){

                        ((TextField) listaComponentesPanelOrdenes).setEnabled(true);

                    }

                    if (listaComponentesPanelOrdenes instanceof TextField && listaComponentesPanelOrdenes.equals(numeroProduccion)) {

                        ((TextField) listaComponentesPanelOrdenes).setEnabled(false);
                        ((TextField) listaComponentesPanelOrdenes).setValue(String.valueOf(ordenService.contarOrdenes() + 1));

                    } else if (listaComponentesPanelOrdenes instanceof TextField && listaComponentesPanelOrdenes.equals(estado)){

                        ((TextField) listaComponentesPanelOrdenes).setEnabled(false);
                        ((TextField) listaComponentesPanelOrdenes).setValue(estadoOrdenService.findEstatusNombreByNombre("En Espera"));

                    } else if (listaComponentesPanelOrdenes instanceof TextField && listaComponentesPanelOrdenes.equals(usuario)){

                        ((TextField) listaComponentesPanelOrdenes).setEnabled(false);
                        ((TextField) listaComponentesPanelOrdenes).setValue("Eduardo");

                    } else if (listaComponentesPanelOrdenes instanceof ComboBox<?>){

                        ((ComboBox<?>) listaComponentesPanelOrdenes).setEnabled(true);

                    } else if (listaComponentesPanelOrdenes instanceof DatePicker) {

                        ((DatePicker) listaComponentesPanelOrdenes).setEnabled(true);

                    } else if (listaComponentesPanelOrdenes instanceof MultiSelectComboBox<?>){

                        ((MultiSelectComboBox<?>) listaComponentesPanelOrdenes).setEnabled(true);

                    }
                }
            }
        }
    }
    private void limpiarComponentes(){

        cliente.setValue(null);
        fechaEstimadaInicio.setValue(null);
        fechaEstimadaFinal.setValue(null);
        fechaRealFinal.setValue(null);
        fechaRealInicio.setValue(null);

        for (Component listaComponentesPanelProductos: getPanelProductos().getChildren().toList()){

            if (listaComponentesPanelProductos instanceof ComboBox<?>){

                ((ComboBox<?>) listaComponentesPanelProductos).setValue(null);
            }
            if (listaComponentesPanelProductos instanceof IntegerField){

                ((IntegerField) listaComponentesPanelProductos).setValue(null);
                ((IntegerField) listaComponentesPanelProductos).setInvalid(false);

            }

            if (listaComponentesPanelProductos instanceof TextArea){

                ((TextArea) listaComponentesPanelProductos).setValue("");

            }

            if (listaComponentesPanelProductos instanceof MultiSelectComboBox<?>){

                ((MultiSelectComboBox<?>) listaComponentesPanelProductos).deselectAll();

            }
        }
    }


    private void setPanelProductos(){

        panelProductos = new FormLayout();
        ComboBox<String> nuevoComboBox = new ComboBox<>("Seleccionar Productos");
        nuevoComboBox.setItems(new ListDataProvider<>(productosService.findAllProductosByNombre()));
        IntegerField nuevoIntegerField = new IntegerField("Cantidad");
        nuevoIntegerField.setMin(1);
        nuevoIntegerField.setMax(1000);
        nuevoIntegerField.setStepButtonsVisible(true);
        TextArea textArea = new TextArea("Especificaciones");
        textArea.setMaxLength(200);
        MultiSelectComboBox<String> procesos = new MultiSelectComboBox<>("Seleccione procesos");
        procesos.setItems(new ListDataProvider<>(procesoService.findAllProcesosByNombre()));
        panelProductos.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
        panelProductos.add(nuevoComboBox, nuevoIntegerField, textArea, procesos);

    }

    private FormLayout getPanelProductos(){

        return panelProductos;

    }

    private void setPanelOrdenes(){

        panelOrdenes = new FormLayout();
        panelOrdenes.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
        panelOrdenes.addClassName("ordenes");
        estado = new TextField("Estado");
        usuario = new TextField("Usuario");
        cliente = new ComboBox<>("Clientes:");
        cliente.setItems(new ListDataProvider<>(personaClienteService.findAllClientesByRFC()));
        cliente.addValueChangeListener(event->{

            String RFC = event.getValue();
            if (RFC != null && !RFC.isEmpty()){

                String personaFisica = personasFisicasClientesService.findNombreClienteByRFC(RFC);
                String personaMoral = personasMoralesClientesService.findRazonSocialByRFC(RFC);

                if (personaFisica != null && !personaFisica.isEmpty()){

                    cliente.setHelperText("Cliente: " + personaFisica);

                } else if (personaMoral != null && !personaMoral.isEmpty()){

                    cliente.setHelperText("Cliente: " + personaMoral);
                }
            }

        });
        fechaEstimadaInicio = new DatePicker("Fecha Estimada de Inicio");
        fechaEstimadaInicio.setMin(LocalDate.now());
        fechaEstimadaFinal = new DatePicker("Fecha Estimada de Finalización");
        fechaEstimadaFinal.setMin(LocalDate.now());
        fechaRealInicio = new DatePicker("Fecha Real de Inicio");
        fechaRealInicio.setMin(LocalDate.now());
        fechaRealFinal = new DatePicker("Fecha Real Final");
        fechaRealFinal.setMin(LocalDate.now());
        numeroProduccion = new TextField("N.P");



        panelOrdenes.add(
                numeroProduccion,
                estado,
                usuario,
                cliente,
                fechaEstimadaInicio,
                fechaEstimadaFinal,
                fechaRealInicio,
                fechaRealFinal
        );
    }

    private FormLayout getPanelOrdenes(){

        return panelOrdenes;

    }

    private void setPanelTabla(){

        panelTabla = new VerticalLayout();
        tablaOrdenes = new Grid<>();
        tablaOrdenes.addColumn(ordenProducto -> "Celda").setHeader("ID Orden-Producto");
        tablaOrdenes.addColumn(ordenProducto -> "Celda 2").setHeader("ID Orden");
        tablaOrdenes.addColumn(ordenProducto -> "Celda 3").setHeader("ID Producto");
        panelTabla.add(tablaOrdenes);

    }

    private VerticalLayout getPanelTabla(){

        return panelTabla;

    }

    private VerticalLayout getMainPanel(){

        return this;

    }
    private class MenuListener implements ComponentEventListener<Tabs.SelectedChangeEvent> {

        @Override
        public void onComponentEvent(Tabs.SelectedChangeEvent event) {

            setContentOnTabs(event.getSelectedTab());

        }
    }

    public class GuardarOrdenListener implements ComponentEventListener<ClickEvent<Button>>{

        Productos productos;
        Integer cantidad;
        String especificaciones;
        Set<String> procesosCombobox;
        DetalleOrdenes detalleOrdenes;

        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            Dialog dialog = new Dialog();
            Button btAceptar = new Button("Guardar");
            Button btCancelar = new Button("Cancelar");
            btCancelar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            btAceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            btAceptar.getStyle().set("margin-right", "auto");
            dialog.getFooter().add(btAceptar, btCancelar);
            dialog.add("¿Está seguro de guardar esta orden de producción?");
            dialog.open();

            btAceptar.addClickListener(eventSave -> {
                try {

                    Orden ordenNueva = new Orden();
                    Long idCliente = clientesService.obtenerIdClientePorRFC(cliente.getValue());
                    Clientes clienteExistente = clientesService.obtenerClientePorId(idCliente);
                    clienteExistente.getOrden().add(ordenNueva);
                    EstadoOrden estatusOrden = estadoOrdenService.findEstatusByNombreEstado(estado.getValue());
                    estatusOrden.getOrden().add(ordenNueva);
                    LocalDate fechaInicio = fechaEstimadaInicio.getValue();
                    LocalDate fechaFinal = fechaEstimadaFinal.getValue();
                    Date fechaInicioSQL = Date.valueOf(fechaInicio);
                    Date fechaFinalSQL = Date.valueOf(fechaFinal);
                    ordenNueva.setNp(Long.parseLong(numeroProduccion.getValue()));
                    ordenNueva.setFechaEstimadaInicio(fechaInicioSQL);
                    ordenNueva.setFechaEstimadaFinal(fechaFinalSQL);
                    ordenNueva.setFechaRealIniciol(fechaInicioSQL);
                    ordenNueva.setFechaRealFinal(fechaFinalSQL);
                    ordenNueva.setEstadoOrden(estatusOrden);
                    ordenNueva.setClientes(clienteExistente);
                    ordenService.guardarOrden(ordenNueva);

                    for (Component panelProductos: getPanelProductos().getChildren().toList()){

                        detalleOrdenes = new DetalleOrdenes();
                        /*
                        detalleOrdenes.setOrden(ordenNueva);
                        ordenNueva.getDatalleOrdenes().add(detalleOrdenes);
                        EstadoProceso estadoProceso = estadoProcesoService.findByEstado("En espera");
                        estadoProceso.getDetalleOrdenes().add(detalleOrdenes);
                        detalleOrdenes.setEstadoProceso(estadoProceso);


                         */
                        if (panelProductos instanceof ComboBox<?>){

                            String nombreProducto = (String) ((ComboBox<?>) panelProductos).getValue();
                            productos = productosService.findProductoByNombre(nombreProducto);
                            System.out.println(productos.getNombre());
                            //productos.getDatalleOrdenes().add(detalleOrdenes);
                            //detalleOrdenes.setProductos(productos);

                        } else if (panelProductos instanceof IntegerField){

                            cantidad = ((IntegerField) panelProductos).getValue();
                            System.out.println(cantidad);
                            //detalleOrdenes.setCantidad(cantidad);

                        } else if (panelProductos instanceof TextArea){

                            especificaciones = ((TextArea) panelProductos).getValue();
                            //detalleOrdenes.setEspecificaciones(especificaciones);
                            //detalleOrdenes.setNombreEncargado("José Manuel");

                        } else if (panelProductos instanceof MultiSelectComboBox<?>){

                            procesosCombobox = (Set<String>) ((MultiSelectComboBox<?>) panelProductos).getSelectedItems();
                            ArrayList<String> procesosSeleccionados = new ArrayList<>();
                            procesosSeleccionados.addAll(procesosCombobox);

                            for (int i = 0; i < procesosSeleccionados.size(); i++){

                                if (i == 0){

                                    Procesos procesosGuardado = procesoService.findByNombre(procesosSeleccionados.get(i));
                                    detalleOrdenes.setCantidad(cantidad);
                                    detalleOrdenes.setEspecificaciones(especificaciones);
                                    detalleOrdenes.setEstadoProceso(estadoProcesoService.findByEstado("En espera"));
                                    detalleOrdenes.setNombreEncargado("");
                                    detalleOrdenes.setOrden(ordenNueva);
                                    ordenNueva.getDatalleOrdenes().add(detalleOrdenes);
                                    detalleOrdenes.setProcesos(procesosGuardado);
                                    procesosGuardado.getDatalleOrdenes().add(detalleOrdenes);
                                    detalleOrdenes.setProductos(productos);
                                    productos.getDatalleOrdenes().add(detalleOrdenes);
                                    detalleOrdenService.saveDetalleOrden(detalleOrdenes);


                                } else{

                                    DetalleOrdenes detalleNuevo = new DetalleOrdenes();
                                    Procesos procesoNuevo = procesoService.findByNombre(procesosSeleccionados.get(i));
                                    detalleNuevo.setOrden(ordenNueva);
                                    ordenNueva.getDatalleOrdenes().add(detalleNuevo);
                                    detalleNuevo.setCantidad(cantidad);
                                    detalleNuevo.setEspecificaciones(especificaciones);
                                    detalleNuevo.setNombreEncargado("");
                                    detalleNuevo.setEstadoProceso(estadoProcesoService.findByEstado("En espera"));
                                    detalleNuevo.setProductos(productos);
                                    productos.getDatalleOrdenes().add(detalleNuevo);
                                    detalleNuevo.setProcesos(procesoNuevo);
                                    procesoNuevo.getDatalleOrdenes().add(detalleNuevo);
                                    detalleOrdenService.saveDetalleOrden(detalleNuevo);


                                }

                            }
                        }
                    }
                    Dialog dialogoConfirmacion = new Dialog();
                    dialogoConfirmacion.add("Orden agregada exitosamente!");
                    dialog.close();
                    dialogoConfirmacion.open();
                    desactivarComponentes(getMainPanel().getChildren().toList());

                }catch (NullPointerException | DataIntegrityViolationException e){

                    dialog.close();
                    Notification.show("Verifica que no tengas campos vacíos", 4000, Notification.Position.BOTTOM_END);
                    e.printStackTrace();

                }
            });



            btCancelar.addClickListener(eventCancel -> {

                dialog.close();

            });

        }
    }


    public class BotonLimpiarListener implements ComponentEventListener<ClickEvent<Button>> {

        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            limpiarComponentes();

        }
    }
    public class AnadirOrdenListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            try{

                if (estadoOrdenService.count() == 0){

                    throw new EstadosOrdenNotFoundException();

                } else if (clientesService.count() == 0){

                    throw new ClientesNotFoundException();
                } else if (procesoService.count() == 0){

                    throw new ProcesosNotFoundException();

                } else if (productosService.countProductos() == 0){

                    throw new ProductosNotFoundException();
                } else {

                    activarComponentes(getMainPanel().getChildren().toList());

                }

            } catch (EstadosOrdenNotFoundException e){

                Notification notificacionError = Notification.show("No existen estados para una OP. Favor de agregarlos", 4000, Notification.Position.MIDDLE);
                notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ClientesNotFoundException e){

                Notification notificacionError = Notification.show("No se encontraron clientes en el sistema. Favor de agregarlos", 4000, Notification.Position.MIDDLE);
                notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ProcesosNotFoundException e){

                Notification notificacionError = Notification.show("No se encontraron procesos en el sistema. Favor de agregarlos", 4000, Notification.Position.MIDDLE);
                notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ProductosNotFoundException e){

                Notification notificacionError = Notification.show("No se encontraron productos en el sistema. Favor de agregarlos", 4000, Notification.Position.MIDDLE);
                notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

        }
    }
    public class AnadirProductoListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            ComboBox<String> nuevoComboBox = new ComboBox<>("Seleccionar Productos");
            nuevoComboBox.setItems(new ListDataProvider<>(productosService.findAllProductosByNombre()));
            IntegerField nuevoIntegerField = new IntegerField("Cantidad");
            nuevoIntegerField.setMin(1);
            nuevoIntegerField.setMax(1000);
            nuevoIntegerField.setStepButtonsVisible(true);
            TextArea textArea = new TextArea("Especificaciones");
            textArea.setMaxLength(200);
            MultiSelectComboBox<String> procesos = new MultiSelectComboBox<>("Seleccionar Procesos");
            procesos.setItems(new ListDataProvider<>(procesoService.findAllProcesosByNombre()));
            getPanelProductos().add(nuevoComboBox);
            getPanelProductos().add(nuevoIntegerField);
            getPanelProductos().add(textArea);
            getPanelProductos().add(procesos);
        }
    }
    public class BorrarProductoListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            int elementos = getPanelProductos().getElement().getChildCount();
            if (elementos >= 8) {

                List<Component> componentList = getPanelProductos().getChildren().collect(Collectors.toList());
                componentList.remove(elementos - 1);
                componentList.remove(elementos - 2);
                componentList.remove(elementos - 3);
                componentList.remove(elementos - 4);
                getPanelProductos().removeAll();
                for (Component component : componentList) {
                    getPanelProductos().add(component);
                }
            }
        }
    }
}