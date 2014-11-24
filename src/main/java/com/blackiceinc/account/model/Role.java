package com.blackiceinc.account.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Mac on 11/20/2014.
 */
@Entity
@Table(name="role")
public class Role {
    private Integer intRoleId;
    private String txtRoleName;

    @Id @GeneratedValue
    @Column(name = "intRoleID", nullable = false, insertable = true, updatable = true)
    public Integer getIntRoleId() {
        return intRoleId;
    }

    public void setIntRoleId(Integer intRoleId) {
        this.intRoleId = intRoleId;
    }

    @Basic
    @Column(name = "txtRoleName", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTxtRoleName() {
        return txtRoleName;
    }

    public void setTxtRoleName(String txtRoleName) {
        this.txtRoleName = txtRoleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (intRoleId != null ? !intRoleId.equals(role.intRoleId) : role.intRoleId != null) return false;
        if (txtRoleName != null ? !txtRoleName.equals(role.txtRoleName) : role.txtRoleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = intRoleId != null ? intRoleId.hashCode() : 0;
        result = 31 * result + (txtRoleName != null ? txtRoleName.hashCode() : 0);
        return result;
    }
}
