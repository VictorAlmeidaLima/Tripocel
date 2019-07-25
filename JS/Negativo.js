

if ("-1".equals($col_RECDESP))
{
    return $col_VLRLANC.negate();
}
else{
    return $col_VLRLANC.plus();
}