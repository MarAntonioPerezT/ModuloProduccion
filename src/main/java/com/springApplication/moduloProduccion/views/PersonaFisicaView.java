package com.springApplication.moduloProduccion.views;


import com.springApplication.moduloProduccion.models.*;
import com.springApplication.moduloProduccion.services.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Personas Fisicas")
@Route(value = "personafisicas", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class PersonaFisicaView extends VerticalLayout {

    private HorizontalLayout layoutbtn;
    private HorizontalLayout layoutDatos;
    private HorizontalLayout layoutDirec;
    private FormLayout formDirec;
    private TextField nombre;
    private TextField aPaterno;
    private TextField aMaterno;
    private  IntegerField telefono;
    private TextField estado;
    private TextField municipio;
    private TextField colonia;
    private TextField calle;
    private IntegerField codPostal;
    private IntegerField numero;
    private TextField rfc;
    private EmailField email;
    private Button addPFProveedor;
    private Button addPFCliente;
    private  Button deletePF;
    private Button cleanPF;
    private Button savePF;
    private Button createPDF;
    private PersonasFisicasProveedoresService proveedorPersonaFisicaService;
    private ProveedoresService proveedoresService;
    private ClientesService clientesService;
    private PersonasFisicasClientesService clientePersonaFisicaService;

    public PersonaFisicaView(PersonasFisicasClientesService clientePersonaFisicaService, ClientesService clientesService){

        this.clientePersonaFisicaService = clientePersonaFisicaService;
        this.clientesService = clientesService;
        initComponents();

    }

    public PersonaFisicaView (PersonasFisicasProveedoresService proveedorPersonaFisicaService, ProveedoresService proveedoresService){

        this.proveedorPersonaFisicaService = proveedorPersonaFisicaService;
        this.proveedoresService = proveedoresService;
        initComponents();

    }

    private void initComponents() {

        setLayoutbtn();
        setLayoutDatos();
        setLayoutDirec();
        add(getLayoutbtn());
        add(getLayoutDatos());
        add(getLayoutDirec());
    }

    private void setLayoutbtn(){

        layoutbtn = new HorizontalLayout();
        addPFProveedor = new Button("Añadir Proveedor", new Icon(VaadinIcon.PLUS_CIRCLE_O));
        addPFProveedor.addClickListener(event -> {

            PersonaProveedor personaProveedor = new PersonaProveedor();
            Proveedores proveedor = new Proveedores();
            proveedor.setPersonaProveedor(personaProveedor);

            PersonasFisicasProveedor personaFisica = new PersonasFisicasProveedor();
            personaFisica.setNombre(nombre.getValue());
            personaFisica.setAPaterno(aPaterno.getValue());
            personaFisica.setAMaterno(aMaterno.getValue());
            personaFisica.setPersonaProveedor(personaProveedor);

            personaProveedor.setCalle(calle.getValue());
            personaProveedor.setCodigoPostal(String.valueOf(codPostal.getValue()));
            personaProveedor.setColonia(colonia.getValue());
            personaProveedor.setEmail(email.getValue());
            personaProveedor.setEstado(estado.getValue());
            personaProveedor.setMunicipio(municipio.getValue());
            personaProveedor.setNumero(Integer.valueOf(numero.getValue()));
            personaProveedor.setRfc(rfc.getValue());
            personaProveedor.setTelefono(String.valueOf(telefono.getValue()));
            personaProveedor.getProveedores().add(proveedor);
            personaProveedor.getPersonaFisicaProveedor().add(personaFisica);

            proveedorPersonaFisicaService.add(personaFisica);

            Notification.show("Se ha agregado a " + nombre.getValue() + " con éxito!", 3000, Notification.Position.MIDDLE);
            limpiarCampos();

        });

        addPFCliente = new Button("Añadir Cliente", new Icon(VaadinIcon.PLUS_CIRCLE_O));
        addPFCliente.addClickListener(event ->{

            PersonaCliente personaCliente = new PersonaCliente();
            Clientes cliente = new Clientes();
            cliente.setPersonaCliente(personaCliente);

            PersonasFisicasCliente personaFisica = new PersonasFisicasCliente();
            personaFisica.setNombre(nombre.getValue());
            personaFisica.setAPaterno(aPaterno.getValue());
            personaFisica.setAMaterno(aMaterno.getValue());
            personaFisica.setPersonaCliente(personaCliente);

            personaCliente.setCalle(calle.getValue());
            personaCliente.setCodigoPostal(String.valueOf(codPostal.getValue()));
            personaCliente.setColonia(colonia.getValue());
            personaCliente.setEmail(email.getValue());
            personaCliente.setEstado(estado.getValue());
            personaCliente.setMunicipio(municipio.getValue());
            personaCliente.setNumero(Integer.valueOf(numero.getValue()));
            personaCliente.setRfc(rfc.getValue());
            personaCliente.setTelefono(String.valueOf(telefono.getValue()));
            personaCliente.getClientes().add(cliente);
            personaCliente.getPersonasFisicasClientes().add(personaFisica);

            clientePersonaFisicaService.add(personaFisica);

            Notification.show("Se ha agregado a " + nombre.getValue() + " con éxito!", 3000, Notification.Position.MIDDLE);

        });

        cleanPF = new Button("Limpiar campos", new Icon(VaadinIcon.ERASER));
        cleanPF.addClickListener(event ->{
            limpiarCampos();

        });
        savePF = new Button("Guardar", new Icon(VaadinIcon.FOLDER_O));
        layoutbtn.setAlignItems(Alignment.AUTO);
        layoutbtn.add(addPFProveedor, addPFCliente, cleanPF, savePF);
    }
    private void setLayoutDatos(){

        layoutDatos = new HorizontalLayout();
        nombre = new TextField("Nombre");
        aPaterno = new TextField("Apellido Paterno");
        aMaterno = new TextField("Apelldio Materno");
        telefono = new IntegerField("Telefono");
        telefono.setPrefixComponent(VaadinIcon.PHONE.create());
        email = new EmailField("Email");
        email.setPrefixComponent(VaadinIcon.ENVELOPE_O.create());
        email.setPlaceholder("hola@gmail.com");

        layoutDatos.add(nombre, aPaterno, aMaterno, telefono, email);
    }

    private void setLayoutDirec(){

        layoutDirec = new HorizontalLayout();
        estado = new TextField("Estado");
        municipio = new TextField("Municipio");
        colonia = new TextField("Colonia");
        calle = new TextField("Calle");
        numero = new IntegerField("No.");
        codPostal = new IntegerField("Código Postal");
        rfc = new TextField("RFC");
        formDirec = new FormLayout();
        formDirec.add(rfc, estado, municipio, colonia, calle, numero, codPostal);
        formDirec.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
        layoutDirec.add(formDirec);

    }
    private HorizontalLayout getLayoutbtn(){
        return layoutbtn;
    }
    private HorizontalLayout getLayoutDatos(){
        return layoutDatos;
    }

    private HorizontalLayout getLayoutDirec(){
        return layoutDirec;
    }

    private void limpiarCampos(){

        nombre.setValue("");
        aPaterno.setValue("");
        aMaterno.setValue("");
        telefono.setValue(null);
        email.setValue("");
        rfc.setValue("");
        estado.setValue("");
        municipio.setValue("");
        colonia.setValue("");
        calle.setValue("");
        numero.setValue(null);
        codPostal.setValue(null);

    }

    public TextField getNombre() {
        return nombre;
    }

    public TextField getaPaterno() {
        return aPaterno;
    }

    public TextField getaMaterno() {
        return aMaterno;
    }

    public IntegerField getTelefono() {
        return telefono;
    }

    public TextField getEstado() {
        return estado;
    }

    public TextField getMunicipio() {
        return municipio;
    }

    public TextField getColonia() {
        return colonia;
    }

    public TextField getCalle() {
        return calle;
    }

    public IntegerField getCodPostal() {
        return codPostal;
    }

    public IntegerField getNumero() {
        return numero;
    }

    public TextField getRfc() {
        return rfc;
    }

    public FormLayout getFormDirec() {
        return formDirec;
    }

    public Button getAddPFProveedor() {
        return addPFProveedor;
    }

    public Button getAddPFCliente() {
        return addPFCliente;
    }

    public Button getDeletePF() {
        return deletePF;
    }

    public Button getCleanPF() {
        return cleanPF;
    }

    public Button getSavePF() {
        return savePF;
    }

    public Button getCreatePDF() {
        return createPDF;
    }

    public ProveedoresService getProveedoresService() {
        return proveedoresService;
    }

    public ClientesService getClientesService() {
        return clientesService;
    }

    public EmailField getEmail() {
        return email;
    }
}
