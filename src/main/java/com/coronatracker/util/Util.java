package com.coronatracker.util;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.assertj.core.util.Strings;

/**
 * Created by Sampath Katari on 26/03/20.
 */
public class Util {
    private Util() {}

    public static String getUserId(final HttpServletRequest request) {
        final Optional<Object> userId = Optional.ofNullable(request.getAttribute("userId"));
        return userId.isPresent() ? userId.get().toString() : "";
    }

    public static String getUpdatedValue(final String prevValue, final String newValue) {
        return !Strings.isNullOrEmpty(newValue) ? newValue : prevValue;
    }
}
