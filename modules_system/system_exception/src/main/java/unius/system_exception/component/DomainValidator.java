package unius.system_exception.component;

import org.springframework.stereotype.Component;
import unius.system_exception.exception.WaggleException;
import unius.system_exception.type.ExceptionType;

import java.util.function.Predicate;

@Component
public class DomainValidator<T> {

    private final ThreadLocal<T> domain = new ThreadLocal<>();

    public DomainValidator() {}

    public DomainValidator<T> of(T object) {
        this.domain.set(object);
        return this;
    }

    public DomainValidator<T> validate(Predicate<T> predicate, ExceptionType exceptionType) {
        T localObject = domain.get();

        if(!predicate.test(localObject)) {
            reset();
            throw new WaggleException(exceptionType);
        }

        return this;
    }

    public T getOrThrow() {
        T validatedDomain = this.domain.get();
        reset();
        return validatedDomain;
    }

    private void reset() {
        this.domain.remove();
    }
}
