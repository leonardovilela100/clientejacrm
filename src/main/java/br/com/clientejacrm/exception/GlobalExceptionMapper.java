package br.com.clientejacrm.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.NotFoundException;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        if (exception instanceof NotFoundException) {
            status = Response.Status.NOT_FOUND.getStatusCode();
        } else if (exception instanceof IllegalArgumentException) {
            status = Response.Status.BAD_REQUEST.getStatusCode();
        }
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return Response.status(status).entity(error).build();
    }
}
