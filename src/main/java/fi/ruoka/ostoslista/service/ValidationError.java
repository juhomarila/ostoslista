package fi.ruoka.ostoslista.service;

public class ValidationError {
    /*
     * VE is ValidationError, RE is ReseptiError, OLE is OstoslistaError, OE is
     * OstosError, TE is TuoteError, UE is UserError
     * 
     */
    public static final String VE001 = "VE001: null";
    public static final String VE002 = "VE002: empty";
    public static final String VE003 = "VE003: notvalid";
    public static final String VE004 = "VE004: forbidden";
    public static final String VE005 = "VE005: doesNotExist";
    public static final String VE006 = "VE006: exists";
    public static final String VE007 = "VE007: mismatch";
    public static final String RE101 = "RE101: recipe not found ";
    public static final String RE102 = "RE102: recipe data validation failed ";
    public static final String RE103 = "RE103: corrupt recipe data ";
    public static final String RE104 = "RE104: recipe save error ";
    public static final String RE105 = "RE105: recipe update error ";
    public static final String RE106 = "RE106: recipe delete error ";
    public static final String OLE101 = "OLE101: ostoslista not found ";
    public static final String OLE102 = "OLE102: ostoslista data validation failed ";
    public static final String OLE103 = "OLE103: corrupt ostoslista data ";
    public static final String OLE104 = "OLE104: ostoslista save error ";
    public static final String OLE105 = "OLE105: ostoslista update error ";
    public static final String OLE106 = "OLE106: ostoslista delete error ";
    public static final String OE101 = "OE101: ostos not found ";
    public static final String OE102 = "OE102: ostos data validation failed ";
    public static final String OE103 = "OE103: corrupt ostos data ";
    public static final String OE104 = "OE104: ostos save error ";
    public static final String OE105 = "OE105: ostos update error ";
    public static final String OE106 = "OE106: ostos delete error ";
    public static final String TE101 = "TE101: tuote not found ";
    public static final String TE102 = "TE102: tuote data validation failed ";
    public static final String TE103 = "TE103: corrupt tuote data ";
    public static final String TE104 = "TE104: tuote save error ";
    public static final String TE105 = "TE105: tuote update error ";
    public static final String TE106 = "TE106: tuote delete error ";
    public static final String UE101 = "UE101: user not found ";
    public static final String UE102 = "UE102: user password mismatch ";
}
