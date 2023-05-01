package com.springApplication.moduloProduccion.views;


import com.springApplication.moduloProduccion.models.Proceso;
import com.springApplication.moduloProduccion.services.ProcesoService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Alta de procesos")
@Route(value = "altaprocesos", layout = MainLayout.class)
public class AltaProcesosView extends VerticalLayout {
    private Tab altaProcesos;
    private Tab consultaProcesos;
    private TextField campoIngresarProceso;
    private VerticalLayout panelProcesos;
    private Button[] botonesOperativos;
    private HorizontalLayout panelBotonesOperativos;
    private Grid<Proceso> tablaProcesos;
    private VerticalLayout panelContenidoTablaProcesos;
    private HorizontalLayout panelToolTipTabla;
    private Tabs menu;
    private ProcesoService procesoService;
    private ListDataProvider<Proceso> dataProvider;

    public AltaProcesosView(ProcesoService procesoService){

        this.procesoService = procesoService;
        initComponents();

    }

    private void initComponents(){

        setDataProvider();
        setMenu();
        setPanelProcesos();
        setPanelBotonesOperativos();
        setPanelContenidoTablaProcesos();
        add(getMenu());
        add(getPanelProcesos());
        add(getPanelBotonesOperativos());

    }

    private void setDataProvider(){

        dataProvider = new ListDataProvider<>(procesoService.getAllProcesos());
    }

    private ListDataProvider<Proceso> getDataProvider(){

        return dataProvider;
    }

    private void setContentOnTabs(Tab tabs){

        remove(getPanelProcesos(), getPanelBotonesOperativos(), getPanelContenidoTablaProcesos());

        if (tabs.equals(altaProcesos)){

            add(getPanelProcesos(), getPanelBotonesOperativos());

        } else if (tabs.equals(consultaProcesos)){

            add(getPanelContenidoTablaProcesos());

        }
    }

    private void setMenu(){

        menu = new Tabs();
        altaProcesos = new Tab("Alta de procesos");
        consultaProcesos = new Tab("Consultar Procesos");
        menu.add(altaProcesos, consultaProcesos);
        menu.addSelectedChangeListener(new SeleccionarVentanaListener());

    }

    private Tabs getMenu(){
        return menu;
    }

    private void setCampoIngresarProceso(){

        campoIngresarProceso = new TextField("Ingrese el proceso");
        campoIngresarProceso.setRequired(true);
        campoIngresarProceso.setRequiredIndicatorVisible(true);
        campoIngresarProceso.setInvalid(true);
        campoIngresarProceso.addValueChangeListener(event -> {

            String valor = event.getValue();

            if (valor.isEmpty() || valor.isBlank()){
                campoIngresarProceso.setInvalid(true);
            } else {
                campoIngresarProceso.setInvalid(false);
            }

        });
    }

    private TextField getCampoIngresarProceso(){

        return campoIngresarProceso;
    }

    private void setPanelProcesos(){

        panelProcesos = new VerticalLayout();
        setCampoIngresarProceso();
        panelProcesos.setPadding(false);
        panelProcesos.add(getCampoIngresarProceso());
    }

    private VerticalLayout getPanelProcesos(){
        return panelProcesos;
    }

    private void setPanelBotonesOperativos(){

        panelBotonesOperativos = new HorizontalLayout();
        botonesOperativos = new Button[]{
                new Button("Guardar"),
                new Button("Limpiar Campo"),
        };
        botonesOperativos[0].addClickListener(new GuardarProcesosListener());
        botonesOperativos[1].addClickListener(eventoLimpiar -> limpiarCampo());
        panelBotonesOperativos.add(botonesOperativos);
    }
    private HorizontalLayout getPanelBotonesOperativos(){
        return panelBotonesOperativos;
    }

    private void setPanelContenidoTablaProcesos(){

        panelContenidoTablaProcesos = new VerticalLayout();
        tablaProcesos = new Grid<>();
        tablaProcesos.addColumn(Proceso::getCodigo_proceso).setHeader("Código");
        tablaProcesos.addColumn(Proceso::getNombre).setHeader("Nombre");
        tablaProcesos.addColumn(new ComponentRenderer<>(Button::new, (button, proceso) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        procesoService.borrarProceso(proceso);
                        dataProvider.getItems().remove(proceso);
                        dataProvider.refreshAll();
                        Notification.show("Proceso eliminado", 2000, Notification.Position.BOTTOM_END);
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Eliminar");
        tablaProcesos.setDataProvider(getDataProvider());
        panelToolTipTabla = new HorizontalLayout();
        Button btActualizar = new Button(new Icon("lumo", "reload"));
        btActualizar.setTooltipText("Actualizar tabla");
        btActualizar.addClickListener(eventoActualizar ->{
           actualizarTabla();
        });
        TextField filtro = new TextField("Filtro");
        Button btLimpiarBusqueda = new Button("Limpiar Busqueda");
        filtro.addValueChangeListener(event ->{

            dataProvider.setFilterByValue(Proceso::getNombre, event.getValue());
        });
        btLimpiarBusqueda.addClickListener(eventoBuscar ->{

            filtro.clear();
            dataProvider.clearFilters();
        });
        panelToolTipTabla.add(filtro, btLimpiarBusqueda, btActualizar);
        panelToolTipTabla.setVerticalComponentAlignment(Alignment.END, btLimpiarBusqueda, btActualizar);
        panelContenidoTablaProcesos.add(panelToolTipTabla, tablaProcesos);
    }

    private void actualizarTabla(){

        setDataProvider();
        tablaProcesos.setDataProvider(getDataProvider());

    }

    private VerticalLayout getPanelContenidoTablaProcesos(){
        return panelContenidoTablaProcesos;
    }
    private void limpiarCampo(){

        getCampoIngresarProceso().setValue("");

    }
    public class GuardarProcesosListener implements ComponentEventListener<ClickEvent<Button>> {

        @Override
        public void onComponentEvent(ClickEvent<Button> event) {

            Dialog dialogo = new Dialog();
            Button btAceptar = new Button("Guardar");
            Button btCancelar = new Button("Cancelar");
            btAceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            btAceptar.getStyle().set("margin-right", "auto");
            btCancelar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            btAceptar.addClickListener(eventGuardar -> {

                if (getCampoIngresarProceso().isInvalid()) {

                    dialogo.close();
                    Notification.show("No puede dejar campos vacíos", 3000, Notification.Position.BOTTOM_END);

                } else {

                        Proceso procesoExistente = procesoService.getProcesoByNombre(getCampoIngresarProceso().getValue());
                        if (procesoExistente != null) {

                            Dialog dialogoError = new Dialog();
                            dialogoError.add("El proceso " + getCampoIngresarProceso().getValue() + " ya existe");
                            dialogoError.open();
                            dialogo.close();

                        } else {

                            Proceso proceso = new Proceso();
                            proceso.setNombre(getCampoIngresarProceso().getValue());
                            procesoService.saveProceso(proceso);
                            Dialog dialogoConfirmacion = new Dialog();
                            dialogo.close();
                            dialogoConfirmacion.add("Proceso agregado exitosamente!");
                            dialogoConfirmacion.open();
                            limpiarCampo();
                        }
                }
            });

            btCancelar.addClickListener(eventoCancelar -> dialogo.close());

        dialogo.getFooter().add(btAceptar, btCancelar);
        dialogo.add("¿Está seguro de guardar este proceso?");
        dialogo.open();
        }
    }
    public class SeleccionarVentanaListener implements ComponentEventListener<Tabs.SelectedChangeEvent> {

        @Override
        public void onComponentEvent(Tabs.SelectedChangeEvent event) {

            setContentOnTabs(event.getSelectedTab());

        }
    }
}
