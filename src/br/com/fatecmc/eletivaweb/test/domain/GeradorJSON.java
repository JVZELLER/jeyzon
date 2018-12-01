package br.com.fatecmc.eletivaweb.test.domain;

import java.util.Date;

import br.com.fatecmc.eletivaweb.framework.Jeyzon;

public class GeradorJSON {

    public static void main (String [] args) {
        Cliente cli = getCliente();
        Jeyzon jeyzon = new Jeyzon();
        String cliente = jeyzon.toString(cli);
        System.out.println(cliente);

    }

    private static Cliente getCliente() {
        Estado estadoDoRG1 = new Estado();
        estadoDoRG1.setSigla("SP");
        estadoDoRG1.setNome("Sao Paulo");

        RG rgDoCliente1 = new RG();
        rgDoCliente1.setNumero("123.456.789");
        rgDoCliente1.setOrgaoExpeditor("SSP");
        rgDoCliente1.setEstadoExpeditor(estadoDoRG1);

        Estado estadoDoRG2 = new Estado();
        estadoDoRG2.setSigla("MG");
        estadoDoRG2.setNome("Minas Gerais");

        RG rgDoCliente2 = new RG();
        rgDoCliente2.setNumero("123.456.780");
        rgDoCliente2.setOrgaoExpeditor("SSP");
        rgDoCliente2.setEstadoExpeditor(estadoDoRG2);

        Cliente cli = new Cliente();
        cli.setNome("Joao da Silva");
        cli.setNumeroCliente("12345");
        cli.setClienteDesde(new Date());
        cli.setRg(new RG[]{rgDoCliente1, rgDoCliente2});
        // Para testar recursao infinita de Objetos
        rgDoCliente1.setPessoa(cli);
        return cli;
    }

}
