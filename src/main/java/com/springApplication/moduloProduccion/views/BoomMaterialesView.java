package com.springApplication.moduloProduccion.views;


import com.springApplication.moduloProduccion.models.Producto;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "explosionmateriales", layout = MainLayout.class)
@PageTitle(value = "Boom de Materiales")
public class BoomMaterialesView extends VerticalLayout {
    private ComboBox<String> seleccionProductos;
    private TextField codigoProducto;
    private Button botonEliminarMaterial;
    private Button botonAniadirMaterial;
    private Button[] botonesOperativos;
    private Tab altaProductosTab;
    private Tab consultaProductosTab;
    private Grid<Producto> tablaProductos;
    private HorizontalLayout panelBotonesMateriales;
    private HorizontalLayout panelProductos;
    private VerticalLayout panelMateriales;
    private HorizontalLayout panelBotonesOperativos;
    private VerticalLayout panelTabla;
    private Tabs menu;


    public BoomMaterialesView(){

        initComponents();

    }

    private void initComponents(){

        setMenu();
        setPanelBotonesMateriales();
        setPanelProductos();
        setPanelMateriales();
        setPanelBotonesOperativos();
        setPanelTabla();
        add(getMenu());
        add(getPanelBotonesMateriales());
        add(getPanelProductos());
        add(getPanelMateriales());
        add(getPanelBotonesOperativos());

    }

    private void setMenu(){

        menu = new Tabs();
        altaProductosTab = new Tab("Alta de productos y materiales");
        consultaProductosTab = new Tab("Consulta de productos y materiales");
        menu.add(altaProductosTab, consultaProductosTab);
        menu.addSelectedChangeListener(new SeleccionarTabListener());
    }

    private Tabs getMenu(){
        return menu;
    }

    private void setPanelBotonesMateriales(){

        panelBotonesMateriales = new HorizontalLayout();
        botonAniadirMaterial = new Button("Añadir Material");
        botonEliminarMaterial = new Button("Eliminar Material");
        botonAniadirMaterial.setIcon(new Icon("lumo", "plus"));
        botonAniadirMaterial.setHeight("100%");
        botonAniadirMaterial.addClickListener(new AnadirMaterialListener());
        botonEliminarMaterial.setIcon(new Icon("lumo", "minus"));
        botonEliminarMaterial.setHeight("100%");
        botonEliminarMaterial.addClickListener(new BorrarMaterialListener());
        panelBotonesMateriales.add(botonAniadirMaterial, botonEliminarMaterial);

    }

    private HorizontalLayout getPanelBotonesMateriales(){
        return panelBotonesMateriales;
    }

    private void setPanelProductos(){

        panelProductos = new HorizontalLayout();
        seleccionProductos = new ComboBox<>("Seleccionar Productos");
        seleccionProductos.setItems("Producto 1", "Producto 2", "Producto 3");
        codigoProducto = new TextField("Código de producto");
        panelProductos.add(seleccionProductos, codigoProducto);

    }

    private HorizontalLayout getPanelProductos(){
        return panelProductos;
    }
    private void setPanelMateriales(){
        panelMateriales = new VerticalLayout();
        panelMateriales.setPadding(false);
    }

    private VerticalLayout getPanelMateriales(){
        return panelMateriales;
    }

    private void setPanelBotonesOperativos(){

        panelBotonesOperativos = new HorizontalLayout();
        botonesOperativos = new Button[]{

                new Button("Guardar"),
                new Button("Limpiar"),
                new Button("Generar PDF")
        };
        botonesOperativos[0].addClickListener(new GuardarProductoListener());
        botonesOperativos[1].addClickListener(new LimpiarCamposListener());
        panelBotonesOperativos.add(botonesOperativos);
    }

    private HorizontalLayout getPanelBotonesOperativos(){
        return panelBotonesOperativos;
    }

    private void setPanelTabla(){

        panelTabla = new VerticalLayout();
        tablaProductos = new Grid<>();
        tablaProductos.addColumn(productos -> "Columna").setHeader("ID Material");
        tablaProductos.addColumn(productos -> "Columna 2").setHeader("ID Producto");
        tablaProductos.addColumn(productos -> "Columna 3").setHeader("Descripcion");
        panelTabla.add(tablaProductos);

    }

    private VerticalLayout getPanelTabla(){
        return panelTabla;
    }

    private void setContentOnTabs(Tab tabs){

        remove(getPanelBotonesMateriales(), getPanelProductos(), getPanelMateriales(), getPanelBotonesOperativos(), getPanelTabla());

        if (tabs.equals(altaProductosTab)){

            add(getPanelBotonesMateriales(), getPanelProductos(), getPanelMateriales(), getPanelBotonesOperativos());


        } else if (tabs.equals(consultaProductosTab)){

            add(getPanelTabla());
        }

    }


    public class SeleccionarTabListener implements ComponentEventListener<Tabs.SelectedChangeEvent>{

        @Override
        public void onComponentEvent(Tabs.SelectedChangeEvent event){

            setContentOnTabs(event.getSelectedTab());

        }
    }

    public class AnadirMaterialListener implements ComponentEventListener<ClickEvent<Button>>{

        @Override
        public void onComponentEvent(ClickEvent<Button> event){

            TextField nuevoTextField = new TextField("Ingrese material");
            getPanelMateriales().add(nuevoTextField);
        }
    }

    public class BorrarMaterialListener implements ComponentEventListener<ClickEvent<Button>>{

        @Override
        public void onComponentEvent(ClickEvent<Button> event){

            int elementos = getPanelMateriales().getComponentCount();

            if (elementos >= 1){

                getPanelMateriales().remove(getPanelMateriales().getComponentAt(elementos - 1));

            }
        }
    }

    public class GuardarProductoListener implements ComponentEventListener<ClickEvent<Button>>{

        @Override
        public void onComponentEvent(ClickEvent<Button> event){

            Dialog dialogo = new Dialog();
            dialogo.add("¿Está seguro de guardar el producto?");
            Button botonAceptar = new Button("Aceptar");
            botonAceptar.getStyle().set("margin-right", "auto");
            botonAceptar.addClickListener(eventSave -> {

               dialogo.close();
               getPanelMateriales().removeAll();
               codigoProducto.setValue("");
               seleccionProductos.setValue(null);

            });
            botonAceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            Button botonCancelar = new Button("Cancelar");
            botonCancelar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            botonCancelar.addClickListener(eventCancel ->{

                dialogo.close();

            });
            dialogo.getFooter().add(botonAceptar, botonCancelar);
            dialogo.open();
        }
    }

    public class LimpiarCamposListener implements ComponentEventListener<ClickEvent<Button>>{

        @Override
        public void onComponentEvent(ClickEvent<Button> event){



        }

    }
}