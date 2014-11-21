package com.blackiceinc.account.model;

import javax.persistence.*;


/**
 * Created by Mac on 11/20/2014.
 */
@Entity
@Table(name = "tenant_data_source", schema = "", catalog = "gcd_master")
public class TenantDataSource {
    private Integer intSchemaId;
    private String txtSchemaName;
    private String txtDbUrl;
    private String txtDbUsername;
    private String txtDbPassword;

    @Id
    @Column(name = "intSchemaID", nullable = false, insertable = true, updatable = true)
    public Integer getIntSchemaId() {
        return intSchemaId;
    }

    public void setIntSchemaId(Integer intSchemaId) {
        this.intSchemaId = intSchemaId;
    }

    @Basic
    @Column(name = "txtSchemaName", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTxtSchemaName() {
        return txtSchemaName;
    }

    public void setTxtSchemaName(String txtSchemaName) {
        this.txtSchemaName = txtSchemaName;
    }

    @Basic
    @Column(name = "txtDBUrl", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTxtDbUrl() {
        return txtDbUrl;
    }

    public void setTxtDbUrl(String txtDbUrl) {
        this.txtDbUrl = txtDbUrl;
    }

    @Basic
    @Column(name = "txtDBUsername", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTxtDbUsername() {
        return txtDbUsername;
    }

    public void setTxtDbUsername(String txtDbUsername) {
        this.txtDbUsername = txtDbUsername;
    }

    @Basic
    @Column(name = "txtDBPassword", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTxtDbPassword() {
        return txtDbPassword;
    }

    public void setTxtDbPassword(String txtDbPassword) {
        this.txtDbPassword = txtDbPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TenantDataSource that = (TenantDataSource) o;

        if (intSchemaId != null ? !intSchemaId.equals(that.intSchemaId) : that.intSchemaId != null) return false;
        if (txtDbPassword != null ? !txtDbPassword.equals(that.txtDbPassword) : that.txtDbPassword != null)
            return false;
        if (txtDbUrl != null ? !txtDbUrl.equals(that.txtDbUrl) : that.txtDbUrl != null) return false;
        if (txtDbUsername != null ? !txtDbUsername.equals(that.txtDbUsername) : that.txtDbUsername != null)
            return false;
        if (txtSchemaName != null ? !txtSchemaName.equals(that.txtSchemaName) : that.txtSchemaName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = intSchemaId != null ? intSchemaId.hashCode() : 0;
        result = 31 * result + (txtSchemaName != null ? txtSchemaName.hashCode() : 0);
        result = 31 * result + (txtDbUrl != null ? txtDbUrl.hashCode() : 0);
        result = 31 * result + (txtDbUsername != null ? txtDbUsername.hashCode() : 0);
        result = 31 * result + (txtDbPassword != null ? txtDbPassword.hashCode() : 0);
        return result;
    }
}
