package com.example.everycoffee.model;

public class Users
{
    private String a_username, a_pass, m_name, m_username, m_phone, m_email, m_pass;

    public Users()
    {

    }

    public Users(String m_name, String m_username, String m_phone, String m_email, String m_pass)
    {
        this.m_name = m_name;
        this.m_username = m_username;
        this.m_phone = m_phone;
        this.m_email = m_email;
        this.m_pass = m_pass;
    }

    public Users(String a_username, String a_pass, String m_name, String m_username, String m_phone, String m_email, String m_pass)
    {
        this.a_username = a_username;
        this.a_pass = a_pass;
        this.m_name = m_name;
        this.m_username = m_username;
        this.m_phone = m_phone;
        this.m_email = m_email;
        this.m_pass = m_pass;
    }

    public String getA_username()
    {
        return a_username;
    }

    public void setA_username(String a_username)
    {
        this.a_username = a_username;
    }

    public String getA_pass()
    {
        return a_pass;
    }

    public void setA_pass(String a_pass)
    {
        this.a_pass = a_pass;
    }

    public String getM_name()
    {
        return m_name;
    }

    public void setM_name(String m_name)
    {
        this.m_name = m_name;
    }

    public String getM_username()
    {
        return m_username;
    }

    public void setM_username(String m_username)
    {
        this.m_username = m_username;
    }

    public String getM_phone()
    {
        return m_phone;
    }

    public void setM_phone(String m_phone)
    {
        this.m_phone = m_phone;
    }

    public String getM_email()
    {
        return m_email;
    }

    public void setM_email(String m_email)
    {
        this.m_email = m_email;
    }

    public String getM_pass()
    {
        return m_pass;
    }

    public void setM_pass(String m_pass)
    {
        this.m_pass = m_pass;
    }
}
