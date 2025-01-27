package fi.ruoka.ostoslista.enums;

public enum Yksikko {
    LITRA("l"),
    DESILITRA("dl"),
    SENTTILITRA("cl"),
    MILLILITRA("ml"),
    TEELUSIKKA("tl"),
    RUOKALUSIKKA("rkl"),
    KILOGRAMMA("kg"),
    GRAMMA("g"),
    KAPPALE("kpl"),
    ISO_KAPPALE("iso kpl"),
    PUSSI("pussi"),
    RASIA("rasia"),
    PAKETTI("paketti"),
    PURKKI("purkki"),
    RIPAUS("ripaus"),
    RUUKKU("ruukku"),
    PULLO("pullo"),
    LAATIKKO("laatikko"),
    HIEMAN("hieman"),
    HYPPYSELLINEN("hyppysellinen"),
    LORAUS("loraus"),
    TILKKA("tilkka"),;

    private final String yksikko;

    Yksikko(String yksikko) {
        this.yksikko = yksikko;
    }

    public String getYksikko() {
        return yksikko;
    }

    public static String getPattern() {
        StringBuilder pattern = new StringBuilder();
        for (Yksikko yksikko : values()) {
            if (pattern.length() > 0) {
                pattern.append("|");
            }
            pattern.append(yksikko.getYksikko());
        }
        return pattern.toString();
    }
}
