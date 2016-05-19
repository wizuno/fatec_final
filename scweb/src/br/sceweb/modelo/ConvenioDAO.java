package br.sceweb.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.sceweb.servico.FabricaDeConexoes;

/**
 * Manipula o banco de dados para manter as informações de convenio
 * 
 * @author edson
 *
 */
public class ConvenioDAO {
	Logger logger = Logger.getLogger(ConvenioDAO.class);

	/**
	 * inclui um convenio na base de dados verifica se existe conflito no
	 * periodo de vigencia do convenio.
	 * 
	 * @param convenio
	 * @return - retorna a quantidade de registros afetados no banco de dados
	 */
	public int adiciona(Convenio convenio) {
		PreparedStatement ps = null;
		Connection conn = null;
		int codigoRetorno = 0;
		if (!verificaVigencia(convenio)) {
			try {
				conn = new FabricaDeConexoes().getConnection();
				ps = (PreparedStatement) conn
						.prepareStatement("insert into convenio (id, empresa_cnpj, dataInicio, dataFim) values(?,?,?,?)");
				ps.setNull(1, Types.INTEGER);
				ps.setString(2, convenio.getCNPJ());
				ps.setString(3, convenio.getDataInicio().toString("YYYY-MM-dd"));
				ps.setString(4, convenio.getDataTermino().toString("YYYY-MM-dd"));
				codigoRetorno = ps.executeUpdate();
				logger.info("codigo de retorno do metodo adiciona convenio = " + codigoRetorno);

			} catch (Exception e) {
				logger.info("convenioDAO metodo adiciona exception -" + e.getMessage());
				try {
					conn.rollback();
				} catch (SQLException ex) {
					logger.info("convenioDAO SQLException -" + e.getMessage());  //CT06UC05A3Cadastrar_convenio_cnpj_nao_cadastrado
				}
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e1) {
						logger.info("convenioDAO metodo adiciona -" + e1.getStackTrace());
					}
				}
			}
		}
		return codigoRetorno;
	}

	/**
	 * exclui convenio
	 * 
	 * @param cnpj
	 * @return - retorna o resultado do mysql quantidade de registros afetados
	 */
	public int exclui(String cnpj) {
		java.sql.PreparedStatement ps;
		int codigoretorno = 0;
		try (Connection conn = new FabricaDeConexoes().getConnection()) {
			ps = conn.prepareStatement("delete from convenio where empresa_cnpj = ?");
			ps.setString(1, cnpj);
			codigoretorno = ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return codigoretorno;

	}

	/**
	 * consulta os convenios ja cadastrados para este cnpj
	 * 
	 * @param cnpj
	 * @return - um arralist com os convenios encontrados, retorna null se nao
	 *         achar convenio para este cnpj
	 */
	public List<Convenio> consultaConvenio(String cnpj) {
		List<Convenio> listaDeConvenios = new ArrayList<Convenio>();
		Convenio convenio = null;
		java.sql.PreparedStatement ps;
		try (Connection conn = new FabricaDeConexoes().getConnection()) {
			ps = conn.prepareStatement("select * from convenio where empresa_cnpj = ?");
			ps.setString(1, cnpj);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				String dtInicioAno = resultSet.getString("dataInicio").substring(0, 4);
				String dtInicioMes = resultSet.getString("dataInicio").substring(5, 7);
				String dtInicioDia = resultSet.getString("dataInicio").substring(8, 10);
				String dataI = dtInicioDia + "/" + dtInicioMes + "/" + dtInicioAno;
				String dtTerminoAno = resultSet.getString("dataFim").substring(0, 4);
				String dtTerminoMes = resultSet.getString("dataFim").substring(5, 7);
				String dtTerminoDia = resultSet.getString("dataFim").substring(8, 10);
				String dataT = dtTerminoDia + "/" + dtTerminoMes + "/" + dtTerminoAno;
				convenio = new Convenio(resultSet.getInt(1),resultSet.getString("empresa_cnpj"), dataI, dataT);
				listaDeConvenios.add(convenio);

			}
			logger.info("consultaConvenio achou " + listaDeConvenios.size() + " registros");
			resultSet.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return listaDeConvenios;
	}

	/**
	 * Objetivo - obtem uma lista de convenios ja cadastrados para este cnpj
	 * verifica se existem convenios cadastrados para este cnpj onde a
	 * dataTermino ou dataInicio esta entre a data de inicio e a data de termino dos convenios
	 * ja cadastrados
	 * 
	 * @param novoConvenio
	 *            - novo convenio que esta sendo cadastrado para este cnpj
	 * @return true - existe convenio cadastrado no periodo de vigencia false -
	 *         nao existe convenio cadastrado
	 */
	public boolean verificaVigencia(Convenio novoConvenio) {
		List<Convenio> listaDeConvenios = new ArrayList<Convenio>();
		listaDeConvenios = consultaConvenio(novoConvenio.getCNPJ());
		if (listaDeConvenios.size() > 0) {// existe convenio para este cnpj
			for (Convenio convenio : listaDeConvenios) {
				Interval interval = new Interval(convenio.getDataInicio(), convenio.getDataTermino());
				logger.info("verificaVigencia entre " + convenio.getDataInicio() + " - " + convenio.getDataTermino());
				if (interval.contains(novoConvenio.getDataTermino())) {
					logger.info("verificaVigencia achou conflito de datas para data termino ");
					return true;// achou conflito no periodo de vigencia
				}
				if (interval.contains(novoConvenio.getDataInicio())) {
					logger.info("verificaVigencia achou conflito de datas para data inicio ");
					return true;// achou conflito no periodo de vigencia
				}
			}
		}
		return false; // nao achou conflito no periodo de vingencia
	}
	
}
