package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.models.*;
import com.springApplication.moduloProduccion.services.*;
import com.springApplication.moduloProduccion.util.exceptions.ProveedoresNotFoundException;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.aspectj.weaver.ast.Not;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.form.CrudFormFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@PageTitle("Materiales")
@Route(value = "materiales", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class MaterialesView extends VerticalLayout {

    private TextField nombreMaterialPF = new TextField("Nombre");
    private TextField nombreMateriaPM = new TextField("Nombre");
    private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private Upload subirArchivoPF = new Upload(buffer);
    private ComboBox<String> proveedor;

    public MaterialesView(MaterialesService materialesService, PersonasFisicasProveedoresService pfService, PersonasMoralesProveedoresService pmService,
                          PersonaProveedorService personaProveedorService, ProveedoresService proveedorService){

        var crudMaterialPF = new GridCrud<>(Materiales.class, materialesService);

        crudMaterialPF.getGrid().removeAllColumns();

        nombreMaterialPF.setPlaceholder("Filtro por nombre de material");
        nombreMaterialPF.setClearButtonVisible(true);
        nombreMaterialPF.addValueChangeListener(e -> crudMaterialPF.getGrid().setItems(materialesService.findAllMateriales(nombreMaterialPF.getValue())));

        proveedor = new ComboBox<>("Proveedores:");
        HorizontalLayout toolBar = new HorizontalLayout(nombreMaterialPF, proveedor);
        /*
        crudMaterialPF.getGrid().setColumns("codigo_material", "nombre", "descripcion", "precioCompra", "unidad");
        crudMaterialPF.getGrid().getColumns().get(0).setHeader("Código");
        crudMaterialPF.getGrid().getColumns().get(3).setHeader("Precio de compra");
        crudMaterialPF.getGrid().getColumns().get(4).setHeader("Unidad de medida");

         */

        crudMaterialPF.getCrudFormFactory().setVisibleProperties("");
        crudMaterialPF.getGrid().addColumn(Materiales::getCodigo_material).setHeader("ID Material");
        crudMaterialPF.getGrid().addColumn(Materiales::getNombre).setHeader("Nombre material");
        crudMaterialPF.getGrid().addColumn(Materiales::getDescripcion).setHeader("Descripción");
        crudMaterialPF.getGrid().addColumn(Materiales::getPrecioCompra).setHeader("Precio de compra");

        crudMaterialPF.getGrid().asSingleSelect().addValueChangeListener(event -> {

            try {


                if (event.getValue() != null) {

                    List<String> proveedores = new ArrayList<>();

                    for (Proveedores listaProveedores : event.getValue().getProveedores()) {

                        String RFC = listaProveedores.getPersonaProveedor().getRfc();
                        proveedores.add(RFC);

                    }

                    if (proveedores.isEmpty()) {

                        throw new ProveedoresNotFoundException();

                    } else {

                        proveedor.setItems(proveedores);
                        proveedor.setOpened(true);
                        proveedor.addValueChangeListener(valueEvent -> {

                            String RFC = valueEvent.getValue();
                            if (RFC != null && !RFC.isEmpty()){

                                String personaFisica = pfService.findNombreProveedorByRFC(RFC);
                                String personaMoral = pmService.findRazonSocialProveedorByRFC(RFC);

                                if (personaFisica != null && !personaFisica.isEmpty()){

                                    proveedor.setHelperText("Proveedor: " + personaFisica);

                                } else if (personaMoral != null && !personaMoral.isEmpty()){

                                    proveedor.setHelperText("Proveedor: " + personaMoral);
                                }
                            }

                        });
                    }
                }

            } catch (ProveedoresNotFoundException e){

                proveedor.setValue(null);
                TextArea textArea = new TextArea("Proveedores seleccionados");
                textArea.setWidth("100%");
                textArea.setHeight("200px");
                textArea.setReadOnly(true);
                Dialog formulario = new Dialog();
                Button btGuardar = new Button("Guardar");
                Dialog dialogo = new Dialog();
                Button btAceptar = new Button("SÍ");
                btAceptar.getStyle().set("margin-right", "auto");
                Button btCancelar = new Button("NO");
                MultiSelectComboBox<String> listaProveedores = new MultiSelectComboBox<>();
                listaProveedores.setItems(personaProveedorService.findAllProveedoresByRFC());
                listaProveedores.addValueChangeListener(eventValue ->{

                    Set<String> RFCs = eventValue.getValue();
                    if (RFCs != null && !RFCs.isEmpty()) {
                        List<String> nombresProveedores = new ArrayList<>();
                        for (String rfc : RFCs) {
                            if (rfc != null && !rfc.isEmpty()){

                                String personaFisica = pfService.findNombreProveedorByRFC(rfc);
                                String personaMoral = pmService.findRazonSocialProveedorByRFC(rfc);

                                if (personaFisica != null && !personaFisica.isEmpty()){

                                    nombresProveedores.add(personaFisica);

                                } else if (personaMoral != null && !personaMoral.isEmpty()){

                                    nombresProveedores.add(personaMoral);
                                }
                            }

                        }
                        String nombresConcatenadosStr = String.join("\n", nombresProveedores);
                        textArea.setValue(nombresConcatenadosStr);
                    } else {
                        textArea.setValue("");
                    }
                });
                btCancelar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
                btAceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
                btCancelar.addClickListener(eventCancelar->{

                    dialogo.close();

                });
                btGuardar.addClickListener(eventGuardar ->{

                    Set<String> proveedoresSeleccionados = listaProveedores.getSelectedItems();
                    List<String> listaProveedoresAGuardar = new ArrayList<>(proveedoresSeleccionados);

                    for (int i = 0; i < listaProveedoresAGuardar.size(); i++){


                        Long idProveedor = proveedorService.obtenerIdProveedorPorRFC(listaProveedoresAGuardar.get(i));
                        Proveedores proveedorExistente = proveedorService.obtenerProveedorPorId(idProveedor);
                        proveedorExistente.getMateriales().add(event.getValue());
                        proveedorService.add(proveedorExistente);
                    }

                    Notification.show("Guardado con éxito!", 3000, Notification.Position.MIDDLE);
                    crudMaterialPF.refreshGrid();
                    formulario.close();

                });
                btAceptar.addClickListener(eventoProveedor ->{

                    if(proveedorService.count() == 0){

                        dialogo.close();
                        Notification notificacionError = Notification.show("No tienes proveedores agregados. Por favor " +
                                "vaya al módulo de proveedores para agregar sus proveedores", 4000, Notification.Position.MIDDLE);
                        notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);

                    } else {

                        dialogo.close();
                        formulario.open();

                    }

                });
                dialogo.add("Este material no tiene proveedores asignados. ¿Deseas asignarle uno?");
                dialogo.getFooter().add(btAceptar, btCancelar);
                formulario.getFooter().add(btGuardar);
                formulario.add(listaProveedores, textArea);
                dialogo.open();



            }
        });

        crudMaterialPF.getCrudFormFactory().setVisibleProperties("nombre", "descripcion", "precioCompra", "unidad");
        crudMaterialPF.setUpdateOperationVisible(false);
        crudMaterialPF.getAddButton().setTooltipText("Añadir producto");
        crudMaterialPF.getAddButton().setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        crudMaterialPF.getDeleteButton().setTooltipText("Eliminar producto");
        crudMaterialPF.getFindAllButton().setTooltipText("Refrescar tabla");
        crudMaterialPF.getCrudLayout().addToolbarComponent(toolBar);
        crudMaterialPF.setSavedMessage("Se ha guardado con exito");

        subirArchivoPF.setAcceptedFileTypes("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        subirArchivoPF.setUploadButton(new Button("Cargar"));
        subirArchivoPF.setMaxFiles(1);
        subirArchivoPF.setMaxFileSize(104857600);
        subirArchivoPF.setDropLabel(new Label("O arrastra tu archivo aquí"));
        subirArchivoPF.addFileRejectedListener(event -> {

            Notification notificacionArchivoRechazado = Notification.show("No se admiten archivos con este formato", 3000,
                    Notification.Position.MIDDLE);
            notificacionArchivoRechazado.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        var texto = new Paragraph("Puede importar los materiales mediante un archivo de Excel (.xls, .xlsx). El tamaño máximo es de 100 MB");
        var textoAdvertencia = new Paragraph("Recuerde seguir el formato del archivo para que el sistema lea correctamente su importanción");
        subirArchivoPF.addSucceededListener(event -> {
            try {

                String nombreArchivo = event.getFileName();
                InputStream datosExcel = buffer.getInputStream(nombreArchivo);
                Workbook excel = WorkbookFactory.create(datosExcel);
                Sheet hoja = excel.getSheetAt(0);
                int inicioDatos = 2;
                List<Materiales> listaMateriales = new ArrayList<>();

                if (hoja.getLastRowNum() > 0) {
                    for (int i = inicioDatos; i < hoja.getLastRowNum(); i++) {

                        Row fila = hoja.getRow(i);

                        Materiales materiales = new Materiales();

                        materiales.setNombre(fila.getCell(1).getStringCellValue());
                        materiales.setDescripcion(fila.getCell(2).getStringCellValue());
                        materiales.setPrecioCompra(fila.getCell(3).getNumericCellValue());
                        materiales.setUnidad(fila.getCell(4).getStringCellValue());

                        listaMateriales.add(materiales);

                    }
                    excel.close();
                    datosExcel.close();
                    materialesService.saveAllMateriales(listaMateriales);
                    Notification notificacionExito = Notification.show("Materiales agregados con éxito!. Actualize la tabla para ver los items agregados", 2000, Notification.Position.MIDDLE);
                    notificacionExito.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    subirArchivoPF.clearFileList();
                    listaMateriales.clear();
                } else {
                    throw new Exception();
                }

            }catch (IOException e) {

                Notification notificacionError = Notification.show("Error al cargar el archivo", 3000, Notification.Position.MIDDLE);
                notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);

            }catch (Exception e){

                Notification notificacionError = Notification.show("Asegurese que su archivo no esté vacío y que siga" +
                        " el formato correcto", 3000, Notification.Position.MIDDLE);
                notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);
                e.printStackTrace();

            }

        });
        add(
                crudMaterialPF, texto, textoAdvertencia, subirArchivoPF
        );
    }
}
