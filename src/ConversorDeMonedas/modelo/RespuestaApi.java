package ConversorDeMonedas.modelo;

public record RespuestaApi(
        String result,
        String documentation,
        String terms_of_use,
        long time_last_update_unix,
        String time_last_update_utc,
        long time_next_update_unix,
        String time_next_update_utc,
        String base_code,
        String target_code,
        double conversion_rate
) { }

record TargetData(
        String locale,
        String two_letter_code,
        String currency_name,
        String currency_name_short,
        String display_symbol,
        String flag_url
) {}

