package com.springApplication.moduloProduccion.util.runnable;

import com.springApplication.moduloProduccion.models.EstadoOrden;
import com.springApplication.moduloProduccion.models.EstadoProceso;
import com.springApplication.moduloProduccion.repositories.EstadoOrdenRepository;
import com.springApplication.moduloProduccion.repositories.EstadoProcesoRepository;
import com.springApplication.moduloProduccion.services.EstadoOrdenService;
import com.springApplication.moduloProduccion.services.EstadoProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultDataConfig {

    @Autowired
    private final EstadoProcesoService estadoProcesoService;
    @Autowired
    private final EstadoOrdenService estadoOrdenService;

    private EstadoProceso estadoProceso;
    private EstadoOrden estadoOrden;


    public DefaultDataConfig(EstadoProcesoService estadoProcesoService, EstadoOrdenService estadoOrdenService){

        this.estadoOrdenService = estadoOrdenService;
        this.estadoProcesoService = estadoProcesoService;

    }

    @Bean
    public CommandLineRunner initializeDefaultData(){

        return args ->{

            String enEspera = "En espera";
            EstadoOrden estadoOrdenEnEspera = estadoOrdenService.findEstatusByNombreEstado(enEspera);
            if (estadoOrdenEnEspera == null) {
                estadoOrden = new EstadoOrden();
                estadoOrden.setEstado(enEspera);
                estadoOrdenService.saveEstadoOrden(estadoOrden);

                estadoProceso = new EstadoProceso();
                estadoProceso.setNombre(enEspera);
                estadoProcesoService.saveEstadoProceso(estadoProceso);
            }

            String enProceso = "En proceso";
            EstadoOrden estadoOrdenEnProceso = estadoOrdenService.findEstatusByNombreEstado(enProceso);
            if (estadoOrdenEnProceso == null) {
                estadoOrden = new EstadoOrden();
                estadoOrden.setEstado(enProceso);
                estadoOrdenService.saveEstadoOrden(estadoOrden);

                estadoProceso = new EstadoProceso();
                estadoProceso.setNombre(enProceso);
                estadoProcesoService.saveEstadoProceso(estadoProceso);
            }

            String terminado = "Terminado";
            EstadoOrden estadoOrdenTerminado = estadoOrdenService.findEstatusByNombreEstado(terminado);
            if (estadoOrdenTerminado == null) {
                estadoOrden = new EstadoOrden();
                estadoOrden.setEstado(terminado);
                estadoOrdenService.saveEstadoOrden(estadoOrden);

                estadoProceso = new EstadoProceso();
                estadoProceso.setNombre(terminado);
                estadoProcesoService.saveEstadoProceso(estadoProceso);
            }

            String cancelado = "Cancelado";
            EstadoOrden estadoOrdenCancelado = estadoOrdenService.findEstatusByNombreEstado(cancelado);
            if (estadoOrdenCancelado == null) {
                estadoOrden = new EstadoOrden();
                estadoOrden.setEstado(cancelado);
                estadoOrdenService.saveEstadoOrden(estadoOrden);
            }

        };
    }

}



