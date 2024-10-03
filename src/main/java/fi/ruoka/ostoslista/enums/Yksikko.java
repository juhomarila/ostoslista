package fi.ruoka.ostoslista.enums;

public enum Yksikko {
    KILOGRAMMA("kg"),
    GRAMMA("g"),
    LITRA("l"),
    DESILITRA("dl"),
    SENTTILITRA("cl"),
    MILLILITRA("ml"),
    KAPPALE("kpl"),
    ISO_KAPPALE("iso kpl"),
    RUOKALUSIKKA("rkl"),
    TEELUSIKKA("tl"),
    RIPAUS("ripaus"),
    RUUKKU("ruukku"),
    PURKKI("purkki");

    private final String yksikko;

    Yksikko(String yksikko) {
        this.yksikko = yksikko;
    }

    public String getYksikko() {
        return yksikko;
    }
}
