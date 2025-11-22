package br.com.academiadev.infrastructure.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class GenericCsvExporter {

    public static <T> String export(List<T> data, String[] colunasSolicitadas) {
        if (data == null || data.isEmpty()) {
            return "";
        }

        StringBuilder csv = new StringBuilder();

        csv.append(String.join(";", colunasSolicitadas)).append("\n");

        for (T item : data) {
            String linha = java.util.Arrays.stream(colunasSolicitadas)
                .map(coluna -> getFieldValue(item, coluna))
                .collect(Collectors.joining(";"));
            
            csv.append(linha).append("\n");
        }

        return csv.toString();
    }

    private static String getFieldValue(Object object, String fieldName) {
        try {
            Field field = findField(object.getClass(), fieldName);
            field.setAccessible(true);
            Object value = field.get(object);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            return "ERRO";
        }
    }

    private static Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), fieldName);
            }
            throw e;
        }
    }
}