package com.addi.sales_pipeline.infraestructure.rest_controller;

import com.addi.sales_pipeline.application.ConvertLeadIntoProspect;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;

@Path("/person")
public class PersonResource {

    private final ConvertLeadIntoProspect convertLeadIntoProspect;

    @Inject
    public PersonResource(ConvertLeadIntoProspect convertLeadIntoProspect) {
        this.convertLeadIntoProspect = convertLeadIntoProspect;
    }

    @PATCH
    @Path("/lead/{nationalIdentificationNumber}")
    public Uni<Void> convertIntoProspect(@PathParam("nationalIdentificationNumber") Long nationalIdentificationNumber) {
        return convertLeadIntoProspect.execute(nationalIdentificationNumber);
    }
}
