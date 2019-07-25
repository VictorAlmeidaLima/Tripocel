package br.com.sankhya.bh.Model;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import com.sankhya.util.TimeUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

public class CaixaDiario {

    //JapeWrapper
    private JapeWrapper cxDiarioDao;
    private JapeWrapper financeiroDAO;
    private JapeWrapper modeloDAO;

    //DynamicVO
    private DynamicVO cxDiarioVO;

    //Variaveis
    String nomeEntidade = "AD_CXDIARIO";


    //Construtor
    public CaixaDiario(){
        cxDiarioDao = JapeFactory.dao(nomeEntidade);
        financeiroDAO = JapeFactory.dao("Financeiro");
        modeloDAO = JapeFactory.dao("AD_TGFFINMOD");
    }

    //Métodos
    public void setCxDiarioVO(BigDecimal nuCx) throws Exception {
        cxDiarioVO = cxDiarioDao.findOne("NUCAIXA = ?",nuCx);
    }

    public void gerarFinanceiro(ContextoAcao contextoAcao) throws Exception {

        Registro[] linhas = contextoAcao.getLinhas();
        Registro linhaPai = contextoAcao.getLinhaPai();

        for (Registro linha : linhas) {
            if(linha.getCampo("NUFIN")==null) {
                DynamicVO modVO = modeloDAO.findOne("NUFIN = ?", new BigDecimal(1));

                Registro financeiro = contextoAcao.novaLinha("Financeiro");

                //Valores Variáveis - Vem da Tela Modelo Financeiro
                financeiro.setCampo("CODPARC", modVO.asBigDecimal("CODPARC"));
                financeiro.setCampo("CODNAT", modVO.asBigDecimal("CODNAT"));
                financeiro.setCampo("CODCENCUS", modVO.asBigDecimal("CODCENCUS"));
                financeiro.setCampo("CODTIPOPER", modVO.asBigDecimal("CODTIPOPER"));
                financeiro.setCampo("CODBCO",  modVO.asBigDecimal("CODBCO"));
                financeiro.setCampo("CODCTABCOINT",  modVO.asBigDecimal("CODCTABCOINT"));
                financeiro.setCampo("PROVISAO", modVO.asString("PROVISAO"));

                financeiro.setCampo("CODTIPTIT", linha.getCampo("CODTIPTIT"));
                financeiro.setCampo("CODEMP", linhaPai.getCampo("CODEMP"));
                financeiro.setCampo("DTNEG", TimeUtils.getNow());
                financeiro.setCampo("NUMNOTA", new BigDecimal(0));
                financeiro.setCampo("DESDOBRAMENTO","1");
                financeiro.setCampo("DTVENC", TimeUtils.getNow());
                financeiro.setCampo("VLRDESDOB", linha.getCampo("VLRLANC"));
                financeiro.setCampo("RECDESP", new BigDecimal((String) linha.getCampo("RECDESP")));
                financeiro.setCampo("ORIGEM", "F");

                financeiro.save();

                linha.setCampo("NUFIN", financeiro.getCampo("NUFIN"));
            }else{
                contextoAcao.mostraErro("Ja foi gerado financeiro para este lançamento.");
            }
        }
        contextoAcao.setMensagemRetorno("Registro Financeiro gerado com sucesso");
    }


}
