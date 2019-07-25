CREATE VIEW VGF_CXDIARIO AS (
                          SELECT SUB_TOT.NUCAIXA,
       SUB_TOT.CODEMP,
       SUB_TOT.DHCAIXA,
       SUB_TOT.VLRENT,
       SUB_TOT.VLRSAI * -1 AS VLRSAI,
       (SUB_TOT.VLRENT-SUB_TOT.VLRSAI) AS VLRCAIXA
FROM (
     SELECT CXVLR.NUCAIXA,
     CX.CODEMP,
            TO_CHAR(CX.DHCAIXA,'dd/mm/yyyy') AS DHCAIXA,
                    SUM(CASE WHEN CXVLR.RECDESP = '1' THEN CXVLR.VLRLANC ELSE 0 END) AS VLRENT,
                    SUM(CASE WHEN CXVLR.RECDESP = '-1' THEN CXVLR.VLRLANC ELSE 0 END) AS VLRSAI
                    FROM AD_CXDIARIOVLR CXVLR

                    INNER JOIN AD_CXDIARIO CX ON CX.NUCAIXA = CXVLR.NUCAIXA

                    GROUP BY CXVLR.NUCAIXA, TO_CHAR(CX.DHCAIXA,'dd/mm/yyyy'),CX.CODEMP) SUB_TOT

GROUP BY SUB_TOT.NUCAIXA,
         SUB_TOT.DHCAIXA,
         SUB_TOT.VLRENT,SUB_TOT.VLRSAI,SUB_TOT.CODEMP
                            )

