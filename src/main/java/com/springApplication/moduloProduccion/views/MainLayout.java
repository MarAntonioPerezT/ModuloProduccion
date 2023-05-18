package com.springApplication.moduloProduccion.views;

import com.springApplication.moduloProduccion.components.appnav.AppNav;
import com.springApplication.moduloProduccion.components.appnav.AppNavItem;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.map.configuration.View;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;


@Route("Menu")
@RolesAllowed("ADMIN")

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.AUTO);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Módulo producción");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.AUTO);
        appName.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        appName.getStyle().set("text-align", "center");
        appName.getStyle().set("padding", "30px 0");
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Órdenes de producción", OrdenesProduccionView.class));
        nav.addItem(new AppNavItem("Boom de materiales", BoomMaterialesView.class));
        nav.addItem(new AppNavItem("Control de producción", ControlProduccionView.class));
        nav.addItem(new AppNavItem("Alta de productos", ProductosView.class));
        nav.addItem(new AppNavItem("Alta de materiales", MaterialesView.class));
        nav.addItem(new AppNavItem("Alta de procesos", AltaProcesosView.class));
        nav.addItem(new AppNavItem("Proveedores", ProveedoresView.class));
        nav.addItem(new AppNavItem("Clientes", ClientesView.class));

    

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
