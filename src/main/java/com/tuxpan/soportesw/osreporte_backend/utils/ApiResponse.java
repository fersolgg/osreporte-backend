// Clase genérica para respuestas estándar de la API
package com.tuxpan.soportesw.osreporte_backend.utils;

public class ApiResponse<T> {
    // Datos devueltos por la API
    private T data;
    // Mensaje informativo o de éxito
    private String message;
    // Mensaje de error (si aplica)
    private String error;

    // Constructor vacío
    public ApiResponse() {}

    // Constructor con todos los campos
    public ApiResponse(T data, String message, String error) {
        this.data = data;
        this.message = message;
        this.error = error;
    }

    // Métodos getter y setter para cada campo
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}