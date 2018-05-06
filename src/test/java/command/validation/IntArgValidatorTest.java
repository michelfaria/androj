package command.validation;

import command.Command;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;


class IntArgValidatorTest {

    @Test
    void invalidPosition_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new IntArgValidator(-1));
    }

    @Test
    void validate_Valid_NoErrors() {
        IntArgValidator v = new IntArgValidator(0);
        Command c = new Command("", Arrays.asList("1"), null);
        assertThat(v.validate(c), is(empty()));
    }

    @Test
    void validate_Invalid_Errors_EnforcePositive() {
        IntArgValidator v = new IntArgValidator(0, IntArgValidator.Enforce.POSITIVE);
        Command c = new Command("", Arrays.asList("-1"), null);
        assertThat(v.validate(c), is(not(empty())));
    }

    @Test
    void validate_Invalid_Errors_EnforceNegative() {
        IntArgValidator v = new IntArgValidator(0, IntArgValidator.Enforce.NEGATIVE);
        Command c = new Command("", Arrays.asList("1"), null);
        assertThat(v.validate(c), is(not(empty())));
    }

    @Test
    void validate_Invalid_Errors() {
        IntArgValidator v = new IntArgValidator(0);
        Command c = new Command("", Arrays.asList("No"), null);
        assertThat(v.validate(c), is(not(empty())));
    }
}