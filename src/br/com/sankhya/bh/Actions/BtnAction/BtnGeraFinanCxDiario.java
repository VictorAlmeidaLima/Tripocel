package br.com.sankhya.bh.Actions.BtnAction;

import br.com.sankhya.bh.Model.CaixaDiario;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.modelcore.senders.EMailSender;

public class BtnGeraFinanCxDiario implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contextoAcao) throws Exception {
        CaixaDiario cxD = new CaixaDiario();

        cxD.gerarFinanceiro(contextoAcao);
    }
}
