package br.sceweb.teste;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.sceweb.modelo.Convenio;
import br.sceweb.modelo.ConvenioDAO;
import br.sceweb.modelo.Empresa;
import br.sceweb.modelo.EmpresaDAO;

public class UC05CadastrarConvenio {
	static ConvenioDAO convenioDAO;
	static Convenio convenio;
	static Convenio novoConvenio;
	static EmpresaDAO empresaDAO;
	static Empresa empresa;
	static String cnpj;
	static List<Convenio> listaDeConvenios;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		listaDeConvenios = new ArrayList<Convenio>();
		empresaDAO = new EmpresaDAO();
		convenioDAO = new ConvenioDAO();
		empresa = new Empresa();
		empresa.setNomeDaEmpresa("empresa x");
		empresa.setCnpj("81965361000174");
		empresa.setNomeFantasia("empresa x");
		empresa.setEndereco("rua taquari");
		empresa.setTelefone("2222");
		empresaDAO.adiciona(empresa);
		
		empresa = new Empresa();
		empresa.setNomeDaEmpresa("empresa x");
		empresa.setCnpj("95117044000102");
		empresa.setNomeFantasia("empresa x");
		empresa.setEndereco("rua taquari");
		empresa.setTelefone("2222");
		empresaDAO.adiciona(empresa);
		//insere 4 convenios para avaliar para conflito de vigencia
		convenio = new Convenio("95117044000102","03/05/2016", "20/05/2016");
		convenioDAO.adiciona(convenio);
		convenio = new Convenio("81965361000174","03/05/2016", "20/05/2016");
		convenioDAO.adiciona(convenio);
		convenio = new Convenio("81965361000174","21/05/2016", "04/06/2016");
		convenioDAO.adiciona(convenio);
		convenio = new Convenio("81965361000174","05/06/2016", "30/06/2016");
	}
	@Test
	public void CT01UC05CadastrarConvenio_com_sucesso() {
		assertEquals(1,convenioDAO.adiciona(convenio));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CT06UC05A3CadastrarConvenio_cnpj_invalido() {
		cnpj = "11111";
		convenio.setCNPJ(cnpj);
	}
	@Test
	public void CT03UC05A2Cadastrar_convenio_dti_invalida(){
		assertFalse(convenio.validaData("42/05/2016"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void CT04UC05A2Cadastrar_convenio_dt_inicial_maior_dt_final(){
		novoConvenio = new Convenio("81965361000174","19/05/2016","04/05/2016");
		
	}
	@Test
	public void CT02UC05A1Cadastrar_convenio_ja_cadastrado(){
		Convenio novoConvenio = new Convenio("81965361000174","04/05/2016", "19/05/2016");
		assertTrue (convenioDAO.verificaVigencia(novoConvenio));
	}
	@Test
	public void CT06UC05A3Cadastrar_convenio_cnpj_nao_cadastrado(){
		Convenio novoConvenio = new Convenio("95702253000113","04/05/2016", "19/05/2016");
		assertEquals(0,convenioDAO.adiciona(novoConvenio));
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		convenioDAO.exclui("81965361000174");
		convenioDAO.exclui("95117044000102");
		empresaDAO.exclui("81965361000174");
		empresaDAO.exclui("95117044000102");
	}
}

