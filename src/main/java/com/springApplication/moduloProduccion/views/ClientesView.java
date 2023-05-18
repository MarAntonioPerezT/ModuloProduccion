package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.models.*;
import com.springApplication.moduloProduccion.services.ClientesService;
import com.springApplication.moduloProduccion.services.PersonasFisicasClientesService;
import com.springApplication.moduloProduccion.services.PersonasMoralesClientesService;
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

import javax.swing.text.TabableView;

@PageTitle("Clientes")
@Route(value = "clientes", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ClientesView extends VerticalLayout {

    private HorizontalLayout layoutRadioBtn;
    private HorizontalLayout layoutTipo;
    private RadioButtonGroup<String> radioGroup;
    private PersonaFisicaView pf;
    private PersonaMoralView pm;
    private GridCrud<PersonasFisicasCliente> tablaClientesPF;
    private GridCrud<PersonasMoralesCliente> tablaClientesPM;
    private ClientesService clientesService;
    private PersonasFisicasClientesService personasFisicasService;
    private PersonasMoralesClientesService personasMoralesService;
    private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private Upload archivoClientesPF = new Upload(buffer);
    private Upload archivoClientesPM = new Upload(buffer);
    public ClientesView(PersonasFisicasClientesService personasFisicasService, PersonasMoralesClientesService personasMoralesService, ClientesService clientesService){

        this.clientesService = clientesService;
        this.personasFisicasService = personasFisicasService;
        this.personasMoralesService = personasMoralesService;
        tablaClientesPF = new GridCrud<>(PersonasFisicasCliente.class, personasFisicasService);
        tablaClientesPM = new GridCrud<>(PersonasMoralesCliente.class, personasMoralesService);
        pf = new PersonaFisicaView(personasFisicasService, clientesService);
        pf.getAddPFProveedor().setVisible(false);
        pf.getSaveProveedor().setVisible(false);
        pm = new PersonaMoralView(personasMoralesService, clientesService);
        pm.getAddPMProveedor().setVisible(false);
        pm.getSaveProveedor().setVisible(false);
        layoutRadioBtn = new HorizontalLayout();
        layoutTipo = new HorizontalLayout();
        radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_HELPER_ABOVE_FIELD);
        radioGroup.setLabel("Tipo de cliente que quiere añadir");
        radioGroup.setItems("Persona Física", "Persona Moral");

        tablaClientesPF.getAddButton().setVisible(false);
        tablaClientesPF.getUpdateButton().setVisible(false);
        tablaClientesPF.getGrid().removeAllColumns();
        tablaClientesPF.getGrid().addColumn(PersonasFisicasCliente::getIdPersonaFisicaCliente).setHeader("ID Cliente");
        tablaClientesPF.getGrid().addColumn(PersonasFisicasCliente::getNombre).setHeader("Nombre").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(PersonasFisicasCliente::getAPaterno).setHeader("Apellido Paterno").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(PersonasFisicasCliente::getAMaterno).setHeader("Apellido Paterno").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getCalle()).setHeader("Calle").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getCodigoPostal()).setHeader("C.P").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getColonia()).setHeader("Colonia").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getEmail()).setHeader("E-Mail").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente-> cliente.getPersonaCliente().getEstado()).setHeader("Estado").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getMunicipio()).setHeader("Municipio").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getNumero()).setHeader("Número").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getRfc()).setHeader("RFC").setAutoWidth(true);
        tablaClientesPF.getGrid().addColumn(cliente-> cliente.getPersonaCliente().getTelefono()).setHeader("Telefono").setAutoWidth(true);

        tablaClientesPM.getAddButton().setVisible(false);
        tablaClientesPM.getUpdateButton().setVisible(false);
        tablaClientesPM.getGrid().removeAllColumns();
        tablaClientesPM.getGrid().addColumn(PersonasMoralesCliente::getIdPersonaMoralCliente).setHeader("ID cliente");
        tablaClientesPM.getGrid().addColumn(PersonasMoralesCliente::getRazonSocial).setHeader("Razón Social").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getCalle()).setHeader("Calle").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getCodigoPostal()).setHeader("C.P").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getColonia()).setHeader("Colonia").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getEmail()).setHeader("E-Mail").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente-> cliente.getPersonaCliente().getEstado()).setHeader("Estado").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getMunicipio()).setHeader("Municipio").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getNumero()).setHeader("Número").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente -> cliente.getPersonaCliente().getRfc()).setHeader("RFC").setAutoWidth(true);
        tablaClientesPM.getGrid().addColumn(cliente-> cliente.getPersonaCliente().getTelefono()).setHeader("Telefono").setAutoWidth(true);



        archivoClientesPF.setAcceptedFileTypes("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        archivoClientesPF.setUploadButton(new Button("Cargar"));
        archivoClientesPF.setMaxFiles(1);
        archivoClientesPF.setMaxFileSize(104857600);
        archivoClientesPF.setDropLabel(new Label("O arrastra tu archivo aquí"));
        archivoClientesPF.addFileRejectedListener(event -> {

            Notification notificacionArchivoRechazado = Notification.show("No se admiten archivos con este formato", 3000,
                    Notification.Position.MIDDLE);
            notificacionArchivoRechazado.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        var texto = new Paragraph("Puede importar sus clientes mediante un archivo de Excel (.xls, .xlsx). El tamaño máximo es de 100 MB");
        var textoAdvertencia = new Paragraph("Recuerde seguir el formato del archivo para que el sistema lea correctamente su importanción");
        archivoClientesPF.addSucceededListener(event -> {
            try {

                String nombreArchivo = event.getFileName();
                InputStream datosExcel = buffer.getInputStream(nombreArchivo);
                Workbook excel = WorkbookFactory.create(datosExcel);
                Sheet hoja = excel.getSheetAt(0);
                int inicioDatos = 2;

                if (hoja.getLastRowNum() > 0) {
                    for (int i = inicioDatos; i <= hoja.getLastRowNum(); i++) {

                        Row fila = hoja.getRow(i);
                        PersonaCliente personaCliente = new PersonaCliente();
                        Clientes cliente = new Clientes();
                        cliente.setPersonaCliente(personaCliente);

                        PersonasFisicasCliente personaFisica = new PersonasFisicasCliente();
                        personaFisica.setNombre(fila.getCell(1).getStringCellValue());
                        personaFisica.setAPaterno(fila.getCell(2).getStringCellValue());
                        personaFisica.setAMaterno(fila.getCell(3).getStringCellValue());
                        personaFisica.setPersonaCliente(personaCliente);

                        personaCliente.setTelefono(String.valueOf((int)fila.getCell(4).getNumericCellValue()));
                        personaCliente.setEmail(fila.getCell(5).getStringCellValue());
                        personaCliente.setRfc(fila.getCell(6).getStringCellValue());
                        personaCliente.setEstado(fila.getCell(7).getStringCellValue());
                        personaCliente.setMunicipio(fila.getCell(8).getStringCellValue());
                        personaCliente.setColonia(fila.getCell(9).getStringCellValue());
                        personaCliente.setCalle(fila.getCell(10).getStringCellValue());
                        personaCliente.setNumero(Integer.valueOf((int) fila.getCell(11).getNumericCellValue()));
                        personaCliente.setCodigoPostal(String.valueOf((int)fila.getCell(12).getNumericCellValue()));

                        personaCliente.getClientes().add(cliente);
                        personaCliente.getPersonasFisicasClientes().add(personaFisica);

                        personasFisicasService.add(personaFisica);

                    }
                    excel.close();
                    datosExcel.close();
                    Notification notificacionExito = Notification.show("Clientes agregados con éxito!. Actualize la tabla para ver los items agregados", 2000, Notification.Position.MIDDLE);
                    notificacionExito.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    archivoClientesPF.clearFileList();
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

        archivoClientesPM.setAcceptedFileTypes("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        archivoClientesPM.setUploadButton(new Button("Cargar"));
        archivoClientesPM.setMaxFiles(1);
        archivoClientesPM.setMaxFileSize(104857600);
        archivoClientesPM.setDropLabel(new Label("O arrastra tu archivo aquí"));
        archivoClientesPM.addFileRejectedListener(event -> {

            Notification notificacionArchivoRechazado = Notification.show("No se admiten archivos con este formato", 3000,
                    Notification.Position.MIDDLE);
            notificacionArchivoRechazado.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        var textoPM = new Paragraph("Puede importar sus clientes mediante un archivo de Excel (.xls, .xlsx). El tamaño máximo es de 100 MB");
        var textoAdvertenciaPM = new Paragraph("Recuerde seguir el formato del archivo para que el sistema lea correctamente su importanción");
        archivoClientesPM.addSucceededListener(event -> {
            try {

                String nombreArchivo = event.getFileName();
                InputStream datosExcel = buffer.getInputStream(nombreArchivo);
                Workbook excel = WorkbookFactory.create(datosExcel);
                Sheet hoja = excel.getSheetAt(0);
                int inicioDatos = 2;

                if (hoja.getLastRowNum() > 0) {
                    for (int i = inicioDatos; i <= hoja.getLastRowNum(); i++) {

                        Row fila = hoja.getRow(i);
                        PersonaCliente personaCliente = new PersonaCliente();
                        Clientes cliente = new Clientes();
                        cliente.setPersonaCliente(personaCliente);

                        PersonasMoralesCliente personaMoral = new PersonasMoralesCliente();
                        personaMoral.setRazonSocial(fila.getCell(1).getStringCellValue());
                        personaMoral.setPersonaCliente(personaCliente);

                        personaCliente.setTelefono(String.valueOf((int)fila.getCell(2).getNumericCellValue()));
                        personaCliente.setEmail(fila.getCell(3).getStringCellValue());
                        personaCliente.setRfc(fila.getCell(4).getStringCellValue());
                        personaCliente.setEstado(fila.getCell(5).getStringCellValue());
                        personaCliente.setMunicipio(fila.getCell(6).getStringCellValue());
                        personaCliente.setColonia(fila.getCell(7).getStringCellValue());
                        personaCliente.setCalle(fila.getCell(8).getStringCellValue());
                        personaCliente.setNumero(Integer.valueOf((int) fila.getCell(9).getNumericCellValue()));
                        personaCliente.setCodigoPostal(String.valueOf((int)fila.getCell(10).getNumericCellValue()));

                        personaCliente.getClientes().add(cliente);
                        personaCliente.getPersonasMoralesClientes().add(personaMoral);

                        personasMoralesService.add(personaMoral);
                    }
                    excel.close();
                    datosExcel.close();
                    Notification notificacionExito = Notification.show("Clientes agregados con éxito!. Actualize la tabla para ver los items agregados", 2000, Notification.Position.MIDDLE);
                    notificacionExito.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    archivoClientesPM.clearFileList();
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
            remove(tablaClientesPF, tablaClientesPM, texto, textoAdvertencia, archivoClientesPF, textoPM, textoAdvertenciaPM, archivoClientesPM);
            if(opcion.equals("Persona Física")){
                layoutTipo.add(pf);
                add(tablaClientesPF, texto, textoAdvertencia, archivoClientesPF);

            }else if (opcion.equals("Persona Moral")){
                layoutTipo.add(pm);
                add(tablaClientesPM, textoPM, textoAdvertenciaPM, archivoClientesPM);
            }
        });

        add(layoutRadioBtn);
        layoutRadioBtn.add(radioGroup);
        add(layoutTipo);

    }

}
