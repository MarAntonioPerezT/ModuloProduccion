package com.springApplication.moduloProduccion.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.springApplication.moduloProduccion.models.Productos;
import com.springApplication.moduloProduccion.services.ProductosService;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Productos")
@Route(value = "productos", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ProductosView extends VerticalLayout{

    private TextField nombre = new TextField("Nombre");
    private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private Upload subirArchivo = new Upload(buffer);

    public ProductosView(ProductosService service){

        var crud = new GridCrud<>(Productos.class, service);

        nombre.setPlaceholder("Filtro por nombre");
        nombre.setClearButtonVisible(true);
        nombre.setValueChangeMode(ValueChangeMode.LAZY);
        nombre.addValueChangeListener(e -> crud.getGrid().setItems(service.findAllProductos(nombre.getValue())));
        
        
        HorizontalLayout toolBar = new HorizontalLayout(nombre);

        crud.getGrid().removeAllColumns();
        crud.getGrid().addColumn(Productos::getCodigo_producto).setHeader("Código").setAutoWidth(true);
        crud.getGrid().addColumn(Productos::getNombre).setHeader("Nombre producto").setAutoWidth(true);
        crud.getGrid().addColumn(Productos::getDescripcion).setHeader("Descripción");
        crud.getGrid().addColumn(Productos::getPrecioVenta).setHeader("Precio de venta").setAutoWidth(true);
        crud.getCrudFormFactory().setVisibleProperties("nombre", "descripcion", "precioVenta");
        crud.setUpdateOperationVisible(false);
        crud.getAddButton().setTooltipText("Añadir producto");
        crud.getAddButton().setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
        crud.getDeleteButton().setTooltipText("Eliminar producto");
        crud.getFindAllButton().setTooltipText("Refrescar tabla");
        crud.getCrudLayout().addToolbarComponent(toolBar);

        subirArchivo.setAcceptedFileTypes("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        subirArchivo.setUploadButton(new Button("Cargar"));
        subirArchivo.setMaxFiles(1);
        subirArchivo.setMaxFileSize(104857600);
        subirArchivo.setDropLabel(new Label("O arrastra tu archivo aquí"));
        subirArchivo.addFileRejectedListener(event -> {

            Notification notificacionArchivoRechazado = Notification.show("No se admiten archivos con este formato", 3000,
                    Notification.Position.MIDDLE);
            notificacionArchivoRechazado.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        var texto = new Paragraph("Puede importar los productos mediante un archivo de Excel (.xls, .xlsx). El tamaño máximo es de 100 MB");
        var textoAdvertencia = new Paragraph("Recuerde seguir el formato del archivo para que el sistema lea correctamente su importanción");
        subirArchivo.addSucceededListener(event -> {
            try {

                String nombreArchivo = event.getFileName();
                InputStream datosExcel = buffer.getInputStream(nombreArchivo);
                Workbook excel = WorkbookFactory.create(datosExcel);
                Sheet hoja = excel.getSheetAt(0);
                int inicioDatos = 2;
                List<Productos> listaProductos = new ArrayList<>();

                if (hoja.getLastRowNum() > 0) {
                    for (int i = inicioDatos; i <= hoja.getLastRowNum(); i++) {

                        Row fila = hoja.getRow(i);
                        Productos productos = new Productos();
                        productos.setNombre(fila.getCell(1).getStringCellValue());
                        productos.setDescripcion(fila.getCell(2).getStringCellValue());
                        productos.setPrecioVenta(fila.getCell(3).getNumericCellValue());
                        listaProductos.add(productos);

                    }
                    excel.close();
                    datosExcel.close();
                    service.saveAllProductos(listaProductos);
                    Notification notificacionExito = Notification.show("Productos agregados con éxito!. Actualize la tabla para ver los items agregados", 2000, Notification.Position.MIDDLE);
                    notificacionExito.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    subirArchivo.clearFileList();
                    listaProductos.clear();
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

            }

        });
        add(
                crud, texto, textoAdvertencia, subirArchivo
        );
        



    }
  
}

