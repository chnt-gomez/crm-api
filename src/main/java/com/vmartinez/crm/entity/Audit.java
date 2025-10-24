package com.vmartinez.crm.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table (name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String method; // GET, POST, PUT, DELETE, etc.

    @Column(nullable = false)
    private String path; // The endpoint path

    @Column(nullable = false)
    private int status; // HTTP response code

    @Column(nullable = false)
    private Instant timestamp;

    @Column(length = 2000)
    private String requestBody; // Optional, store as string

    @Column(length = 2000)
    private String responseBody; // Optional, store as string

    protected Audit () {};

    public Audit( String method, String path, int status, Instant timestamp, String requestBody, String responseBody) {
        this.method = method;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

}
