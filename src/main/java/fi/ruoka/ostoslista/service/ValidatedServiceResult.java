package fi.ruoka.ostoslista.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidatedServiceResult<T> {
    private T t;
    private ValidationResult vr;

    public ValidatedServiceResult(T t, ValidationResult vr) {
        this.t = t;
        this.vr = vr;
    }
}
