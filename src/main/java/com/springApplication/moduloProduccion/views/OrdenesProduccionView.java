package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.models.*;
import com.springApplication.moduloProduccion.services.*;
//import com.springApplication.moduloProduccion.util.ProductosNotFoundException;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldBase;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@PageTitle("Órdenes de Producción")
@Route(value = "ordenesProduccion", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class OrdenesProduccionView extends VerticalLayout {
    private TextField numeroProduccion;
    private TextField estado;
    private TextField usuario;
    private ComboBox<String> cliente;
    private DatePicker fechaEstimadaInicio;
    private DatePicker fechaEstimadaFinal;
    private Button[] botonesCRUD;
    private Button[] botonesOperativos;
    private Tab ordenesTab;
    private Tab consultarOrdenesTab;
    private Tabs menu;
    private Grid<OrdenProducto> tablaOrdenes;
    private FormLayout panelOrdenes;
    private HorizontalLayout panelBotonesOperativos;
    private HorizontalLayout panelBotonesCrud;
    private HorizontalLayout panelFiltro;
    private VerticalLayout panelTabla;
    private FormLayout panelProductos;
    private ListDataProvider<Orden> dataProvider;
    @Autowired
    private ProcesoService procesoService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private OrdenService ordenService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EstatusOrdenService estatusOrdenService;
    @Autowired
    private OrdenProductoService ordenProductoService;


    public OrdenesProduccionView(ProcesoService procesoService, ProductoService productoService, ClienteService clienteService, OrdenService ordenService,
                                 UsuarioService usuarioService, EstatusOrdenService estatusOrdenService, OrdenProductoService ordenProductoService) {

        this.productoService = productoService;
        this.procesoService = procesoService;
        this.clienteService = clienteService;
        this.ordenService = ordenService;
        this.usuarioService = usuarioService;
        this.estatusOrdenService = estatusOrdenService;
        this.ordenProductoService = ordenProductoService;
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
                new Button(new Icon(VaadinIcon.PLUS_CIRCLE)),
                new Button(new Icon(VaadinIcon.MINUS_CIRCLE)),
        };

        botonesCRUD[0].addClickListener(new AnadirOrdenListener());
        botonesCRUD[1].addClickListener(new AnadirProductoListener());
        botonesCRUD[1].setTooltipText("Agregar producto");
        botonesCRUD[2].addClickListener(new BorrarProductoListener());
        botonesCRUD[2].setTooltipText("Quitar producto");
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
                        ((TextField) listaComponentesPanelOrdenes).setValue(estatusOrdenService.findEstatusNombreByNombre("En Espera"));

                    } else if (listaComponentesPanelOrdenes instanceof TextField && listaComponentesPanelOrdenes.equals(usuario)){

                        ((TextField) listaComponentesPanelOrdenes).setEnabled(false);
                        ((TextField) listaComponentesPanelOrdenes).setValue(usuarioService.findNombreUsuarioByNombre("Eduardo"));

                    } else if (listaComponentesPanelOrdenes instanceof ComboBox<?>){

                            ((ComboBox<?>) listaComponentesPanelOrdenes).setEnabled(true);

                    } else if (listaComponentesPanelOrdenes instanceof DatePicker) {

                        ((DatePicker) listaComponentesPanelOrdenes).setEnabled(true);

                    } else if (listaComponentesPanelOrdenes instanceof CheckboxGroup<?>){

                        ((CheckboxGroup<?>) listaComponentesPanelOrdenes).setEnabled(true);

                    }
                }
            }
        }
    }
    private void limpiarComponentes(){

       cliente.setValue(null);
       fechaEstimadaInicio.setValue(null);
       fechaEstimadaFinal.setValue(null);

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

           if (listaComponentesPanelProductos instanceof CheckboxGroup<?>){

               ((CheckboxGroup<?>) listaComponentesPanelProductos).deselectAll();

           }

       }


    }


    private void setPanelProductos(){

        panelProductos = new FormLayout();
        ComboBox<String> nuevoComboBox = new ComboBox<>("Seleccionar Productos");
        nuevoComboBox.setItems(new ListDataProvider<>(productoService.getAllProductosByNombre()));
        IntegerField nuevoIntegerField = new IntegerField("Cantidad");
        nuevoIntegerField.setMin(1);
        nuevoIntegerField.setMax(1000);
        nuevoIntegerField.setStepButtonsVisible(true);
        TextArea textArea = new TextArea("Especificaciones");
        textArea.setMaxLength(200);
        CheckboxGroup<String> checkBoxProcesos = new CheckboxGroup<>("Seleccione procesos");
        checkBoxProcesos.setItems(new ListDataProvider<>(procesoService.getAllProcesosByNombre()));
        panelProductos.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
        panelProductos.add(nuevoComboBox, nuevoIntegerField, textArea, checkBoxProcesos);

    }

    private FormLayout getPanelProductos(){

        return panelProductos;

    }

    private void setPanelOrdenes(){

        panelOrdenes = new FormLayout();
        panelOrdenes.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        panelOrdenes.addClassName("ordenes");
        estado = new TextField("Estado");
        usuario = new TextField("Usuario");
        cliente = new ComboBox<>("Clientes:");
        cliente.setItems(new ListDataProvider<>(clienteService.findAllClientesByName()));
        fechaEstimadaInicio = new DatePicker("Fecha Estimada de Inicio");
        fechaEstimadaInicio.setMin(LocalDate.now());
        fechaEstimadaFinal = new DatePicker("Fecha Estimada de Finalización");
        fechaEstimadaFinal.setMin(LocalDate.now());
        numeroProduccion = new TextField("N.P");
        panelOrdenes.add(
                numeroProduccion,
                estado,
                usuario,
                cliente,
                fechaEstimadaInicio,
                fechaEstimadaFinal
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
        //panelTabla.add(tablaOrdenes);

        panelFiltro = new HorizontalLayout();
        Button btActualizar = new Button(new Icon("lumo", "reload"));
        btActualizar.setTooltipText("Actualizar tabla");
        btActualizar.addClickListener(eventoActualizar ->{
          //actualizarTabla();
        });
        TextField filtro = new TextField("Filtro");
        Button btLimpiarBusqueda = new Button("Limpiar Busqueda");
        filtro.addValueChangeListener(event ->{

            dataProvider.setFilterByValue(Orden::getCliente, event.getValue());
        });
        btLimpiarBusqueda.addClickListener(eventoBuscar ->{

            filtro.clear();
            dataProvider.clearFilters();
        });
        panelFiltro.add(filtro, btLimpiarBusqueda, btActualizar);
        panelFiltro.setVerticalComponentAlignment(Alignment.END, btLimpiarBusqueda, btActualizar);
        panelTabla.add(panelFiltro, tablaOrdenes);
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
                    Cliente clienteExistente = clienteService.findClienteByNombre(cliente.getValue());
                    EstatusOrden estatusOrden = estatusOrdenService.findEstatusByNombreEstado(estado.getValue());
                    Usuario usuarioExistente = usuarioService.findUsuarioByNombre(usuario.getValue());
                    LocalDate fechaInicio = fechaEstimadaInicio.getValue();
                    LocalDate fechaFinal = fechaEstimadaFinal.getValue();
                    Date fechaInicioSQL = Date.valueOf(fechaInicio);
                    Date fechaFinalSQL = Date.valueOf(fechaFinal);
                    ordenNueva.setNp(Long.parseLong(numeroProduccion.getValue()));
                    ordenNueva.setFechaInicio(fechaInicioSQL);
                    ordenNueva.setFechaFinal(fechaFinalSQL);
                    ordenNueva.setCliente(clienteExistente);
                    ordenNueva.setUsuario(usuarioExistente);
                    ordenNueva.setEstatus(estatusOrden);
                    List<Producto> productos = new ArrayList<>();
                    List<Integer> cantidades = new ArrayList<>();
                    List<String> especificaciones = new ArrayList<>();
                    for (Component panelProductos: getPanelProductos().getChildren().toList()){

                        if (panelProductos instanceof ComboBox<?>){

                            Producto producto = productoService.findProductoByNombre((String) ((ComboBox<?>) panelProductos).getValue());
                            productos.add(producto);

                        } else if (panelProductos instanceof IntegerField){

                            cantidades.add(((IntegerField) panelProductos).getValue());

                        } else if (panelProductos instanceof TextArea){

                            especificaciones.add(((TextArea) panelProductos).getValue());

                        }
                    }
                    ordenProductoService.saveOrdenConProductos(ordenNueva, productos, cantidades, especificaciones);
                    Dialog dialogoConfirmacion = new Dialog();
                    dialogoConfirmacion.add("Orden agregada exitosamente!");
                    dialog.close();
                    dialogoConfirmacion.open();
                    desactivarComponentes(getMainPanel().getChildren().toList());

                }catch (NullPointerException | DataIntegrityViolationException e){

                    dialog.close();
                    Notification.show("Verifica que no tengas campos vacíos", 4000, Notification.Position.BOTTOM_END);

                }catch (InvalidDataAccessApiUsageException e2){

                    dialog.close();
                    Notification.show("No puedes agregar 2 o mas productos iguales a la vez", 4000, Notification.Position.TOP_END);
                }
            });
            btCancelar.addClickListener(eventCancel -> {

                dialog.close();

            });

        }
    }

    
    public class BotonLimpiarListener implements ComponentEventListener<ClickEvent<Button>> {

        public void onComponentEvent(ClickEvent<Button> event) {

            limpiarComponentes();

        }
    }
    public class AnadirOrdenListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            activarComponentes(getMainPanel().getChildren().toList());
        }
    }
    public class AnadirProductoListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            ComboBox<String> nuevoComboBox = new ComboBox<>("Seleccionar Productos");
            nuevoComboBox.setItems(new ListDataProvider<>(productoService.getAllProductosByNombre()));
            IntegerField nuevoIntegerField = new IntegerField("Cantidad");
            nuevoIntegerField.setMin(1);
            nuevoIntegerField.setMax(1000);
            nuevoIntegerField.setStepButtonsVisible(true);
            TextArea textArea = new TextArea("Especificaciones");
            textArea.setMaxLength(200);
            CheckboxGroup<String> checkBoxProcesos = new CheckboxGroup<>();
            checkBoxProcesos.setItems(new ListDataProvider<>(procesoService.getAllProcesosByNombre()));
            getPanelProductos().add(nuevoComboBox);
            getPanelProductos().add(nuevoIntegerField);
            getPanelProductos().add(textArea);
            getPanelProductos().add(checkBoxProcesos);
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
