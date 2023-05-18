package com.springApplication.moduloProduccion.views;


import com.springApplication.moduloProduccion.models.*;
import com.springApplication.moduloProduccion.services.PersonasFisicasProveedoresService;
import com.springApplication.moduloProduccion.services.PersonasMoralesProveedoresService;
import com.springApplication.moduloProduccion.services.ProveedoresService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Proveedores")
@Route(value = "proveedores", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ProveedoresView extends VerticalLayout {

    private GridCrud<PersonasFisicasProveedor> tablaProveedoresPF;
    private GridCrud<PersonasMoralesProveedor> tablaProveedoresPM;
    private HorizontalLayout layoutRadioBtn;
    private HorizontalLayout layoutTipo;
    private RadioButtonGroup<String> radioGroup;
    private PersonaFisicaView pf;
    private PersonaMoralView pm;
    private ProveedoresService proveedoresService;
    private PersonasMoralesProveedoresService personasMoralesService;
    private PersonasFisicasProveedoresService personasFisicasService;
    private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private Upload archivoProveedoresPF = new Upload(buffer);
    private Upload archivoProveedoresPM = new Upload(buffer);

    public ProveedoresView(ProveedoresService proveedoresService, PersonasFisicasProveedoresService personasFisicasService, PersonasMoralesProveedoresService personasMoralesService) {

        this.proveedoresService = proveedoresService;
        this.personasFisicasService = personasFisicasService;
        this.personasMoralesService = personasMoralesService;
        tablaProveedoresPF = new GridCrud<>(PersonasFisicasProveedor.class, personasFisicasService);
        tablaProveedoresPM = new GridCrud<>(PersonasMoralesProveedor.class, personasMoralesService);
        pf = new PersonaFisicaView(personasFisicasService, proveedoresService);
        pf.getAddPFCliente().setVisible(false);
        pf.getSaveCliente().setVisible(false);
        pm = new PersonaMoralView(personasMoralesService, proveedoresService);
        pm.getAddPMCliente().setVisible(false);
        pm.getSaveCliente().setVisible(false);
        layoutRadioBtn = new HorizontalLayout();
        layoutTipo = new HorizontalLayout();
        radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_HELPER_ABOVE_FIELD);
        radioGroup.setLabel("Tipo de proveedor que quiere añadir");
        radioGroup.setItems("Persona Física", "Persona Moral");

        tablaProveedoresPF.getAddButton().setVisible(false);
        tablaProveedoresPF.getUpdateButton().setVisible(false);
        tablaProveedoresPF.getGrid().removeAllColumns();
        tablaProveedoresPF.getGrid().addColumn(PersonasFisicasProveedor::getIdPersonaFisicaProveedor).setHeader("ID Proveedor").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(PersonasFisicasProveedor::getNombre).setHeader("Nombre").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(PersonasFisicasProveedor::getAPaterno).setHeader("Apellido Paterno").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(PersonasFisicasProveedor::getAMaterno).setHeader("Apellido Paterno").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getCalle()).setHeader("Calle").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getCodigoPostal()).setHeader("C.P").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getColonia()).setHeader("Colonia").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getEmail()).setHeader("E-Mail").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getEstado()).setHeader("Estado").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getMunicipio()).setHeader("Municipio").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getNumero()).setHeader("Número").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getRfc()).setHeader("RFC").setAutoWidth(true);
        tablaProveedoresPF.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getTelefono()).setHeader("Telefono").setAutoWidth(true);

        tablaProveedoresPM.getAddButton().setVisible(false);
        tablaProveedoresPM.getUpdateButton().setVisible(false);
        tablaProveedoresPM.getGrid().removeAllColumns();
        tablaProveedoresPM.getGrid().addColumn(PersonasMoralesProveedor::getIdPersonaMoralProveedor).setHeader("ID Proveedor").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(PersonasMoralesProveedor::getRazonSocial).setHeader("Razón Social").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getCalle()).setHeader("Calle").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getCodigoPostal()).setHeader("C.P").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getColonia()).setHeader("Colonia").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getEmail()).setHeader("E-Mail").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getEstado()).setHeader("Estado").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getMunicipio()).setHeader("Municipio").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getNumero()).setHeader("Número").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getRfc()).setHeader("RFC").setAutoWidth(true);
        tablaProveedoresPM.getGrid().addColumn(proveedor -> proveedor.getPersonaProveedor().getTelefono()).setHeader("Telefono").setAutoWidth(true);



        archivoProveedoresPF.setAcceptedFileTypes("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        archivoProveedoresPF.setUploadButton(new Button("Cargar"));
        archivoProveedoresPF.setMaxFiles(1);
        archivoProveedoresPF.setMaxFileSize(104857600);
        archivoProveedoresPF.setDropLabel(new Label("O arrastra tu archivo aquí"));
        archivoProveedoresPF.addFileRejectedListener(event -> {

            Notification notificacionArchivoRechazado = Notification.show("No se admiten archivos con este formato", 3000,
                    Notification.Position.MIDDLE);
            notificacionArchivoRechazado.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        var texto = new Paragraph("Puede importar sus proveedores mediante un archivo de Excel (.xls, .xlsx). El tamaño máximo es de 100 MB");
        var textoAdvertencia = new Paragraph("Recuerde seguir el formato del archivo para que el sistema lea correctamente su importanción");
        archivoProveedoresPF.addSucceededListener(event -> {
            try {

                String nombreArchivo = event.getFileName();
                InputStream datosExcel = buffer.getInputStream(nombreArchivo);
                Workbook excel = WorkbookFactory.create(datosExcel);
                Sheet hoja = excel.getSheetAt(0);
                int inicioDatos = 2;

                if (hoja.getLastRowNum() > 0) {
                    for (int i = inicioDatos; i <= hoja.getLastRowNum(); i++) {

                        Row fila = hoja.getRow(i);

                        PersonaProveedor personaProveedor = new PersonaProveedor();
                        Proveedores proveedor = new Proveedores();
                        proveedor.setPersonaProveedor(personaProveedor);

                        PersonasFisicasProveedor personaFisica = new PersonasFisicasProveedor();
                        personaFisica.setNombre(fila.getCell(1).getStringCellValue());
                        personaFisica.setAPaterno(fila.getCell(2).getStringCellValue());
                        personaFisica.setAMaterno(fila.getCell(3).getStringCellValue());
                        personaFisica.setPersonaProveedor(personaProveedor);

                        personaProveedor.setTelefono(String.valueOf((int)fila.getCell(4).getNumericCellValue()));
                        personaProveedor.setEmail(fila.getCell(5).getStringCellValue());
                        personaProveedor.setRfc(fila.getCell(6).getStringCellValue());
                        personaProveedor.setEstado(fila.getCell(7).getStringCellValue());
                        personaProveedor.setMunicipio(fila.getCell(8).getStringCellValue());
                        personaProveedor.setColonia(fila.getCell(9).getStringCellValue());
                        personaProveedor.setCalle(fila.getCell(10).getStringCellValue());
                        personaProveedor.setNumero(Integer.valueOf((int) fila.getCell(11).getNumericCellValue()));
                        personaProveedor.setCodigoPostal(String.valueOf((int)fila.getCell(12).getNumericCellValue()));

                        personaProveedor.getProveedores().add(proveedor);
                        personaProveedor.getPersonaFisicaProveedor().add(personaFisica);

                        personasFisicasService.add(personaFisica);

                    }
                    excel.close();
                    datosExcel.close();
                    Notification notificacionExito = Notification.show("Procesos agregados con éxito!. Actualize la tabla para ver los items agregados", 2000, Notification.Position.MIDDLE);
                    notificacionExito.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    archivoProveedoresPF.clearFileList();

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

        archivoProveedoresPM.setAcceptedFileTypes("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        archivoProveedoresPM.setUploadButton(new Button("Cargar"));
        archivoProveedoresPM.setMaxFiles(1);
        archivoProveedoresPM.setMaxFileSize(104857600);
        archivoProveedoresPM.setDropLabel(new Label("O arrastra tu archivo aquí"));
        archivoProveedoresPM.addFileRejectedListener(event -> {

            Notification notificacionArchivoRechazado = Notification.show("No se admiten archivos con este formato", 3000,
                    Notification.Position.MIDDLE);
            notificacionArchivoRechazado.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        var textoPM = new Paragraph("Puede importar sus proveedores mediante un archivo de Excel (.xls, .xlsx). El tamaño máximo es de 100 MB");
        var textoAdvertenciaPM = new Paragraph("Recuerde seguir el formato del archivo para que el sistema lea correctamente su importanción");
        archivoProveedoresPM.addSucceededListener(event -> {
                    try {

                        String nombreArchivo = event.getFileName();
                        InputStream datosExcel = buffer.getInputStream(nombreArchivo);
                        Workbook excel = WorkbookFactory.create(datosExcel);
                        Sheet hoja = excel.getSheetAt(0);
                        int inicioDatos = 2;

                        if (hoja.getLastRowNum() > 0) {
                            for (int i = inicioDatos; i <= hoja.getLastRowNum(); i++) {

                                Row fila = hoja.getRow(i);

                                PersonaProveedor personaProveedor = new PersonaProveedor();
                                Proveedores proveedor = new Proveedores();
                                proveedor.setPersonaProveedor(personaProveedor);

                                PersonasMoralesProveedor personaMoral = new PersonasMoralesProveedor();
                                personaMoral.setRazonSocial(fila.getCell(1).getStringCellValue());
                                personaMoral.setPersonaProveedor(personaProveedor);

                                personaProveedor.setTelefono(String.valueOf((int)fila.getCell(2).getNumericCellValue()));
                                personaProveedor.setEmail(fila.getCell(3).getStringCellValue());
                                personaProveedor.setRfc(fila.getCell(4).getStringCellValue());
                                personaProveedor.setEstado(fila.getCell(5).getStringCellValue());
                                personaProveedor.setMunicipio(fila.getCell(6).getStringCellValue());
                                personaProveedor.setColonia(fila.getCell(7).getStringCellValue());
                                personaProveedor.setCalle(fila.getCell(8).getStringCellValue());
                                personaProveedor.setNumero(Integer.valueOf((int) fila.getCell(9).getNumericCellValue()));
                                personaProveedor.setCodigoPostal(String.valueOf((int)fila.getCell(10).getNumericCellValue()));

                                personaProveedor.getProveedores().add(proveedor);
                                personaProveedor.getPersonasMoralesProveedor().add(personaMoral);

                                personasMoralesService.add(personaMoral);
                            }
                            excel.close();
                            datosExcel.close();
                            Notification notificacionExito = Notification.show("Procesos agregados con éxito!. Actualize la tabla para ver los items agregados", 2000, Notification.Position.MIDDLE);
                            notificacionExito.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            archivoProveedoresPM.clearFileList();

                        } else {
                            throw new Exception();
                        }

                    } catch (IOException e) {

                        Notification notificacionError = Notification.show("Error al cargar el archivo", 3000, Notification.Position.MIDDLE);
                        notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);

                    } catch (Exception e) {

                        Notification notificacionError = Notification.show("Asegurese que su archivo no esté vacío y que siga" +
                                " el formato correcto", 3000, Notification.Position.MIDDLE);
                        notificacionError.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        e.printStackTrace();
                    }
                });

        radioGroup.addValueChangeListener(event -> {
           String opcion = radioGroup.getValue();
            layoutTipo.removeAll();
            remove(tablaProveedoresPF, tablaProveedoresPM, texto, textoAdvertencia, archivoProveedoresPF, textoPM, textoAdvertenciaPM, archivoProveedoresPM);
            if(opcion.equals("Persona Física")){
                layoutTipo.add(pf);
                add(tablaProveedoresPF, texto, textoAdvertencia, archivoProveedoresPF);
                
            }else if (opcion.equals("Persona Moral")){
                layoutTipo.add(pm);
                add(tablaProveedoresPM, textoPM, textoAdvertenciaPM, archivoProveedoresPM);
            }
        });

        add(layoutRadioBtn);
        layoutRadioBtn.add(radioGroup);
        add(layoutTipo);

    }

}
