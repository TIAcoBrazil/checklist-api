package com.fleet.fleet.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Server Error", details, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Erro no corpo da requisição.", details, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDriverNotFoundException(DriverNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Motorista não encontrado na base dados.", details, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleOpenChecklistExists(OpenChecklistExistsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Existe uma checklist em aberto para este veículo.", details, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleVehicleNotFoundException(VehicleNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Carro não encontrado na base de dados.", details, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleBlockedVehicleException(BlockedVehicleException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Veículo encontra-se bloqueado.", details, HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handlePendingChecklistException(PendingChecklistException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Existe uma checklist pendente para este veículo", details, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUploadPhotoException(UploadPhotoException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Erro ao salvar foto.", details, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleCargoValidationException(CargoValidationException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ServerResponse error = new ServerResponse("Erro de validação de carregamento", details, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
