package br.sceweb.teste;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.sceweb.modelo.Convenio;
import br.sceweb.modelo.ConvenioDAO;
import br.sceweb.modelo.Empresa;
import br.sceweb.modelo.EmpresaDAO;

public class UC06ConsultarConvenio {
	static ConvenioDAO convenioDAO;
	static Convenio resultadoEsperado;
	static Convenio resultadoObtido;
	static Convenio convenio;
	static EmpresaDAO empresaDAO;
	static Empresa empresa;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		empresaDAO = new EmpresaDAO();
		convenioDAO = new ConvenioDAO();
		empresa = new Empresa();
		empresa.setNomeDaEmpresa("empresa x");
		empresa.setCnpj("27378275000110");
		empresa.setNomeFantasia("empresa x");
		empresa.setEndereco("rua taquari");
		empresa.setTelefone("2222");
		empresaDAO.adiciona(empresa);
		convenio = new Convenio("27378275000110","03/05/2016", "20/05/2016");
		convenioDAO.adiciona(convenio);
	}
	@Test
	public void CT01UC06ConsultarConvenio_por_cnpj() {
		List<Convenio> listaDeConvenios = new ArrayList<Convenio>();
		listaDeConvenios = convenioDAO.consultaConvenio("27378275000110");
		resultadoEsperado = new Convenio("27378275000110","03/05/2016", "20/05/2016");
		resultadoObtido = listaDeConvenios.get(0);
		assertTrue (resultadoEsperado.equals(resultadoObtido));
		resultadoEsperado = new Convenio("27378275000110","20/05/2016","03/05/2016");
		resultadoEsperado.dataInicialMaiorDataFinal();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		convenioDAO.exclui("27378275000110");
		empresaDAO.exclui("27378275000110");
	}

	
}
