package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.models.*;
import com.springApplication.moduloProduccion.services.ClientesService;
import com.springApplication.moduloProduccion.services.PersonasMoralesClientesService;
import com.springApplication.moduloProduccion.services.PersonasMoralesProveedoresService;
import com.springApplication.moduloProduccion.services.ProveedoresService;
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

@PageTitle("Personas Morales")
@Route(value = "personamorales", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class PersonaMoralView extends VerticalLayout {

    private HorizontalLayout layoutbtn;
    private HorizontalLayout layoutDatos;
    private HorizontalLayout layoutDirec;
    private FormLayout formDirec;
    private TextField razonSocial;
    private IntegerField telefono;
    private TextField estado;
    private TextField municipio;
    private TextField colonia;
    private TextField calle;
    private IntegerField codPostal;
    private IntegerField numero;
    private TextField rfc;
    private EmailField email;

    private Button addPMProveedor;
    private Button addPMCliente;
    private Button deletePF;
    private Button cleanPF;
    private Button saveCliente;
    private Button saveProveedor;
    private Button createPDF;
    private ProveedoresService proveedoresService;
    private PersonasMoralesProveedoresService personasMoralesProveedoresService;
    private ClientesService clientesService;
    private PersonasMoralesClientesService personasMoralesClientesService;

    public PersonaMoralView(PersonasMoralesProveedoresService personasMoralesProveedoresService, ProveedoresService proveedoresService) {

        this.personasMoralesProveedoresService = personasMoralesProveedoresService;
        this.proveedoresService = proveedoresService;
        initComponents();

    }

    public PersonaMoralView(PersonasMoralesClientesService personasMoralesClientesService, ClientesService clientesService) {

        this.personasMoralesClientesService = personasMoralesClientesService;
        this.clientesService = clientesService;
        initComponents();

    }

    private void initComponents() {

        setLayoutbtn();
        setLayoutDatos();
        setLayoutDirec();
        add(getLayoutbtn());
        add(getLayoutDatos());
        add(getLayoutDirec());
        desactivarComponentes();
    }

    private void setLayoutbtn() {

        layoutbtn = new HorizontalLayout();
        addPMProveedor = new Button("Añadir Proveedor", new Icon(VaadinIcon.PLUS_CIRCLE_O));

        addPMProveedor.addClickListener(event -> {

            activarComponentes();
        });


        addPMCliente = new Button("Añadir Cliente", new Icon(VaadinIcon.PLUS_CIRCLE_O));

        addPMCliente.addClickListener(event -> {

            activarComponentes();

        });


        cleanPF = new Button("Limpiar campos", new Icon(VaadinIcon.ERASER));
        cleanPF.addClickListener(event -> {

            limpiarCampos();
        });
        saveCliente = new Button("Guardar Cliente", new Icon(VaadinIcon.FOLDER_O));
        saveCliente.addClickListener(event -> {

            PersonaCliente personaCliente = new PersonaCliente();
            Clientes cliente = new Clientes();
            cliente.setPersonaCliente(personaCliente);

            PersonasMoralesCliente personaMoral = new PersonasMoralesCliente();
            personaMoral.setRazonSocial(razonSocial.getValue());
            personaMoral.setPersonaCliente(personaCliente);

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
            personaCliente.getPersonasMoralesClientes().add(personaMoral);

            personasMoralesClientesService.add(personaMoral);

            Notification.show("Se ha agregado a " + razonSocial.getValue() + " con éxito!", 3000, Notification.Position.MIDDLE);
            limpiarCampos();
            desactivarComponentes();
        });

        saveProveedor = new Button("Guardar Proveedor", new Icon(VaadinIcon.FOLDER_O));
        saveProveedor.addClickListener(event -> {

            PersonaProveedor personaProveedor = new PersonaProveedor();
            Proveedores proveedor = new Proveedores();
            proveedor.setPersonaProveedor(personaProveedor);

            PersonasMoralesProveedor personaMoral = new PersonasMoralesProveedor();
            personaMoral.setRazonSocial(razonSocial.getValue());
            personaMoral.setPersonaProveedor(personaProveedor);

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
            personaProveedor.getPersonasMoralesProveedor().add(personaMoral);

            personasMoralesProveedoresService.add(personaMoral);
            Notification.show("Se ha agregado al proveedor " + razonSocial.getValue() + " exitosamente!", 3000, Notification.Position.MIDDLE);
            limpiarCampos();
            desactivarComponentes();
        });
        layoutbtn.setAlignItems(Alignment.AUTO);
        layoutbtn.add(addPMProveedor, addPMCliente, cleanPF, saveCliente, saveProveedor);
    }

    private void limpiarCampos() {

        razonSocial.setValue("");
        telefono.setValue(null);
        telefono.setInvalid(false);
        email.setValue("");
        rfc.setValue("");
        estado.setValue("");
        municipio.setValue("");
        colonia.setValue("");
        calle.setValue("");
        numero.setValue(null);
        numero.setInvalid(false);
        codPostal.setValue(null);
        codPostal.setInvalid(false);

    }

    private void activarComponentes() {

        razonSocial.setEnabled(true);
        telefono.setEnabled(true);
        email.setEnabled(true);
        rfc.setEnabled(true);
        estado.setEnabled(true);
        municipio.setEnabled(true);
        colonia.setEnabled(true);
        calle.setEnabled(true);
        numero.setEnabled(true);
        codPostal.setEnabled(true);
        cleanPF.setEnabled(true);
        saveCliente.setEnabled(true);
        saveProveedor.setEnabled(true);
        addPMCliente.setEnabled(false);
        addPMProveedor.setEnabled(false);

    }

    private void desactivarComponentes() {

        razonSocial.setEnabled(false);
        telefono.setEnabled(false);
        email.setEnabled(false);
        rfc.setEnabled(false);
        estado.setEnabled(false);
        municipio.setEnabled(false);
        colonia.setEnabled(false);
        calle.setEnabled(false);
        numero.setEnabled(false);
        codPostal.setEnabled(false);
        cleanPF.setEnabled(false);
        saveCliente.setEnabled(false);
        saveProveedor.setEnabled(false);
        addPMProveedor.setEnabled(true);
        addPMCliente.setEnabled(true);
    }

    private void setLayoutDatos() {

        layoutDatos = new HorizontalLayout();
        razonSocial = new TextField("Razón social");
        telefono = new IntegerField("Telefono");
        telefono.setPrefixComponent(VaadinIcon.PHONE.create());
        email = new EmailField("Email");
        email.setPrefixComponent(VaadinIcon.ENVELOPE_O.create());
        email.setPlaceholder("hola@gmail.com");

        layoutDatos.add(razonSocial, telefono, email);
    }

    private void setLayoutDirec() {

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

    public HorizontalLayout getLayoutbtn() {
        return layoutbtn;
    }

    public HorizontalLayout getLayoutDatos() {
        return layoutDatos;
    }

    public HorizontalLayout getLayoutDirec() {
        return layoutDirec;
    }

    public FormLayout getFormDirec() {
        return formDirec;
    }

    public TextField getRazonSocial() {
        return razonSocial;
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

    public EmailField getEmail() {
        return email;
    }

    public Button getAddPMProveedor() {
        return addPMProveedor;
    }

    public Button getAddPMCliente() {
        return addPMCliente;
    }

    public Button getDeletePF() {
        return deletePF;
    }

    public Button getCleanPF() {
        return cleanPF;
    }

    public Button getSaveCliente() {
        return saveCliente;
    }

    public Button getSaveProveedor() {
        return saveProveedor;
    }

    public Button getCreatePDF() {
        return createPDF;
    }

    public ProveedoresService getProveedoresService() {
        return proveedoresService;
    }

    public PersonasMoralesProveedoresService getPersonasMoralesProveedoresService() {
        return personasMoralesProveedoresService;
    }

    public ClientesService getClientesService() {
        return clientesService;
    }

    public PersonasMoralesClientesService getPersonasMoralesClientesService() {
        return personasMoralesClientesService;
    }
}
