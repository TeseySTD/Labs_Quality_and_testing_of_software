package edu.skoreiko;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab4
 * @class RomanToArabicConverterTest
 * @since 13.04.2025 - 01.23
 */
public class RomanToArabicConverterTest {
    @Test
    void whenInputNullThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> RomanToArabicConverter.romanToArabic(null));
    }

    @Test
    void whenInputBlankThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic(" "));
        assertTrue(thrown.getMessage().contains(" cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputZThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("Z"));
        assertTrue(thrown.getMessage().contains("Z cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputABCThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("ABC"));
        assertTrue(thrown.getMessage().contains("ABC cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputCyrillicIThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("І"));
        assertTrue(thrown.getMessage().contains("І cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputCyrillicCThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("С"));
        assertTrue(thrown.getMessage().contains("С cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputCyrillicXThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("Х"));
        assertTrue(thrown.getMessage().contains("Х cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputCyrillicMThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("М"));
        assertTrue(thrown.getMessage().contains("М cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputIIVThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("IIV"));
        assertTrue(thrown.getMessage().contains("IIV cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputVXThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("VX"));
        assertTrue(thrown.getMessage().contains("VX cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputLCThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("LC"));
        assertTrue(thrown.getMessage().contains("LC cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputDMThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("DM"));
        assertTrue(thrown.getMessage().contains("DM cannot be converted to a Roman Numeral"));
    }

   @Test
    void whenInputXMThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("XM"));
        assertTrue(thrown.getMessage().contains("XM cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputXDThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("XD"));
        assertTrue(thrown.getMessage().contains("XD cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputIMThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("IM"));
        assertTrue(thrown.getMessage().contains("IM cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputVLThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("VL"));
        assertTrue(thrown.getMessage().contains("VL cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputICThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("IC"));
        assertTrue(thrown.getMessage().contains("IC cannot be converted to a Roman Numeral"));
    }

    @Test
    void whenInputVXIIIIThenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> RomanToArabicConverter.romanToArabic("VXIIII"));
        assertTrue(thrown.getMessage().contains("VXIIII cannot be converted to a Roman Numeral"));
    }
}