package com.prueba.tarjeta.utils;

import java.util.Random;

public class Constantes {

    private Constantes(){}

    public static final String LOG_ERROR_001 = "LOG001";
    public static final String LOG_ERROR_002 = "LOG002";
    public static final String LOG_ERROR_003 = "LOG003";
    public static final String LOG_ERROR_004 = "LOG004";
    public static final String LOG_ERROR_005 = "LOG005";
    public static final String LOG_ERROR_006 = "LOG006";

    public static final String LOG_ERROR_001_DESCRIPTION = "El id del producto debe contener 6 digitos";
    public static final String LOG_ERROR_002_DESCRIPTION = "El numero de tarjeta ingresado no existe";
    public static final String LOG_ERROR_003_DESCRIPTION = "La tarjeta ingresada no existe o se encuentra bloqueada";
    public static final String LOG_ERROR_004_DESCRIPTION = "La transacción que desea consultar no existe";
    public static final String LOG_ERROR_005_DESCRIPTION = "La transacción que desea anular ha superado las 24 horas";
    public static final String LOG_ERROR_006_DESCRIPTION = "La transacción ya ha sido anulada anteriormente";

    public static final String VALIDATION_ERROR= "VALIDATION_ERROR";

    public static final String ACTIVO = "Activo";
}
