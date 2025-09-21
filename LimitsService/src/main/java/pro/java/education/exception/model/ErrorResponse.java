package pro.java.education.exception.model;


public record ErrorResponse(String error, String status, String description) {
}