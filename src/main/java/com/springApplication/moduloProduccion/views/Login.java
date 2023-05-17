package com.springApplication.moduloProduccion.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;

@Route("login")
@RolesAllowed("ADMIN")
public class Login extends Composite<LoginOverlay> {


   public Login(){

        getContent().setTitle("Módulo de Producción");
        getContent().setDescription("");
        getContent().setOpened(true);
        getContent().setAction("login");
    }

}
