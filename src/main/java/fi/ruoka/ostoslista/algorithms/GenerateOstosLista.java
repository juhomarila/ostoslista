package fi.ruoka.ostoslista.algorithms;

import java.util.Set;

import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.dto.RuokaAineDto;
import fi.ruoka.ostoslista.enums.Tuotteet;
import fi.ruoka.ostoslista.enums.Yksikko;

@Service
public class GenerateOstosLista {

    public OstosDto generateOstosFromRuokaAine(RuokaAineDto dto) {
        Set<String> kplYksikot = Set.of(Yksikko.KAPPALE.getYksikko(), Yksikko.ISO_KAPPALE.getYksikko());

        OstosDto ostos = new OstosDto();
        ostos.setTuote(dto.getRuokaAine());
        if (dto.getRuokaAine().equals(Tuotteet.PORKKANA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.SIPULI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.VALKOSIPULI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.PUNASIPULI.getTuote())) {
            // if (kplYksikot.contains(dto.getYksikko())) {
            // ostos.setMaara(1L);
            // ostos.setYksikko(Yksikko.PUSSI.getYksikko());
            // } else {
            // ostos.setMaara(dto.getMaara());
            // ostos.setYksikko(dto.getYksikko());
            // }
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PUSSI.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.MAITO.getTuote())) {
            if (dto.getYksikko().equals(Yksikko.LITRA.getYksikko())) {
                Double maara = dto.getMaara() / 1.75; // 1.75l maitopurkki
                ostos.setMaara((long) Math.ceil(maara));
            } else if (dto.getYksikko().equals(Yksikko.DESILITRA.getYksikko())) {
                Double maara = dto.getMaara() / 10.0 / 1.75; // 1.75l maitopurkki
                ostos.setMaara((long) Math.ceil(maara));
            } else if (dto.getYksikko().equals(Yksikko.SENTTILITRA.getYksikko())) {
                Double maara = dto.getMaara() / 100.0 / 1.75; // 1.75l maitopurkki
                ostos.setMaara((long) Math.ceil(maara));
            } else {
                ostos.setMaara(dto.getMaara());
            }
            ostos.setYksikko(Yksikko.TÖLKKI.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.VOI.getTuote())) {
            // if (dto.getYksikko().equals(Yksikko.GRAMMA.getYksikko())) {
            // Double maara = dto.getMaara() / 500.0; // 500g voipaketti
            // ostos.setMaara((long) Math.ceil(maara));
            // } else if (dto.getYksikko().equals(Yksikko.DESILITRA.getYksikko())) {
            // Double maara = dto.getMaara() * 90.0 / 1000; // dl voita painaa noin 90g
            // ostos.setMaara((long) Math.ceil(maara));
            // } else if (dto.getYksikko().equals(Yksikko.RUOKALUSIKKA.getYksikko())) {
            // Double maara = dto.getMaara() * 15.0 / 1000; // rkl voita painaa noin 15g
            // ostos.setMaara((long) Math.ceil(maara));
            // } else {
            // ostos.setMaara(dto.getMaara());
            // }
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PAKETTI.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.VEHNÄJAUHO.getTuote())) {
            // if (dto.getYksikko().equals(Yksikko.DESILITRA.getYksikko())) {
            // Double maara = dto.getMaara() * 60.0 / 1000; // dl jauhoja painaa noin 60g
            // ostos.setMaara((long) Math.ceil(maara));
            // } else if (dto.getYksikko().equals(Yksikko.GRAMMA.getYksikko())) {
            // Double maara = dto.getMaara() / 1000.0; // 1kg jauhopussi
            // ostos.setMaara((long) Math.ceil(maara));
            // } else {
            // ostos.setMaara(dto.getMaara());
            // }
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PUSSI.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.SOKERI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.FARIINISOKERI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.RUOKOSOKERI.getTuote())) {
            // if (dto.getYksikko().equals(Yksikko.GRAMMA.getYksikko())) {
            // Double maara = dto.getMaara() / 1000.0; // 1kg sokeripussi
            // ostos.setMaara((long) Math.ceil(maara));
            // } else if (dto.getYksikko().equals(Yksikko.DESILITRA.getYksikko())) {
            // Double maara = dto.getMaara() * 85.0 / 1000; // dl sokeria painaa noin 85g
            // ostos.setMaara((long) Math.ceil(maara));
            // } else if (dto.getYksikko().equals(Yksikko.RUOKALUSIKKA.getYksikko())) {
            // Double maara = dto.getMaara() * 12.0 / 1000; // rkl sokeria painaa noin 12g
            // ostos.setMaara((long) Math.ceil(maara));
            // } else {
            // ostos.setMaara(dto.getMaara());
            // } // might use later
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PUSSI.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.HUNAJA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.SIIRAPPI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KETSUPPI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.BBQKASTIKE.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.SRIRACHA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.MAJONEESI.getTuote())) {
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PURKKI.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.SOIJAKASTIKE.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KALAKASTIKE.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.VALKOVIINIETIKKA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.RIISIVIINIETIKKA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.WORCHESTERINKASTIKE.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.OLIIVIÖLJY.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.SEESAMIÖLJY.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.RUOKAOLJY.getTuote())) {
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PULLO.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.KASVISFONDI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KANAFONDI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.NAUDANFONDI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KASVISLIEMIKUUTIO.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.NAUDANLIEMIKUUTIO.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KANALIEMIKUUTIO.getTuote())) {
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PAKETTI.getYksikko());
        } else if (dto.getRuokaAine().equals(Tuotteet.OREGANO.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.BASILIKA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.CURRY.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.PAPRIKAMAUSTE.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KANELI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KANELITANKO.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.TÄHTIANIS.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.RAKUUNA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KOKONAINENNEILIKKA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KADREMUMMA.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.INKIVÄÄRIJAUHE.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.MUSTAPIPPURI.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.MUSTAPIPPURIJAUHETTU.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.MUSTAPIPPURIKOKO.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.MAUSTEPIPPURIKOKO.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.MAUSTEPIPPURIJAUHETTU.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.KORIANTERINSIEMEN.getTuote())
                || dto.getRuokaAine().equals(Tuotteet.CHILIJAUHE.getTuote())) {
            ostos.setMaara(1L);
            ostos.setYksikko(Yksikko.PUSSI.getYksikko());
        } else {
            ostos.setMaara(dto.getMaara());
            ostos.setYksikko(dto.getYksikko());
        }
        ostos.setOstettu(false);
        return ostos;
    }
}
