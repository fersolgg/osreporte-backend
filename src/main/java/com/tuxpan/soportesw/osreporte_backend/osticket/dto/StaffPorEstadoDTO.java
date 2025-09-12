package com.tuxpan.soportesw.osreporte_backend.osticket.dto;

public class StaffPorEstadoDTO {
    private Long staffId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

    public StaffPorEstadoDTO(Long staffId, String username, String firstname, String lastname, String email) {
        this.staffId = staffId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
