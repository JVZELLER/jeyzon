package br.com.fatecmc.eletivaweb.test.domain;


import java.util.Date;
import br.com.fatecmc.eletivaweb.framework.Formate;

public class Cliente extends PessoaFisica {
    
    private String numeroCliente;
    @Formate( padrao = "yyyy" )
    private Date clienteDesde;

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public Date getClienteDesde() {
        return clienteDesde;
    }

    public void setClienteDesde(Date clienteDesde) {
        this.clienteDesde = clienteDesde;
    }

}
