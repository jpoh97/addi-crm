package com.addi.sales_pipeline.infraestructure;

import com.addi.sales_pipeline.domain.exception.DomainException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SalesPipelineExceptionHandler implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
