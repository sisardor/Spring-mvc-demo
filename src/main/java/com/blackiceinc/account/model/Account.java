package com.blackiceinc.account.model;

import javax.persistence.*;

/**
 * Created by Mac on 11/20/2014.
 */
@Entity
@Table(name="account")
public class Account {
    private Integer intAccountId;
    private Integer intTenantId;
    private String txtUsername;
    private String txtPassword;
    //private Role role;

    @Id
    @Column(name = "intAccountId", nullable = false, insertable = true, updatable = true)
    public Integer getIntAccountId() {
        return intAccountId;
    }

    public void setIntAccountId(Integer intAccountId) {
        this.intAccountId = intAccountId;
    }

    @Basic
    @Column(name = "intTenantId", nullable = true, insertable = true, updatable = true)
    public Integer getIntTenantId() {
        return intTenantId;
    }

    public void setIntTenantId(Integer intTenantId) {
        this.intTenantId = intTenantId;
    }

    @Basic
    @Column(name = "txtUsername", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(String txtUsername) {
        this.txtUsername = txtUsername;
    }

    @Basic
    @Column(name = "txtPassword", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (intAccountId != null ? !intAccountId.equals(account.intAccountId) : account.intAccountId != null)
            return false;
        if (intTenantId != null ? !intTenantId.equals(account.intTenantId) : account.intTenantId != null) return false;
        if (txtPassword != null ? !txtPassword.equals(account.txtPassword) : account.txtPassword != null) return false;
        if (txtUsername != null ? !txtUsername.equals(account.txtUsername) : account.txtUsername != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = intAccountId != null ? intAccountId.hashCode() : 0;
        result = 31 * result + (intTenantId != null ? intTenantId.hashCode() : 0);
        result = 31 * result + (txtUsername != null ? txtUsername.hashCode() : 0);
        result = 31 * result + (txtPassword != null ? txtPassword.hashCode() : 0);
        return result;
    }
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "account_role", joinColumns = {
//            @JoinColumn(name = "intAccountId", referencedColumnName = "intAccountId") }, inverseJoinColumns = {
//            @JoinColumn(name = "intRoleID", referencedColumnName = "intRoleID") })
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }

	@Override
	public String toString() {
		return "Account [intAccountId=" + intAccountId + ", intTenantId="
				+ intTenantId + ", txtUsername=" + txtUsername
				+ ", txtPassword=" + txtPassword + "]";
	}
    
    
    
}