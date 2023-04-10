package com.example.demo;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static <T> MappingJacksonValue filterModel(List<T> models, String... fields) {

            var filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
            var filters = new SimpleFilterProvider().setFailOnUnknownId(false)
                    .addFilter(models.get(0).getClass().getSimpleName() + "Filter", filter);
            var mapping = new MappingJacksonValue(models);
            mapping.setFilters(filters);
            return mapping;


    }

    public static <T> MappingJacksonValue filterModel(T model, String... fields) {

        var filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        var filters = new SimpleFilterProvider().setFailOnUnknownId(false)
                .addFilter(model.getClass().getSimpleName() + "Filter", filter);
        var mapping = new MappingJacksonValue(model);
        mapping.setFilters(filters);
        return mapping;
    }
}
